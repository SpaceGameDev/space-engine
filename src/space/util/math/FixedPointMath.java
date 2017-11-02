package space.util.math;

public class FixedPointMath {
	
	public static final long bitMaskBit33 = 0x100000000L;
	
	public static int add31(int a, int b, int[] overflow) {
		int r = a + b;
		if (((a ^ r) & (b ^ r)) < 0)
			overflow[0] += (r & PrimitiveInteger.int32MaskSign) == 0 ? -1 : 1;
		return r;
	}
	
	public static int sub31(int a, int b, int[] overflow) {
		int r = a - b;
		if (((a ^ b) & (a ^ r)) < 0)
			overflow[0] += (r & PrimitiveInteger.int32MaskSign) == 0 ? -1 : 1;
		return r;
	}
	
	public static int add32(int a, int b, int[] overflow) {
		long r = (long) a + b;
		if (((a ^ r) & (b ^ r)) < 0)
			overflow[0] += (r & bitMaskBit33) == 0 ? -1 : 1;
		return (int) r;
	}
	
	public static int sub32(int a, int b, int[] overflow) {
		long r = (long) a - b;
		if (((a ^ b) & (a ^ r)) < 0)
			overflow[0] += (r & bitMaskBit33) == 0 ? -1 : 1;
		return (int) r;
	}
	
	public static long add63(long a, long b, int[] overflow) {
		long r = a + b;
		if (((a ^ r) & (b ^ r)) < 0)
			overflow[0] += (r & PrimitiveInteger.int64MaskSign) == 0 ? -1 : 1;
		return r;
	}
	
	public static long sub63(long a, long b, int[] overflow) {
		long r = a - b;
		if (((a ^ b) & (a ^ r)) < 0)
			overflow[0] += (r & PrimitiveInteger.int64MaskSign) == 0 ? -1 : 1;
		return r;
	}
	
	public static long add64(long a, long b, int[] overflow) {
		int al = BigMath.getLower(a);
		int au = BigMath.getUpper(a);
		int bl = BigMath.getLower(b);
		int bu = BigMath.getUpper(b);
		
		long ret;
		
		long temp1 = bitMaskBit33 + al + bl;
		ret = BigMath.getLower(temp1);
		
		long temp2 = bitMaskBit33 + au + bu + (BigMath.getUpper(temp1) - 1);
		ret += temp2 << 32;
		
		overflow[0] += BigMath.getUpper(temp2) - 1;
		
		return ret;
	}
	
	public static long sub64(long a, long b, int[] overflow) {
		int al = BigMath.getLower(a);
		int au = BigMath.getUpper(a);
		int bl = BigMath.getLower(b);
		int bu = BigMath.getUpper(b);
		
		long ret;
		
		long temp1 = bitMaskBit33 + al - bl;
		ret = BigMath.getLower(temp1);
		
		long temp2 = bitMaskBit33 + au - bu + (BigMath.getUpper(temp1) - 1);
		ret += BigMath.toUpper(temp2);
		
		overflow[0] += BigMath.getUpper(temp2) - 1;
		
		return ret;
	}
}
