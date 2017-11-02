package space.util.math;

public class PrimitiveFloatingPoint {
	
	public static final int float32SizeSign = 1;
	public static final int float32MaskSign = 0x80000000;
	public static final int float32ShiftSign = 31;
	public static final int float32SizeFraction = 23;
	public static final int float32SizeFractionFull = 24;
	public static final int float32MaskFraction = 0x007FFFFF;
	public static final int float32MaskFractionFull = 0x00FFFFFF;
	public static final int float32ShiftFraction = 0;
	public static final int float32ShiftFractionFull = 0;
	public static final int float32FractionFullBit = 0x00800000;
	public static final int float32SizeExponent = 8;
	public static final int float32MaskExponent = 0x7F800000;
	public static final int float32ShiftExponent = 23;
	
	public static final int float64SizeSign = 1;
	public static final long float64MaskSign = 0x8000000000000000L;
	public static final long float64ShiftSign = 63;
	public static final int float64SizeFraction = 52;
	public static final int float64SizeFractionFull = 53;
	public static final long float64MaskFraction = 0x000FFFFFFFFFFFFFL;
	public static final long float64MaskFractionFull = 0x001FFFFFFFFFFFFFL;
	public static final long float64FractionFullBit = 0x0010000000000000L;
	public static final long float64ShiftFraction = 0;
	public static final long float64ShiftFractionFull = 0;
	public static final int float64SizeExponent = 11;
	public static final long float64MaskExponent = 0x7FF0000000000000L;
	public static final long float64ShiftExponent = 52;
}
