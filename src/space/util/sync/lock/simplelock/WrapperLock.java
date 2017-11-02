package space.util.sync.lock.simplelock;

import space.util.sync.lock.keylock.KeyLock;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * a simple wrapper class from {@link ISimpleLock} to {@link KeyLock}, which calls the supplier for the key
 */
public class WrapperLock<KEY> implements ISimpleLock {
	
	public Supplier<KEY> key;
	public KeyLock<? super KEY> lock;
	
	public WrapperLock(Supplier<KEY> key) {
		this(key, new KeyLock<>());
	}
	
	public WrapperLock(Supplier<KEY> key, KeyLock<? super KEY> lock) {
		this.key = key;
		this.lock = lock;
	}
	
	@Override
	public void lock() {
		lock.lock(key.get());
	}
	
	@Override
	public void lock(long time, TimeUnit unit) {
		lock.lock(key.get(), time, unit);
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		lock.lockInterruptibly(key.get());
	}
	
	@Override
	public void lockInterruptibly(long time, TimeUnit unit) throws InterruptedException {
		lock.lockInterruptibly(key.get(), time, unit);
	}
	
	@Override
	public boolean tryLock() {
		return lock.tryLock(key.get());
	}
	
	@Override
	public void unlock() {
		lock.unlock(key.get());
	}
	
	@Override
	public boolean tryUnlock() {
		return lock.tryUnlock(key.get());
	}
	
	@Override
	public boolean isLocked() {
		return lock.isLocked();
	}
}
