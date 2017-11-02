package space.util.sync.lock.rwlock;

import space.util.sync.lock.simplelock.ISimpleLock;

/**
 * an object providing a read and a write lock to do easy resource management.
 */
public interface IRWLock {
	
	ISimpleLock readLock();
	
	ISimpleLock writeLock();
}
