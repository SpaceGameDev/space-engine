package space.util.concurrent.lock.simplelock;

import space.util.concurrent.lock.keylock.KeyLock;

import java.util.function.Supplier;

/**
 * a simple wrapper class from {@link ISimpleLock} to {@link KeyLock}, which gets the Key by the Constructor argument
 */
public class SimpleLock<KEY> extends WrapperLock<KEY> {
	
	public SimpleLock(KEY key) {
		super(makeSupplier(key));
	}
	
	public SimpleLock(KEY key, KeyLock<? super KEY> lock) {
		super(makeSupplier(key), lock);
	}
	
	public static <T> Supplier<T> makeSupplier(T t) {
		return () -> t;
	}
}
