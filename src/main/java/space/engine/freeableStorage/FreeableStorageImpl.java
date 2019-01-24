package space.engine.freeableStorage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableStorageList.Entry;

import java.lang.ref.PhantomReference;

public abstract class FreeableStorageImpl extends PhantomReference<Object> implements FreeableStorage {
	
	private volatile boolean isFreed = false;
	private final FreeableStorageList.Entry[] entries;
	private final int freePriority;
	private FreeableStorageList subList;
	
	public FreeableStorageImpl(@Nullable Object referent, @NotNull FreeableStorage... parents) {
		super(referent, FreeableStorageCleaner.QUEUE);
		
		int freePriority = Integer.MIN_VALUE;
		entries = new FreeableStorageList.Entry[parents.length];
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
	@NotNull
	@Override
	public synchronized FreeableStorageList getSubList() {
		return subList != null ? subList : (subList = FreeableStorageListImpl.createList(freePriority));
	}
}
