package space.util.math;

import space.util.SystemInfo;

import static java.lang.Math.log;

public class PrimitiveInteger {
	
	//bits
	public static final int ByteBits = 8;
	public static final int BooleanBits = 1;
	public static final int PointerBits;
	
	public static final int int8Bits = 8;
	public static final int int16Bits = 16;
	public static final int int32Bits = 32;
	public static final int int64Bits = 64;
	
	public static final int fp16Bits = 16;
	public static final int fp32Bits = 32;
	public static final int fp64Bits = 64;
	
	//bytes
	public static final int ByteBytes = 1;
	public static final int BooleanBytes = 1;
	public static final int PointerBytes;
	
	public static final int int8Bytes = 1;
	public static final int int16Bytes = 2;
	public static final int int32Bytes = 4;
	public static final int int64Bytes = 8;
	
	public static final int fp16Bytes = 2;
	public static final int fp32Bytes = 4;
	public static final int fp64Bytes = 8;
	
	//shift
	public static final int ByteShift = 0;
	public static final int BooleanShift = 0;
	public static final int PointerShift;
	
	public static final int int8Shift = 0;
	public static final int int16Shift = 1;
	public static final int int32Shift = 2;
	public static final int int64Shift = 3;
	
	public static final int fp16Shift = 1;
	public static final int fp32Shift = 2;
	public static final int fp64Shift = 3;
	
	//mask sign
	public static final int ByteMaskSign = 0x80;
	
	public static final int int8MaskSign = 0x80;
	public static final int int16MaskSign = 0x8000;
	public static final int int32MaskSign = 0x80000000;
	public static final long int64MaskSign = 0x8000000000000000L;
	
	//mask number
	public static final int ByteMaskNumber = 0x7F;
	
	public static final int int8MaskNumber = 0x7F;
	public static final int int16MaskNumber = 0x7FFF;
	public static final int int32MaskNumber = 0x7FFFFFFF;
	public static final long int64MaskNumber = 0x7FFFFFFFFFFFFFFFL;
	
	static {
		PointerBits = SystemInfo.SYSTEMINFO.archBits;
		PointerBytes = PointerBits / 8;
		PointerShift = (int) (log(PointerBytes) / log(2));
	}
}
