package space.util.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeInstance {
	
	public static final Unsafe unsafe;
	
	static {
		Unsafe u = null;
		//noinspection EmptyCatchBlock
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			u = (Unsafe) f.get(null);
		} catch (Exception e) {
			
		}
		unsafe = u;
	}
	
	public static Unsafe getUnsafe() {
		return unsafe;
	}
	
	public static boolean isUnsafeAvailable() {
		return unsafe != null;
	}
	
	public static void throwIfUnavailable() throws NoUnsafeException {
		if (!isUnsafeAvailable())
			throw new NoUnsafeException("Unsafe is not avaible!");
	}
	
	public static Unsafe getUnsafeOrThrow() throws NoUnsafeException {
		throwIfUnavailable();
		return unsafe;
	}
	
	public static long objectFieldOffset(Class<?> clazz, String name) throws UnsafeNoFieldException {
		try {
			return unsafe.objectFieldOffset(clazz.getDeclaredField(name));
		} catch (Exception e) {
			throw new UnsafeNoFieldException("Field " + clazz.getName() + "." + name, e);
		}
	}
	
	@SuppressWarnings("EmptyCatchBlock")
	public static long objectFieldOffsetSearchSuperclass(Class<?> clazz, String name) throws UnsafeNoFieldException {
		Class<?> c = clazz;
		while (c != null) {
			try {
				return unsafe.objectFieldOffset(c.getDeclaredField(name));
			} catch (NoSuchFieldException e) {

			}
			c = c.getSuperclass();
		}
		throw new UnsafeNoFieldException("Field " + clazz.getName() + "." + name);
	}
}
