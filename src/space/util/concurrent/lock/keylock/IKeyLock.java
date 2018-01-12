package space.util.concurrent.lock.keylock;

import java.util.concurrent.TimeUnit;

/**
 * A simple Lock which can be locked with a KEY instead of the current Thread
 */
public interface IKeyLock<KEY> {
	
	//lock
	void lock(KEY key);
	
	void lock(KEY key, long time, TimeUnit unit);
	
	void lockInterruptibly(KEY key) throws InterruptedException;
	
	void lockInterruptibly(KEY key, long time, TimeUnit unit) throws InterruptedException;
	
	boolean tryLock(KEY key);
	
	//unlock
	void unlock(KEY key);
	
	/**
	 * may be an {@link UnsupportedOperationException}
	 */
	boolean tryUnlock(KEY key);
	
	//getter
	
	/**
	 * may be an {@link UnsupportedOperationException}
	 */
	boolean isLocked();
	
	/**
	 * may be an {@link UnsupportedOperationException}
	 */
	KEY getHolder();
	
	default void execute(KEY key, Runnable command) {
		lock(key);
		try {
			command.run();
		} finally {
			unlock(key);
		}
	}
	
	default void executeInterruptibly(KEY key, Runnable command) {
		try {
			lockInterruptibly(key);
			try {
				command.run();
			} finally {
				unlock(key);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
