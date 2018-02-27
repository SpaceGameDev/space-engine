package space.util.math;

import space.util.primitive.IntegerConst;

public class BigPrimitiveMath {
	
	//int[] array from primitive
	//unsigned
	public static int[] intArrayFromByteUnsigned(byte i2) {
		int i = Byte.toUnsignedInt(i2);
		if (Integer.compareUnsigned(i, 0x0) == 0)
			return new int[0];
		return new int[] {i};
	}
	
	public static int[] intArrayFromShortUnsigned(short i2) {
		int i = Short.toUnsignedInt(i2);
		if (Integer.compareUnsigned(i, 0x0) == 0)
			return new int[0];
		return new int[] {i};
	}
	
	public static int[] intArrayFromIntUnsigned(int i) {
		if (Integer.compareUnsigned(i, 0x0) == 0)
			return new int[0];
		return new int[] {i};
	}
	
	public static int[] intArrayFromLongUnsigned(long l) {
		if (Long.compareUnsigned(l, 0x0L) == 0)
			return new int[0];
		if (Long.compareUnsigned(l, 0xFFFFFFFFL) != 1)
			return new int[] {BigMath.getLower(l)};
		return new int[] {BigMath.getLower(l), BigMath.getUpper(l)};
	}
	
	//signed
	public static int[] intArrayFromByteSigned(byte i) {
		if (Integer.compareUnsigned(i, 0x0) == 0)
			return new int[0];
		return new int[] {i};
	}
	
	public static int[] intArrayFromShortSigned(short i) {
		if (Integer.compareUnsigned(i, 0x0) == 0)
			return new int[0];
		return new int[] {i};
	}
	
	public static int[] intArrayFromIntSigned(int i) {
		if (Integer.compareUnsigned(i, 0x0) == 0)
			return new int[0];
		return new int[] {i};
	}
	
	public static int[] intArrayFromLongSigned(long l) {
		if (Long.compareUnsigned(l, 0x0L) == 0)
			return new int[0];
		if (Long.compareUnsigned(l, 0xFFFFFFFFL) != 1)
			return new int[] {BigMath.getLower(l)};
		return new int[] {BigMath.getLower(l), BigMath.getUpper(l)};
	}
	
	//fixed
	public static int[] intArrayFromByteFixed(byte i2) {
		int i = Byte.toUnsignedInt(i2);
		if (Integer.compareUnsigned(i, 0x0) == 0)
			return new int[0];
		return new int[] {i << 24};
	}
	
	public static int[] intArrayFromShortFixed(short i2) {
		int i = Short.toUnsignedInt(i2);
		if (Integer.compareUnsigned(i, 0x0) == 0)
			return new int[0];
		return new int[] {i << 16};
	}
	
	public static int[] intArrayFromIntFixed(int i) {
		if (Integer.compareUnsigned(i, 0x0) == 0)
			return new int[0];
		return new int[] {i};
	}
	
	public static int[] intArrayFromLongFixed(long l) {
		if (Long.compareUnsigned(l, 0x0L) == 0)
			return new int[0];
		if (Long.compareUnsigned(l, 0xFFFFFFFFL) != 1)
			return new int[] {BigMath.getLower(l)};
		return new int[] {BigMath.getUpper(l), BigMath.getLower(l)};
	}
	
	//int[] array to primitive
	//unsigned
	public static byte byteFromIntArrayUnsigned(int[] v) {
		return v.length == 0 ? 0 : (byte) v[0];
	}
	
	public static short shortFromIntArrayUnsigned(int[] v) {
		return v.length == 0 ? 0 : (short) v[0];
	}
	
	public static int intFromIntArrayUnsigned(int[] v) {
		return v.length == 0 ? 0 : v[0];
	}
	
	public static long longFromIntArrayUnsigned(int[] v) {
		switch (v.length) {
			case 0:
				return 0;
			case 1:
				return v[0];
			default:
				return v[0] | BigMath.toUpper(v[1]);
		}
	}
	
	//signed
	public static byte byteFromIntArraySigned(int[] v, boolean sign) {
		return (byte) ((intFromIntArrayUnsigned(v) & IntegerConst.INT8MaskNumber) | (sign ? 0 : IntegerConst.INT8MaskSign));
	}
	
	public static short shortFromIntArraySigned(int[] v, boolean sign) {
		return (short) ((intFromIntArrayUnsigned(v) & IntegerConst.INT16MaskNumber) | (sign ? 0 : IntegerConst.INT16MaskSign));
	}
	
	public static int intFromIntArraySigned(int[] v, boolean sign) {
		return (intFromIntArrayUnsigned(v) & IntegerConst.INT32MaskNumber) | (sign ? 0 : IntegerConst.INT32MaskSign);
	}
	
	public static long longFromIntArraySigned(int[] v, boolean sign) {
		return (longFromIntArrayUnsigned(v) & IntegerConst.INT64MaskNumber) | (sign ? 0 : IntegerConst.INT64MaskSign);
	}
	
	//fixed
	public static byte byteFromIntArrayFixed(int[] v) {
		return v.length == 0 ? 0 : (byte) v[0];
	}
	
	public static short shortFromIntArrayFixed(int[] v) {
		return v.length == 0 ? 0 : (short) v[0];
	}
	
	public static int intFromIntArrayFixed(int[] v) {
		return v.length == 0 ? 0 : v[0];
	}
	
	public static long longFromIntArrayFixed(int[] v) {
		switch (v.length) {
			case 0:
				return 0;
			case 1:
				return BigMath.toUpper(v[0]);
			default:
				return BigMath.toUpper(v[0]) | v[1];
		}
	}
	
	//getSign
	public static boolean getSign(byte b) {
		return (b & IntegerConst.INT8MaskSign) == 0;
	}
	
	public static boolean getSign(short b) {
		return (b & IntegerConst.INT16MaskSign) == 0;
	}
	
	public static boolean getSign(int b) {
		return (b & IntegerConst.INT32MaskSign) == 0;
	}
	
	public static boolean getSign(long b) {
		return (b & IntegerConst.INT64MaskSign) == 0;
	}
}
