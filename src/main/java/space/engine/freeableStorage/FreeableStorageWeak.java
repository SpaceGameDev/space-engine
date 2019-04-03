package space.engine.freeableStorage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableList.Entry;
import space.engine.sync.DelayTask;
import space.engine.sync.Tasks;
import space.engine.sync.barrier.Barrier;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Objects;

public abstract class FreeableStorageWeak<T> extends WeakReference<T> implements Freeable {
	
	private volatile boolean isFreed = false;
	private final FreeableList.Entry[] entries;
	private volatile @Nullable FreeableList subList;
	private @Nullable Barrier freeBarrier;
	
	public FreeableStorageWeak(@Nullable T referent, @NotNull Object[] parents) {
		super(referent, FreeableStorageCleaner.QUEUE);
		entries = Arrays.stream(parents).map(parent -> Freeable.getFreeable(parent).getSubList().insert(this)).toArray(Entry[]::new);
	}
	
	//free
	@Override
	public synchronized final @NotNull Barrier free() {
		if (isFreed)
			return Objects.requireNonNull(freeBarrier);
		isFreed = true;
		
		FreeableList subList = this.subList;
		if (subList != null) {
			Barrier subListFree = subList.free();
			if (subListFree != Barrier.ALWAYS_TRIGGERED_BARRIER) {
				
				//we need to wait for subList to free
				freeBarrier = Tasks.runnable(() -> {
					throw new DelayTask(handleFree());
				}).submit(subListFree);
				freeBarrier.addHook(this::removeEntries);
				return freeBarrier;
			}
		}
		
		//no waiting for subList
		freeBarrier = handleFree();
		freeBarrier.addHook(this::removeEntries);
		return freeBarrier;
	}
	
	protected abstract @NotNull Barrier handleFree();
	
	private void removeEntries() {
		for (Entry entry : entries)
			entry.remove();
	}
	
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
		FreeableList subList = this.subList;
		if (subList != null)
			return subList;
		synchronized (this) {
			subList = this.subList;
			if (subList != null)
				return subList;
			return this.subList = new FreeableList();
		}
	}
}
