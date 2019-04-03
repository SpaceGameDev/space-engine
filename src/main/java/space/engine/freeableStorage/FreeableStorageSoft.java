package space.engine.freeableStorage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableList.Entry;

import java.lang.ref.SoftReference;
import java.util.Arrays;

public abstract class FreeableStorageSoft<T> extends SoftReference<T> implements Freeable {
	
	private volatile boolean isFreed = false;
	private final FreeableList.Entry[] entries;
	private volatile FreeableList subList;
	
	public FreeableStorageSoft(@Nullable T referent, @NotNull Object[] parents) {
		super(referent, FreeableStorageCleaner.QUEUE);
		entries = Arrays.stream(parents).map(parent -> Freeable.getFreeable(parent).getSubList().insert(this)).toArray(Entry[]::new);
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
	public FreeableList getSubList() {
		if (subList != null)
			return subList;
		synchronized (this) {
			if (subList != null)
				return subList;
			return subList = new FreeableList();
		}
	}
}
