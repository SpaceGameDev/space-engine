package space.util.concurrent.lock.rwlock;

import space.util.concurrent.lock.simplelock.ISimpleLock;

/**
 * an object providing a read and a write lock to do easy resource management.
 */
public interface IRWLock {
	
	ISimpleLock readLock();
	
	ISimpleLock writeLock();
}
