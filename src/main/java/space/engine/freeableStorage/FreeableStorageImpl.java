package space.engine.freeableStorage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableStorageList.Entry;

import java.lang.ref.PhantomReference;
import java.util.Arrays;

public abstract class FreeableStorageImpl extends PhantomReference<Object> implements FreeableStorage {
	
	private volatile boolean isFreed = false;
	private final FreeableStorageList.Entry[] entries;
	private volatile FreeableStorageList subList;
	
	public FreeableStorageImpl(@Nullable Object referent, @NotNull FreeableStorage... parents) {
		super(referent, FreeableStorageCleaner.QUEUE);
		entries = Arrays.stream(parents).map(parent -> parent.getSubList().insert(this)).toArray(Entry[]::new);
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
	
	//children
	@NotNull
	@Override
	public FreeableStorageList getSubList() {
		if (subList != null)
			return subList;
		synchronized (this) {
			if (subList != null)
				return subList;
			return subList = FreeableStorageListImpl.createList();
		}
	}
}
