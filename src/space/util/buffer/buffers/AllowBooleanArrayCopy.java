package space.util.buffer.buffers;

import space.util.unsafe.UnsafeInstance;
import sun.misc.Unsafe;

import static sun.misc.Unsafe.ARRAY_BOOLEAN_BASE_OFFSET;

public class AllowBooleanArrayCopy {
	
	private static final Unsafe UNSAFE = UnsafeInstance.getUnsafeOrThrow();
	
	public static final boolean ALLOWBOOLEANARRAYCOPY;
	
	static {
		ALLOWBOOLEANARRAYCOPY = (UNSAFE.getByte(new boolean[] {true}, (long) ARRAY_BOOLEAN_BASE_OFFSET) != (byte) 0) && (UNSAFE.getByte(new boolean[] {false}, (long) ARRAY_BOOLEAN_BASE_OFFSET) == (byte) 0);
	}
}
