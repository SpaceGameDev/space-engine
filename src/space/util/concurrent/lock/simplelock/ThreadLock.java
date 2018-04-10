package space.util.concurrent.lock.simplelock;

import space.util.concurrent.lock.keylock.KeyLockImpl;

/**
 * a simple wrapper class from {@link SimpleLock} to {@link KeyLockImpl} with 'Thread' as the Key
 */
public class ThreadLock extends WrapperLock<Thread> {
	
	public ThreadLock() {
		super(Thread::currentThread);
	}
	
	public ThreadLock(KeyLockImpl<? super Thread> lock) {
		super(Thread::currentThread, lock);
	}
}
