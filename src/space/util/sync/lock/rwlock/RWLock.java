package space.util.sync.lock.rwlock;

import space.util.sync.lock.keylock.IKeyLock;
import space.util.sync.lock.keylock.KeyLock;
import space.util.sync.lock.simplelock.ISimpleLock;
import space.util.sync.lock.simplelock.SimpleLock;
import space.util.sync.lock.simplelock.ThreadLock;

/**
 * locking for write while holding a lock for read will cause a deadlock in the Thread!
 */
public class RWLock implements IRWLock {
	
	public static final Object rLockObj = new Object();
	
	public ISimpleLock rLock;
	public ISimpleLock wLock;
	
	public RWLock() {
		this(new KeyLock<>());
	}
	
	public RWLock(IKeyLock<Object> lock) {
		this(new SimpleLock<>(rLockObj, (KeyLock<? super Object>) lock), new ThreadLock((KeyLock<? super Object>) lock));
	}
	
	public RWLock(ISimpleLock rLock, ISimpleLock wLock) {
		this.rLock = rLock;
		this.wLock = wLock;
	}
	
	@Override
	public ISimpleLock readLock() {
		return rLock;
	}
	
	@Override
	public ISimpleLock writeLock() {
		return wLock;
	}
}
