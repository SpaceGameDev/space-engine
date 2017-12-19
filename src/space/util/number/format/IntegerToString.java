package space.util.number.format;

import space.util.math.MathUtils;
import space.util.math.SignedMath;
import space.util.string.builder.CharBufferBuilder1DBackwards;

public class IntegerToString implements IIntegerToString {
	
	public static IIntegerToString INSTANCE = new IntegerToString();
	
	protected IntegerToString() {
	}
	
	@Override
	public String toString(byte n, int radix, int cnt) {
		return toString((int) n, radix, cnt);
	}
	
	@Override
	public String toString(short n, int radix, int cnt) {
		return toString((int) n, radix, cnt);
	}
	
	@Override
	public String toString(int n, int radix, int cnt) {
		if (radix == 10)
			return toStringRadix10(n, cnt);
		if (n != Integer.MIN_VALUE && SignedMath.isPowerOfTwo(radix))
			return toStringShift(n, SignedMath.getPowerOfTwoFloor(radix), cnt);
		return toStringRadix(n, radix, cnt);
	}
	
	@Override
	public String toString(long n, int radix, int cnt) {
		if (radix == 10)
			return toStringRadix10(n, cnt);
		if (n != Long.MIN_VALUE && SignedMath.isPowerOfTwo(radix))
			return toStringShift(n, SignedMath.getPowerOfTwoFloor(radix), cnt);
		return toStringRadix(n, radix, cnt);
	}
	
	//shift
	//doesn't work with MIN_VALUE
	public String toStringShift(int n, int shift, int cnt) {
		boolean neg = n < 0;
		if (neg)
			n = -n;
		
		int mask = (1 << shift) - 1;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards<>();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[n & mask]);
			n >>>= shift;
		}
		
		return neg ? "-" + c.toString() : c.toString();
	}
	
	public String toStringShift(long n, int shift, int cnt) {
		boolean neg = n < 0;
		if (neg)
			n = -n;
		
		long mask = (1 << shift) - 1;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[(int) (n & mask)]);
			n >>>= shift;
		}
		
		return neg ? "-" + c.toString() : c.toString();
	}
	
	//radix10
	public String toStringRadix10(int n, int cnt) {
		boolean neg = n < 0;
		
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[MathUtils.abs(n % 10)]);
			n /= 10;
		}
		
		return neg ? "-" + c.toString() : c.toString();
	}
	
	public String toStringRadix10(long n, int cnt) {
		boolean neg = n < 0;
		
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[(int) MathUtils.abs(n % 10)]);
			n /= 10;
		}
		
		return neg ? "-" + c.toString() : c.toString();
	}
	
	//radix
	public String toStringRadix(int n, int radix, int cnt) {
		boolean neg = n < 0;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards();
		
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[MathUtils.abs(n % radix)]);
			n /= radix;
		}
		
		return neg ? "-" + c.toString() : c.toString();
	}
	
	public String toStringRadix(long n, int radix, int cnt) {
		boolean neg = n < 0;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards();
		
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[(int) MathUtils.abs(n % radix)]);
			n /= radix;
		}
		
		return neg ? "-" + c.toString() : c.toString();
	}
}
