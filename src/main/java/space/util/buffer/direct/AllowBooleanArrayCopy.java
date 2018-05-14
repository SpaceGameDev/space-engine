package space.util.buffer.direct;

import space.util.unsafe.UnsafeInstance;
import sun.misc.Unsafe;

import static sun.misc.Unsafe.ARRAY_BOOLEAN_BASE_OFFSET;

/**
 * {@link AllowBooleanArrayCopy#ALLOW_BOOLEAN_ARRAY_COPY} is true if
 * <ul>
 * <li>a boolean being false == 0</li>
 * <li>a boolean being true != 0</li>
 * </ul>
 * This allows us to just copy the booleans instead of converting them individually.
 */
public class AllowBooleanArrayCopy {
	
	private static final Unsafe UNSAFE = UnsafeInstance.getUnsafeOrThrow();
	
	/**
	 * @see AllowBooleanArrayCopy
	 */
	public static final boolean ALLOW_BOOLEAN_ARRAY_COPY;
	
	static {
		ALLOW_BOOLEAN_ARRAY_COPY = (UNSAFE.getByte(new boolean[] {true}, (long) ARRAY_BOOLEAN_BASE_OFFSET) != (byte) 0) && (UNSAFE.getByte(new boolean[] {false}, (long) ARRAY_BOOLEAN_BASE_OFFSET) == (byte) 0);
	}
}
