package space.util.concurrent.lock.rwlock;

import space.util.concurrent.lock.simplelock.SimpleLock;

/**
 * an object providing a read and a write lock to do easy resource management.
 */
public interface RWLock {
	
	SimpleLock readLock();
	
	SimpleLock writeLock();
}
