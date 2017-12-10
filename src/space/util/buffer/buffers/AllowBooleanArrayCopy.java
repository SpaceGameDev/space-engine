package space.util.buffer.buffers;

import static space.util.unsafe.UnsafeInstance.UNSAFE;
import static sun.misc.Unsafe.ARRAY_BOOLEAN_BASE_OFFSET;

public class AllowBooleanArrayCopy {
	
	public static final boolean ALLOWBOOLEANARRAYCOPY;
	
	static {
		ALLOWBOOLEANARRAYCOPY = (UNSAFE.getByte(new boolean[] {true}, (long) ARRAY_BOOLEAN_BASE_OFFSET) != (byte) 0) && (UNSAFE.getByte(new boolean[] {false}, (long) ARRAY_BOOLEAN_BASE_OFFSET) == (byte) 0);
	}
}
