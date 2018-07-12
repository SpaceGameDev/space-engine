package space.util.lock.simplelock;

import org.jetbrains.annotations.NotNull;
import space.util.lock.keylock.KeyLockImpl;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * a simple wrapper class from {@link SimpleLock} to {@link KeyLockImpl}, which calls the supplier for the key
 */
public class WrapperLock<KEY> implements SimpleLock {
	
	public Supplier<KEY> key;
	public KeyLockImpl<? super KEY> lock;
	
	public WrapperLock(Supplier<KEY> key) {
		this(key, new KeyLockImpl<>());
	}
	
	public WrapperLock(Supplier<KEY> key, KeyLockImpl<? super KEY> lock) {
		this.key = key;
		this.lock = lock;
	}
	
	@Override
	public void lock() {
		lock.lock(key.get());
	}
	
	@Override
	public void lock(long time, @NotNull TimeUnit unit) {
		lock.lock(key.get(), time, unit);
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		lock.lockInterruptibly(key.get());
	}
	
	@Override
	public void lockInterruptibly(long time, @NotNull TimeUnit unit) throws InterruptedException {
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
