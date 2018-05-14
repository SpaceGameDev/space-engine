package space.util.concurrent.lock.rwlock;

import space.util.concurrent.lock.keylock.KeyLock;
import space.util.concurrent.lock.keylock.KeyLockImpl;
import space.util.concurrent.lock.simplelock.SimpleLock;
import space.util.concurrent.lock.simplelock.SimpleLockImpl;
import space.util.concurrent.lock.simplelock.ThreadLock;

/**
 * locking for write while holding a lock for read will cause a deadlock in the Thread!
 */
public class RWLockImpl implements RWLock {
	
	public static final Object rLockObj = new Object();
	
	public SimpleLock rLock;
	public SimpleLock wLock;
	
	public RWLockImpl() {
		this(new KeyLockImpl<>());
	}
	
	public RWLockImpl(KeyLock<Object> lock) {
		this(new SimpleLockImpl<>(rLockObj, (KeyLockImpl<? super Object>) lock), new ThreadLock((KeyLockImpl<? super Object>) lock));
	}
	
	public RWLockImpl(SimpleLock rLock, SimpleLock wLock) {
		this.rLock = rLock;
		this.wLock = wLock;
	}
	
	@Override
	public SimpleLock readLock() {
		return rLock;
	}
	
	@Override
	public SimpleLock writeLock() {
		return wLock;
	}
}
