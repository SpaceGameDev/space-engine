package space.util.concurrent.lock.keylock;

import space.util.concurrent.awaitable.OneToOneSignalable;

import java.util.concurrent.TimeUnit;

public class KeyLock<KEY> extends OneToOneSignalable implements IKeyLock<KEY> {
	
	public KEY currentKey;
	public int stackLevel;
	
	//lock
	@Override
	public synchronized void lock(KEY key) {
		while (!tryLock(key))
			try {
				await();
			} catch (InterruptedException ignore) {
				
			}
	}
	
	@Override
	public synchronized void lock(KEY key, long time, TimeUnit unit) {
		while (!tryLock(key))
			try {
				await(time, unit);
			} catch (InterruptedException ignore) {
				
			}
	}
	
	@Override
	public synchronized void lockInterruptibly(KEY key) throws InterruptedException {
		while (!tryLock(key))
			await();
	}
	
	@Override
	public synchronized void lockInterruptibly(KEY key, long time, TimeUnit unit) throws InterruptedException {
		while (!tryLock(key))
			await(time, unit);
	}
	
	@Override
	public synchronized boolean tryLock(KEY key) {
		if (currentKey == null) {
			currentKey = key;
			stackLevel = 1;
			return true;
		}
		if (currentKey == key) {
			stackLevel++;
			return true;
		}
		
		return false;
	}
	
	//unlock
	@Override
	public synchronized boolean tryUnlock(KEY key) {
		if (currentKey == null || currentKey != key)
			return false;
		unlock();
		return true;
	}
	
	@Override
	public synchronized void unlock(KEY key) {
		if (currentKey == null)
			throw new IllegalStateException("Not locked!");
		if (currentKey != key)
			throw new IllegalStateException("Locked by other key!");
		unlock();
	}
	
	/**
	 * will force unlock ignoring the key
	 */
	protected synchronized void unlock() {
		stackLevel--;
		if (stackLevel == 0) {
			currentKey = null;
			signal();
		}
	}
	
	//getter
	@Override
	public synchronized boolean isLocked() {
		return currentKey != null;
	}
	
	@Override
	public synchronized KEY getHolder() {
		return currentKey;
	}
}
