package space.util.number.format;

import space.util.math.MathUtils;
import space.util.math.SignedMath;
import space.util.math.UnsignedMath;
import space.util.string.builder.CharBufferBuilder1DBackwards;

public class UnsignedToString implements IIntegerToString {
	
	public static IIntegerToString INSTANCE = new UnsignedToString();
	
	protected UnsignedToString() {
	}
	
	@Override
	public String toString(byte n, int radix, int cnt) {
		return toString(UnsignedMath.toInt(n), radix, cnt);
	}
	
	@Override
	public String toString(short n, int radix, int cnt) {
		return toString(UnsignedMath.toInt(n), radix, cnt);
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
	public String toStringShift(int n, int shift, int cnt) {
		int mask = (1 << shift) - 1;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards<>();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[n & mask]);
			n >>>= shift;
		}
		
		return c.toString();
	}
	
	public String toStringShift(long n, int shift, int cnt) {
		long mask = (1 << shift) - 1;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards<>();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[(int) (n & mask)]);
			n >>>= shift;
		}
		
		return c.toString();
	}
	
	//radix10
	public String toStringRadix10(int n, int cnt) {
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[UnsignedMath.remainder(n, 10)]);
			n /= 10;
		}
		
		return c.toString();
	}
	
	public String toStringRadix10(long n, int cnt) {
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[(int) UnsignedMath.remainder(n, 10)]);
			n /= 10;
		}
		
		return c.toString();
	}
	
	//radix
	public String toStringRadix(int n, int radix, int cnt) {
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[UnsignedMath.remainder(n, radix)]);
			n /= radix;
		}
		
		return c.toString();
	}
	
	public String toStringRadix(long n, int radix, int cnt) {
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards();
		for (int i = 0; n != 0 && i < cnt; i++) {
			c.append(MathUtils.DIGITS[(int) UnsignedMath.remainder(n, radix)]);
			n /= radix;
		}
		
		return c.toString();
	}
}
