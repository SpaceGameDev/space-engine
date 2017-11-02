package space.util.conversion;

import space.util.string.CharSequence2D;
import space.util.string.String2D;

public interface ToObject<T> {
	
	//primitive
	T to(byte obj);
	
	T to(short obj);
	
	T to(int obj);
	
	T to(long obj);
	
	T to(float obj);
	
	T to(double obj);
	
	T to(boolean obj);
	
	T to(char obj);
	
	//array
	default T to(byte[] obj) {
		return to(obj, 0, obj.length);
	}
	
	default T to(short[] obj) {
		return to(obj, 0, obj.length);
	}
	
	default T to(int[] obj) {
		return to(obj, 0, obj.length);
	}
	
	default T to(long[] obj) {
		return to(obj, 0, obj.length);
	}
	
	default T to(float[] obj) {
		return to(obj, 0, obj.length);
	}
	
	default T to(double[] obj) {
		return to(obj, 0, obj.length);
	}
	
	default T to(boolean[] obj) {
		return to(obj, 0, obj.length);
	}
	
	default T to(char[] obj) {
		return to(obj, 0, obj.length);
	}
	
	//array from to
	T to(byte[] obj, int from, int to);
	
	T to(short[] obj, int from, int to);
	
	T to(int[] obj, int from, int to);
	
	T to(long[] obj, int from, int to);
	
	T to(float[] obj, int from, int to);
	
	T to(double[] obj, int from, int to);
	
	T to(boolean[] obj, int from, int to);
	
	T to(char[] obj, int from, int to);
	
	//object
	T to(Object obj);
	
	default T to(Object[] obj) {
		return to(obj, 0, obj.length);
	}
	
	T to(Object[] obj, int from, int to);
	
	//extra
	default T to(String obj) {
		return to((CharSequence) obj);
	}
	
	default T to(CharSequence obj) {
		return to((Object) obj);
	}
	
	default T to(String2D obj) {
		return to((CharSequence2D) obj);
	}
	
	default T to(CharSequence2D obj) {
		return to((Object) obj);
	}
}
