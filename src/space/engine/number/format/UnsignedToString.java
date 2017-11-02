package space.engine.number.format;

import space.util.math.UnsignedMath;
import space.util.string.builder.CharBufferBuilder1DBackwards;
import space.util.string.builder.IStringBuilder;

public class UnsignedToString implements IIntegerToString {
	
	public FormatterUtil formatter;
	
	public UnsignedToString() {
	}
	
	public UnsignedToString(FormatterUtil formatter) {
		this.formatter = formatter;
	}
	
	@Override
	public IStringBuilder<?> toString(IStringBuilder<?> b, int n, int radix, int cnt) {
		if (radix == 10)
			return toStringRadix10(b, n, cnt);
		if (UnsignedMath.isPowerOfTwoFast(radix))
			return toStringShift(b, n, UnsignedMath.getPowerOfTwoFloor(radix), cnt);
		return toStringDefImpl(b, n, radix, cnt);
	}
	
	@Override
	public IStringBuilder<?> toString(IStringBuilder<?> b, long n, int radix, int cnt) {
		if (radix == 10)
			return toStringRadix10(b, n, cnt);
		if (UnsignedMath.isPowerOfTwoFast(radix))
			return toStringShift(b, n, UnsignedMath.getPowerOfTwoFloor(radix), cnt);
		return toStringDefImpl(b, n, radix, cnt);
	}
	
	//shifting
	public IStringBuilder<?> toStringShift(IStringBuilder<?> b, int n, int shift, int cnt) {
		int mask = (1 << shift) - 1;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; n != 0; n >>>= shift)
			c.append(formatter.digits[n & mask]);
		
		b.append(c.toString());
		return b;
	}
	
	public IStringBuilder<?> toStringShift(IStringBuilder<?> b, long n, int shift, int cnt) {
		int mask = (1 << shift) - 1;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards<>(l);
		
		for (; n != 0; n >>>= shift)
			c.append(formatter.digits[(int) (n & mask)]);
		
		b.append(c.toString());
		return b;
	}
	
	//radix10
	public IStringBuilder<?> toStringRadix10(IStringBuilder<?> b, int n, int cnt) {
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; UnsignedMath.isBiggerEquals(n, 1000); n = UnsignedMath.divide(n, 1000)) {
			int rem = UnsignedMath.remainder(n, 1000);
			c.ensureCapacity(3);
			
			c.append(formatter.digits[rem % 10]);
			rem /= 10;
			c.append(formatter.digits[rem % 10]);
			rem /= 10;
			c.append(formatter.digits[rem]);
		}
		
		for (; n != 0; n = UnsignedMath.divide(n, 10))
			c.append(formatter.digits[UnsignedMath.remainder(n, 10)]);
		
		b.append(c.toString());
		return b;
	}
	
	public IStringBuilder<?> toStringRadix10(IStringBuilder<?> b, long n, int cnt) {
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; UnsignedMath.isBiggerEquals(n, 1000); n = UnsignedMath.divide(n, 1000)) {
			long rem = UnsignedMath.remainder(n, 1000);
			c.ensureCapacity(3);
			
			c.append(formatter.digits[(int) (rem % 10)]);
			rem /= 10;
			c.append(formatter.digits[(int) (rem % 10)]);
			rem /= 10;
			c.append(formatter.digits[(int) rem]);
		}
		
		for (; n != 0; n = UnsignedMath.divide(n, 10)) {
			c.append(formatter.digits[(int) UnsignedMath.remainder(n, 10)]);
		}
		
		b.append(c.toString());
		return b;
	}
	
	//DefImpl
	public IStringBuilder<?> toStringDefImpl(IStringBuilder<?> b, int n, int radix, int cnt) {
		int i = 0;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; n != 0; n = UnsignedMath.divide(n, radix))
			c.append(formatter.digits[UnsignedMath.remainder(n, radix)]);
		
		b.append(c.toString());
		return b;
	}
	
	public IStringBuilder<?> toStringDefImpl(IStringBuilder<?> b, long n, int radix, int cnt) {
		int i = 0;
		int l = formatter.charCacheSize;
		CharBufferBuilder1DBackwards c = new CharBufferBuilder1DBackwards(l);
		
		for (; n != 0; n = UnsignedMath.divide(n, radix))
			c.append(formatter.digits[(int) UnsignedMath.remainder(n, radix)]);
		
		b.append(c.toString());
		return b;
	}
}
