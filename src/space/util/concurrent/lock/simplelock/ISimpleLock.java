package space.util.concurrent.lock.simplelock;

import java.util.concurrent.TimeUnit;

/**
 * A Lock which is locked by your Thread
 */
public interface ISimpleLock {
	
	//lock
	void lock();
	
	void lock(long time, TimeUnit unit);
	
	void lockInterruptibly() throws InterruptedException;
	
	void lockInterruptibly(long time, TimeUnit unit) throws InterruptedException;
	
	boolean tryLock();
	
	//unlock
	void unlock();
	
	/**
	 * may be an {@link UnsupportedOperationException}
	 */
	boolean tryUnlock();
	
	//getter
	
	/**
	 * may be an {@link UnsupportedOperationException}
	 */
	boolean isLocked();
	
	default void execute(Runnable command) {
		lock();
		try {
			command.run();
		} finally {
			unlock();
		}
	}
	
	default void executeInterruptibly(Runnable command) {
		try {
			lockInterruptibly();
			try {
				command.run();
			} finally {
				unlock();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
