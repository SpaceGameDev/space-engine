package space.util.primitive;

public class PrimitiveInteger extends PrimitiveSigned {
	
	public PrimitiveInteger(int bytes, int signBit) {
		super(bytes, signBit);
	}
	
	public PrimitiveInteger(int bytes, int bits, boolean isAligned, int shift, int signBit) {
		super(bytes, bits, isAligned, shift, signBit);
	}
}
