package space.util.buffer.buffers;

import space.util.unsafe.UnsafeInstance;
import sun.misc.Unsafe;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static space.util.unsafe.UnsafeInstance.objectFieldOffsetWithSuper;

public class NioByteBufferWrapper {
	
	private static final Unsafe UNSAFE = UnsafeInstance.getUnsafeOrThrow();
	
	public static final Class<?> BYTE_BUFFER_CLASS;
	public static final long BYTE_BUFFER_ADDRESS;
	public static final long BYTE_BUFFER_CAPACITY;
	
	static {
		try {
			ByteBuffer parent = ByteBuffer.allocateDirect(0);
			ByteBuffer bb = parent.slice();
			
			BYTE_BUFFER_CLASS = bb.getClass();
			BYTE_BUFFER_ADDRESS = objectFieldOffsetWithSuper(BYTE_BUFFER_CLASS, "address");
			BYTE_BUFFER_CAPACITY = objectFieldOffsetWithSuper(BYTE_BUFFER_CLASS, "capacity");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//wrap methods
	public static ByteBuffer wrap(Buffer buffer) {
		long capacity = buffer.capacity();
		if (capacity > Integer.MAX_VALUE)
			throw new RuntimeException("buffer capacity " + capacity + " above int size limit");
		return wrap(buffer, (int) capacity);
	}
	
	public static ByteBuffer wrap(Buffer buffer, int length) {
		if (length > buffer.capacity())
			throw new RuntimeException("length exceeds capacity: " + length + " > " + buffer.capacity());
		
		try {
			ByteBuffer b = (ByteBuffer) UNSAFE.allocateInstance(BYTE_BUFFER_CLASS);
			UNSAFE.putLong(b, BYTE_BUFFER_ADDRESS, buffer.address());
			UNSAFE.putInt(b, BYTE_BUFFER_CAPACITY, length);
			
			b.order(ByteOrder.nativeOrder());
			b.clear();
			return b;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
	}
}
