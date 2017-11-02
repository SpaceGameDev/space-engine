package space.engine.number.format;

import space.util.string.builder.CharBufferBuilder1D;
import space.util.string.builder.IStringBuilder;

public interface IIntegerToString {
	
	//int
	default String toString(int n) {
		return toString(new CharBufferBuilder1D(), n).toString();
	}
	
	default String toString(int n, int radix) {
		return toString(new CharBufferBuilder1D(), n, radix).toString();
	}
	
	default IStringBuilder<?> toString(IStringBuilder<?> b, int n) {
		return toString(b, n, 10, Integer.MAX_VALUE);
	}
	
	default IStringBuilder<?> toString(IStringBuilder<?> b, int n, int radix) {
		return toString(b, n, radix, Integer.MAX_VALUE);
	}
	
	IStringBuilder<?> toString(IStringBuilder<?> b, int n, int radix, int cnt);
	
	//long
	default String toString(long n) {
		return toString(new CharBufferBuilder1D(), n).toString();
	}
	
	default String toString(long n, int radix) {
		return toString(new CharBufferBuilder1D<>(), n, radix).toString();
	}
	
	default IStringBuilder<?> toString(IStringBuilder<?> b, long n) {
		return toString(b, n, 10, Integer.MAX_VALUE);
	}
	
	default IStringBuilder<?> toString(IStringBuilder<?> b, long n, int radix) {
		return toString(b, n, radix, Integer.MAX_VALUE);
	}
	
	IStringBuilder<?> toString(IStringBuilder<?> b, long n, int radix, int cnt);
}
