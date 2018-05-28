package space.util.primitive;

public class PrimitiveFloatingPoint extends PrimitiveSigned {
	
	/**
	 * bit where the fraction starts
	 */
	public final int fractionStart;
	
	/**
	 * bit where the fraction ends
	 */
	public final int fractionEnd;
	
	/**
	 * size of the fraction
	 */
	public final int fractionSize;
	
	/**
	 * size of the fraction including the leading 1 not in the data type
	 */
	public final int fractionFullSize;
	
	/**
	 * bit where the exponent starts
	 */
	public final int exponentStart;
	
	/**
	 * bit where the exponent ends
	 */
	public final int exponentEnd;
	
	/**
	 * size of the exponent
	 */
	public final int exponentSize;
	
	public PrimitiveFloatingPoint(int bytes, int signBit, int fractionStart, int fractionSize, int exponentStart, int exponentSize) {
		super(bytes, signBit);
		
		this.fractionStart = fractionStart;
		this.fractionEnd = fractionStart + fractionSize;
		this.fractionSize = fractionSize;
		this.fractionFullSize = fractionSize + 1;
		this.exponentStart = exponentStart;
		this.exponentEnd = exponentStart + exponentSize;
		this.exponentSize = exponentSize;
	}
	
	public PrimitiveFloatingPoint(int bytes, int bits, boolean isAligned, int shift, int signBit, int fractionStart, int fractionSize, int exponentStart, int exponentSize) {
		super(bytes, bits, isAligned, shift, signBit);
		
		this.fractionStart = fractionStart;
		this.fractionEnd = fractionStart + fractionSize;
		this.fractionSize = fractionSize;
		this.fractionFullSize = fractionSize + 1;
		this.exponentStart = exponentStart;
		this.exponentEnd = exponentStart + exponentSize;
		this.exponentSize = exponentSize;
	}
}
