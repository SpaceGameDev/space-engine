package space.util.buffer;

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
	
	/**
	 * Wraps a {@link Buffer} to a Java-{@link ByteBuffer}.
	 * <p><b>Note: A Reference to the {@link Buffer} should be kept during the existence of the returned ByteBuffer!</b></p>
	 *
	 * @param buffer the {@link Buffer} to wrap into a {@link ByteBuffer}
	 */
	public static ByteBuffer wrap(Buffer buffer) {
		long capacity = buffer.capacity();
		if (capacity > Integer.MAX_VALUE)
			throw new RuntimeException("buffer capacity " + capacity + " above int size limits of ByteBuffer");
		return wrap(buffer, (int) capacity);
	}
	
	/**
	 * Wraps a {@link Buffer} to a Java-{@link ByteBuffer}.
	 * <p><b>Note: A Reference to the {@link Buffer} should be kept during the existence of the returned ByteBuffer!</b></p>
	 *
	 * @param buffer the {@link Buffer} to wrap into a {@link ByteBuffer}
	 * @param length the length the {@link ByteBuffer} should have (32 bits only!)
	 */
	public static ByteBuffer wrap(Buffer buffer, int length) {
		return wrap(buffer, 0, length);
	}
	
	/**
	 * Wraps a {@link Buffer} to a Java-{@link ByteBuffer}.
	 * <p><b>Note: A Reference to the {@link Buffer} should be kept during the existence of the returned ByteBuffer!</b></p>
	 *
	 * @param buffer the {@link Buffer} to wrap into a {@link ByteBuffer}
	 * @param offset the offset at which the the returned {@link ByteBuffer} should begin
	 * @param length the length the {@link ByteBuffer} should have (32 bits only!)
	 */
	public static ByteBuffer wrap(Buffer buffer, long offset, int length) {
		if (offset + length > buffer.capacity())
			throw new RuntimeException("Total length exceeds capacity: " + (offset + length) + " > " + buffer.capacity());
		
		try {
			ByteBuffer b = (ByteBuffer) UNSAFE.allocateInstance(BYTE_BUFFER_CLASS);
			UNSAFE.putLong(b, BYTE_BUFFER_ADDRESS, buffer.address() + offset);
			UNSAFE.putInt(b, BYTE_BUFFER_CAPACITY, length);
			
			b.order(ByteOrder.nativeOrder()); //resets the variables 'bigEndian' and 'nativeByteOrder'
			b.clear(); //resets the variables 'position', 'limit' and 'mark', not clearing the buffer
			return b;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
	}
}
