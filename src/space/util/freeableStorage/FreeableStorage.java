package space.util.freeableStorage;

import space.util.baseobject.exceptions.FreedException;
import space.util.freeableStorage.IFreeableStorageList.Entry;

import java.lang.ref.PhantomReference;

public abstract class FreeableStorage extends PhantomReference<Object> implements IFreeableStorage {
	
	private volatile boolean isFreed = false;
	private final IFreeableStorageList.Entry[] entries;
	private final int freePriority;
	private IFreeableStorageList subList;
	
	public FreeableStorage(Object referent, IFreeableStorage... parents) {
		super(referent, FreeableStorageCleaner.QUEUE);
		
		int freePriority = Integer.MIN_VALUE;
		entries = new IFreeableStorageList.Entry[parents.length];
		for (int i = 0; i < parents.length; i++) {
			entries[i] = parents[i].getSubList().insert(this);
			int lfp = parents[i].freePriority();
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
	
	//isFreed
	@Override
	public boolean isFreed() {
		return isFreed;
	}
	
	@Override
	public void throwIfFreed() throws FreedException {
		if (isFreed)
			throw new FreedException(this);
	}
	
	//other
	@Override
	public int freePriority() {
		return freePriority;
	}
	
	//children
	@Override
	public synchronized IFreeableStorageList getSubList() {
		return subList != null ? subList : (subList = FreeableStorageList.createList(freePriority));
	}
}
