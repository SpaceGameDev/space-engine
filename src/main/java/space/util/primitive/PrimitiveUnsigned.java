package space.util.primitive;

public class PrimitiveUnsigned extends Primitive {
	
	public PrimitiveUnsigned(int bytes) {
		super(bytes);
	}
	
	public PrimitiveUnsigned(int bytes, int bits, boolean isAligned, int shift) {
		super(bytes, bits, isAligned, shift);
	}
}
