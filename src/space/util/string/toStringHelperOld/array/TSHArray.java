package space.util.string.toStringHelperOld.array;

import space.util.string.toStringHelperOld.ToStringHelper;

public interface TSHArray extends ToStringHelper {
	
	//array
	default String toString(byte[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default String toString(short[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default String toString(int[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default String toString(long[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default String toString(float[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default String toString(double[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default String toString(boolean[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default String toString(char[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	//array from to
	String toString(byte[] obj, int from, int to);
	
	String toString(short[] obj, int from, int to);
	
	String toString(int[] obj, int from, int to);
	
	String toString(long[] obj, int from, int to);
	
	String toString(float[] obj, int from, int to);
	
	String toString(double[] obj, int from, int to);
	
	String toString(boolean[] obj, int from, int to);
	
	String toString(char[] obj, int from, int to);
	
	//object
	default String toString(Object[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	String toString(Object[] obj, int from, int to);
}
