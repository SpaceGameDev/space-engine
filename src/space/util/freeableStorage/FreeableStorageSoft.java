package space.util.freeableStorage;

import space.util.freeableStorage.FreeableStorageList.Entry;

import java.lang.ref.SoftReference;

public abstract class FreeableStorageSoft<T> extends SoftReference<T> implements FreeableStorage {
	
	private volatile boolean isFreed = false;
	private final FreeableStorageList.Entry[] entries;
	private final int freePriority;
	private FreeableStorageList subList;
	
	public FreeableStorageSoft(T referent, FreeableStorage... lists) {
		super(referent, FreeableStorageCleaner.QUEUE);
		
		int freePriority = Integer.MIN_VALUE;
		entries = new FreeableStorageList.Entry[lists.length];
		for (int i = 0; i < lists.length; i++) {
			entries[i] = lists[i].getSubList().insert(this);
			int lfp = lists[i].freePriority();
			if (lfp > freePriority)
				freePriority = lfp;
		}
		this.freePriority = freePriority - 1;
	}
	
	//free
	@Override
	public synchronized final void free() {
		if (isFreed)
			return;
		isFreed = true;
		
		//entries
		for (Entry entry : entries)
			entry.remove();
		//subList
		if (subList != null)
			subList.free();
		
		handleFree();
	}
	
	protected abstract void handleFree();
	
	@Override
	public boolean isFreed() {
		return isFreed;
	}
	
	//other
	@Override
	public int freePriority() {
		return freePriority;
	}
	
	//children
	@Override
	public synchronized FreeableStorageList getSubList() {
		return subList != null ? subList : (subList = FreeableStorageListImpl.createList(freePriority));
	}
}
