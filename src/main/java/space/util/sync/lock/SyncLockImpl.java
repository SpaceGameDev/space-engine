package space.util.sync.lock;

import org.jetbrains.annotations.NotNull;
import space.util.lock.keylock.BlockingKeyLock;
import space.util.lock.keylock.BlockingKeyLockImpl;

import java.util.ArrayList;
import java.util.List;

public class SyncLockImpl implements SyncLock {
	
	private static final Object LOCK_OBJECT = new Object();
	
	/**
	 * only non-blocking methods are used
	 */
	private final @NotNull BlockingKeyLock<Object> lock = new BlockingKeyLockImpl<>();
	private @NotNull List<Runnable> notifyUnlock = new ArrayList<>();
	
	@Override
	public synchronized boolean tryLock() {
		return lock.tryLock(LOCK_OBJECT);
	}
	
	@Override
	public synchronized void unlock() {
		lock.unlock(LOCK_OBJECT);
		notifyUnlock.forEach(Runnable::run);
		notifyUnlock.clear();
	}
	
	@Override
	public synchronized void notifyUnlock(Runnable run) {
		notifyUnlock.add(run);
	}
}
