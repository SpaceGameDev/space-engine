package space.util.freeableStorage;

import space.util.baseobject.Freeable;
import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public abstract class FreeableStorageList implements Freeable {
	
	static IFreeableStorageList createList(int freePriority) {
		return new List(freePriority);
	}
	
	public FreeableStorageList prev;
	public FreeableStorageList next;
	
	protected void insertBefore(FreeableStorageList base) {
		prev = base.prev;
		next = base;
		prev.next = this;
		next.prev = this;
	}
	
	protected void remove(boolean quickRemove) {
		if (!quickRemove) {
			prev.next = next;
			next.prev = prev;
			next = null;
		}
		prev = null;
	}
	
	@Override
	public boolean isFreed() {
		return prev == null;
	}
	
	private static class List extends FreeableStorageList implements IFreeableStorageList, ToString {
		
		public int freePriority;
		
		private List(int freePriority) {
			this.freePriority = freePriority;
			prev = this;
			next = this;
		}
		
		@Override
		public FreeableStorageListEntry insert(IFreeableStorage storage) {
			return new FreeableStorageListEntry(storage);
		}
		
		@Override
		public int freePriority() {
			return freePriority;
		}
		
		@Override
		public synchronized void free() {
			if (isFreed())
				return;
			remove(true);
			
			FreeableStorageList next = this.next;
			while (next != this) {
				next.free();
				next = next.next;
			}
		}
		
		private class FreeableStorageListEntry extends FreeableStorageList implements Entry {
			
			final IFreeableStorage freeableStorage;
			
			public FreeableStorageListEntry(IFreeableStorage freeableStorage) {
				this.freeableStorage = freeableStorage;
				synchronized (List.this) {
					insertBefore(List.this);
				}
			}
			
			@Override
			public void remove() {
				synchronized (List.this) {
					remove(List.this.isFreed());
				}
			}
			
			//free
			@Override
			public void free() {
				synchronized (List.this) {
					if (isFreed())
						return;
					remove(List.this.isFreed());
				}
				
				freeableStorage.free();
			}
		}
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			int size = 0;
			FreeableStorageList next = this;
			while ((next = next.next) != this)
				size++;
			
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("size", size);
			tsh.add("freePriority", this.freePriority);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
