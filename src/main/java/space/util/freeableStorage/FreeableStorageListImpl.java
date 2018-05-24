package space.util.freeableStorage;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.Freeable;
import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public abstract class FreeableStorageListImpl implements Freeable {
	
	public FreeableStorageListImpl prev;
	public FreeableStorageListImpl next;
	
	protected void insertBefore(FreeableStorageListImpl base) {
		prev = base.prev;
		next = base;
		prev.next = this;
		next.prev = this;
	}
	
	static FreeableStorageList createList(int freePriority) {
		return new List(freePriority);
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
	
	private static class List extends FreeableStorageListImpl implements FreeableStorageList, ToString {
		
		public int freePriority;
		
		private List(int freePriority) {
			this.freePriority = freePriority;
			prev = this;
			next = this;
		}
		
		@NotNull
		@Override
		public FreeableStorageListEntry insert(@NotNull FreeableStorage storage) {
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
			
			FreeableStorageListImpl next = this.next;
			while (next != this) {
				next.free();
				next = next.next;
			}
		}
		
		@NotNull
		@Override
		public <T> T toTSH(@NotNull ToStringHelper<T> api) {
			int size = 0;
			FreeableStorageListImpl next = this;
			while ((next = next.next) != this)
				size++;
			
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("size", size);
			tsh.add("freePriority", this.freePriority);
			return tsh.build();
		}
		
		private class FreeableStorageListEntry extends FreeableStorageListImpl implements Entry {
			
			final FreeableStorage freeableStorage;
			
			public FreeableStorageListEntry(FreeableStorage freeableStorage) {
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
		public String toString() {
			return toString0();
		}
	}
}
