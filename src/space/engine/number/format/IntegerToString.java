package space.engine.number.format;

import space.util.math.MathUtils;
import space.util.math.SignedMath;
import space.util.string.builder.CharBufferBuilder1DBackwards;
import space.util.string.builder.IStringBuilder;

public class IntegerToString implements IIntegerToString {
	
	public FormatterUtil formatter;
	
	public IntegerToString() {
	}
	
	public IntegerToString(FormatterUtil formatter) {
		this.formatter = formatter;
	}
	
	@Override
	public IStringBuilder<?> toString(IStringBuilder<?> b, int n, int radix, int cnt) {
		if (radix == 10)
			return toStringRadix10(b, n, cnt);
		if (n != Integer.MAX_VALUE && SignedMath.isPowerOfTwoFast(radix))
			return toStringShift(b, n, SignedMath.getPowerOfTwoFloor(radix), cnt);
		return toStringDefImpl(b, n, radix, cnt);
	}
	
	@Override
	public IStringBuilder<?> toString(IStringBuilder<?> b, long n, int radix, int cnt) {
		if (radix == 10)
			return toStringRadix10(b, n, cnt);
		if (n != Long.MAX_VALUE && SignedMath.isPowerOfTwoFast(radix))
			return toStringShift(b, n, SignedMath.getPowerOfTwoFloor(radix), cnt);
		return toStringDefImpl(b, n, radix, cnt);
	}
	
	//shifting
	//doesn't work with MAX_VALUE
	public IStringBuilder<?> toStringShift(IStringBuilder<?> b, int n, int shift, int cnt) {
		boolean neg = n < 0;
		if (neg)
			n = -n;
		
		int mask = (1 << shift) - 1;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards<>(l);
		
		for (; n != 0; n >>>= shift)
			c.append(formatter.digits[n & mask]);
		
		if (neg)
			b.append(formatter.minus);
		b.append(c.toString());
		return b;
	}
	
	public IStringBuilder<?> toStringShift(IStringBuilder<?> b, long n, int shift, int cnt) {
		boolean neg = n < 0;
		if (neg)
			n = -n;
		
		long mask = (1 << shift) - 1;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; n != 0; n >>>= shift)
			c.append(formatter.digits[(int) (n & mask)]);
		
		if (neg)
			b.append(formatter.minus);
		b.append(c.toString());
		return b;
	}
	
	//radix10
	public IStringBuilder<?> toStringRadix10(IStringBuilder<?> b, int n, int cnt) {
		boolean neg = n < 0;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; n >= 1000; n /= 1000) {
			int rem = n % 1000;
			c.ensureCapacity(3);
			
			c.append(formatter.digits[MathUtils.abs(rem % 10)]);
			rem /= 10;
			c.append(formatter.digits[MathUtils.abs(rem % 10)]);
			rem /= 10;
			c.append(formatter.digits[MathUtils.abs(rem)]);
		}
		
		for (; n != 0; n /= 10)
			c.append(formatter.digits[MathUtils.abs(n % 10)]);
		
		if (neg)
			b.append(formatter.minus);
		b.append(c.toString());
		return b;
	}
	
	public IStringBuilder<?> toStringRadix10(IStringBuilder<?> b, long n, int cnt) {
		boolean neg = n < 0;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; n >= 1000; n /= 1000) {
			long rem = n % 1000;
			c.ensureCapacity(3);
			
			c.append(formatter.digits[(int) MathUtils.abs(rem % 10)]);
			rem /= 10;
			c.append(formatter.digits[(int) MathUtils.abs(rem % 10)]);
			rem /= 10;
			c.append(formatter.digits[(int) MathUtils.abs(rem)]);
		}
		
		for (; n != 0; n /= 10)
			c.append(formatter.digits[(int) MathUtils.abs(n % 10)]);
		
		if (neg)
			b.append(formatter.minus);
		b.append(c.toString());
		return b;
	}
	
	//DefImpl
	public IStringBuilder<?> toStringDefImpl(IStringBuilder<?> b, int n, int radix, int cnt) {
		int i = 0;
		boolean neg = n < 0;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; n != 0; n /= radix)
			c.append(formatter.digits[MathUtils.abs(n % radix)]);
		
		if (neg)
			b.append(formatter.minus);
		b.append(c.toString());
		return b;
	}
	
	public IStringBuilder<?> toStringDefImpl(IStringBuilder<?> b, long n, int radix, int cnt) {
		int i = 0;
		boolean neg = n < 0;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; n != 0; n /= radix)
			c.append(formatter.digits[(int) MathUtils.abs(n % radix)]);
		
		if (neg)
			b.append(formatter.minus);
		b.append(c.toString());
		return b;
	}
}
