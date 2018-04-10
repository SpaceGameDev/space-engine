package space.util.concurrent.lock.simplelock;

import space.util.concurrent.lock.keylock.KeyLockImpl;

import java.util.function.Supplier;

/**
 * a simple wrapper class from {@link SimpleLock} to {@link KeyLockImpl}, which gets the Key by the Constructor argument
 */
public class SimpleLockImpl<KEY> extends WrapperLock<KEY> {
	
	public SimpleLockImpl(KEY key) {
		super(makeSupplier(key));
	}
	
	public SimpleLockImpl(KEY key, KeyLockImpl<? super KEY> lock) {
		super(makeSupplier(key), lock);
	}
	
	public static <T> Supplier<T> makeSupplier(T t) {
		return () -> t;
	}
}
