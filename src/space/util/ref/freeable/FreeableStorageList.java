package space.util.ref.freeable;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class FreeableStorageList extends FreeableStorageListBase implements IFreeableStorageList, ToString {
	
	public int freePriority;
	
	public FreeableStorageList(int freePriority) {
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
		
		FreeableStorageListBase next = this.next;
		while (next != this) {
			next.free();
			next = next.next;
		}
	}
	
	private class FreeableStorageListEntry extends FreeableStorageListBase implements IFreeableStorageList.Entry {
		
		final IFreeableStorage freeableStorage;
		
		public FreeableStorageListEntry(IFreeableStorage freeableStorage) {
			this.freeableStorage = freeableStorage;
			synchronized (FreeableStorageList.this) {
				insertBefore(FreeableStorageList.this);
			}
		}
		
		@Override
		public void remove() {
			synchronized (FreeableStorageList.this) {
				remove(FreeableStorageList.this.isFreed());
			}
		}
		
		//free
		@Override
		public void free() {
			synchronized (FreeableStorageList.this) {
				if (isFreed())
					return;
				remove(FreeableStorageList.this.isFreed());
			}
			
			freeableStorage.free();
		}
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		int size = 0;
		FreeableStorageListBase next = this;
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
