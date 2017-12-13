package space.util.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeInstance {
	
	public static final Unsafe UNSAFE;
	
	static {
		Unsafe u = null;
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			u = (Unsafe) f.get(null);
		} catch (Exception ignore) {
			
		}
		UNSAFE = u;
	}
	
	public static Unsafe getUnsafe() {
		return UNSAFE;
	}
	
	public static boolean isUnsafeAvailable() {
		return UNSAFE != null;
	}
	
	public static void throwIfUnavailable() throws NoUnsafeException {
		if (!isUnsafeAvailable())
			throw new NoUnsafeException("Unsafe is not avaible!");
	}
	
	public static Unsafe getUnsafeOrThrow() throws NoUnsafeException {
		throwIfUnavailable();
		return UNSAFE;
	}
	
	public static long objectFieldOffset(Class<?> clazz, String name) throws UnsafeNoFieldException {
		try {
			return UNSAFE.objectFieldOffset(clazz.getDeclaredField(name));
		} catch (Exception e) {
			throw new UnsafeNoFieldException("Field " + clazz.getName() + "." + name, e);
		}
	}
	
	@SuppressWarnings("EmptyCatchBlock")
	public static long objectFieldOffsetWithSuper(Class<?> clazz, String name) throws UnsafeNoFieldException {
		Class<?> c = clazz;
		while (c != null) {
			try {
				return UNSAFE.objectFieldOffset(c.getDeclaredField(name));
			} catch (NoSuchFieldException e) {
			
			}
			c = c.getSuperclass();
		}
		throw new UnsafeNoFieldException("Field " + clazz.getName() + "." + name);
	}
}
