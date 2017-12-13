package space.util.buffer.buffers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;

import static space.util.unsafe.UnsafeInstance.*;

public class NioByteBufferWrapper {
	
	public static final Class<?> BYTE_BUFFER_CLASS;
	public static final long BYTE_BUFFER_PARENT;
	public static final long BYTE_BUFFER_ADDRESS;
	public static final long BYTE_BUFFER_CAPACITY;
	
	static {
		throwIfUnavailable();
		try {
			ByteBuffer parent = ByteBuffer.allocateDirect(0);
			ByteBuffer bb = parent.slice();
			BYTE_BUFFER_CLASS = bb.getClass();
			BYTE_BUFFER_ADDRESS = objectFieldOffsetWithSuper(BYTE_BUFFER_CLASS, "address");
			BYTE_BUFFER_CAPACITY = objectFieldOffsetWithSuper(BYTE_BUFFER_CLASS, "capacity");
			BYTE_BUFFER_PARENT = UNSAFE.objectFieldOffset(findBBParent(bb, parent));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Field findBBParent(Object o, Object find) throws IllegalArgumentException, IllegalAccessException {
		Class<?> s = o.getClass();
		while (s != null) {
			for (Field f : s.getDeclaredFields()) {
				if (Modifier.isStatic(f.getModifiers()))
					continue;
				f.setAccessible(true);
				if (f.get(o) == find)
					return f;
			}
			s = s.getSuperclass();
		}
		return null;
	}
	
	//wrap methods
	public static ByteBuffer wrap(Buffer buffer) {
		long capacity = buffer.capacity();
		if (capacity > Integer.MAX_VALUE)
			throw new RuntimeException("buffer capacity " + capacity + " above int size limit");
		return wrap(buffer, (int) capacity);
	}
	
	public static ByteBuffer wrap(Buffer buffer, int length) {
		if (length < buffer.capacity())
			throw new RuntimeException("length exceeds capacity: " + length + " > " + buffer.capacity());
		try {
			ByteBuffer b = (ByteBuffer) UNSAFE.allocateInstance(BYTE_BUFFER_CLASS);
			UNSAFE.putLong(b, BYTE_BUFFER_ADDRESS, buffer.address());
			UNSAFE.putInt(b, BYTE_BUFFER_CAPACITY, length);
			UNSAFE.putObject(b, BYTE_BUFFER_PARENT, null);
			return b;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
	}
}
