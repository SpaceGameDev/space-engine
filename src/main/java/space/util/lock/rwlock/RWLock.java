package space.util.lock.rwlock;

import org.jetbrains.annotations.NotNull;
import space.util.lock.simplelock.SimpleLock;

/**
 * an object providing a read and a write lock to do easy resource management.
 */
public interface RWLock {
	
	@NotNull SimpleLock readLock();
	
	@NotNull SimpleLock writeLock();
}
