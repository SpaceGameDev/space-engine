package space.util.number.format;

public interface IIntegerToString {
	
	//int
	default String toString(int n) {
		return toString(n, 10, Integer.MAX_VALUE);
	}
	
	default String toString(int n, int radix) {
		return toString(n, radix, Integer.MAX_VALUE);
	}
	
	String toString(int n, int radix, int cnt);
	
	//long
	default String toString(long n) {
		return toString(n, 10, Integer.MAX_VALUE);
	}
	
	default String toString(long n, int radix) {
		return toString(n, radix, Integer.MAX_VALUE);
	}
	
	String toString(long n, int radix, int cnt);
}
