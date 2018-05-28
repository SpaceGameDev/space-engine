package space.util.primitive;

import space.util.math.UnsignedMath;

public class Primitive<T> {
	
	/**
	 * size in bytes (= {@link Primitive#bits} / 8)
	 */
	public final int bytes;
	
	/**
	 * size in bits (= {@link Primitive#bytes} * 8)
	 */
	public final int bits;
	
	/**
	 * true if type is aligned (when {@link Primitive#bytes} is power of two).
	 */
	public final boolean isAligned;
	
	/**
	 * if {@link Primitive#isAligned}: shift distance to skip one element<br>
	 * otherwise: -1 (to cause an {@link Exception} if you shift)
	 */
	public final int shift;
	
	public Primitive(int bytes) {
		this(bytes, UnsignedMath.isPowerOfTwo(bytes));
	}
	
	private Primitive(int bytes, boolean isAligned) {
		this(bytes, bytes * 8, isAligned, isAligned ? UnsignedMath.getPowerOfTwoFloor(bytes) : -1);
	}
	
	public Primitive(int bytes, int bits, boolean isAligned, int shift) {
		this.bytes = bytes;
		this.bits = bits;
		this.isAligned = isAligned;
		this.shift = shift;
	}
}
