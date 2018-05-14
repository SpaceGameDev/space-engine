package space.util.primitive;

@SuppressWarnings("unused")
public class FloatingPointConst {
	
	//@formatter:off
	public static final int float32SignSize = 1;
	public static final int float32SignMask = 0x80000000;
	public static final int float32SignShift = 31;
	public static final int float32FractionSize = 23;
	public static final int float32FractionMask = 0x007FFFFF;
	public static final int float32FractionShift = 0;
	public static final int float32FractionFullSize = 24;
	public static final int float32FractionFullMask = 0x00FFFFFF;
	public static final int float32FractionFullShift = 0;
	public static final int float32FractionFullBit = 0x00800000;
	public static final int float32ExponentSize = 8;
	public static final int float32ExponentMask = 0x7F800000;
	public static final int float32ExponentShift = 23;
	
	public static final int  float64SignSize = 1;
	public static final long float64SignMask = 0x8000000000000000L;
	public static final long float64SignShift = 63;
	public static final int  float64FractionSize = 52;
	public static final long float64FractionMask = 0x000FFFFFFFFFFFFFL;
	public static final long float64FractionShift = 0;
	public static final int  float64FractionFullSize = 53;
	public static final long float64FractionFullMask = 0x001FFFFFFFFFFFFFL;
	public static final long float64FractionFullShift = 0;
	public static final long float64FractionFullBit = 0x0010000000000000L;
	public static final int  float64ExponentSize = 11;
	public static final long float64ExponentMask = 0x7FF0000000000000L;
	public static final long float64ExponentShift = 52;
	//@formatter:on
}
