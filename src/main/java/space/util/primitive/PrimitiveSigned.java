package space.util.primitive;

public class PrimitiveSigned<T> extends Primitive<T> {
	
	/**
	 * bit representing the sign
	 */
	public final int signBit;
	
	public PrimitiveSigned(int bytes, int signBit) {
		super(bytes);
		this.signBit = signBit;
	}
	
	public PrimitiveSigned(int bytes, int bits, boolean isAligned, int shift, int signBit) {
		super(bytes, bits, isAligned, shift);
		this.signBit = signBit;
	}
}
