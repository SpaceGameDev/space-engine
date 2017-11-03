package space.util.string.toStringHelper;

import space.util.string.CharSequence2D;
import space.util.string.String2D;

import java.util.function.Supplier;

public interface ToStringHelper<T> {
	
	static ToStringHelper<?> getDefault() {
		return ToStringHelperGetter.DEFAULT.get();
	}
	
	static void setDefault(Supplier<ToStringHelper<?>> tsh) {
		ToStringHelperGetter.DEFAULT = tsh;
	}
	
	static void setDefault(ToStringHelper<?> tsh) {
		ToStringHelperGetter.DEFAULT = () -> tsh;
	}
	
	//native
	T toString(byte b);
	
	T toString(short s);
	
	T toString(int i);
	
	T toString(long l);
	
	T toString(float f);
	
	T toString(double d);
	
	T toString(boolean b);
	
	T toString(char c);
	
	T toString(Object o);
	
	//array
	default T toString(byte[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default T toString(short[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default T toString(int[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default T toString(long[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default T toString(float[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default T toString(double[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default T toString(boolean[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	default T toString(char[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	//array from to
	T toString(byte[] obj, int from, int to);
	
	T toString(short[] obj, int from, int to);
	
	T toString(int[] obj, int from, int to);
	
	T toString(long[] obj, int from, int to);
	
	T toString(float[] obj, int from, int to);
	
	T toString(double[] obj, int from, int to);
	
	T toString(boolean[] obj, int from, int to);
	
	T toString(char[] obj, int from, int to);
	
	//array object
	default T toString(Object[] obj) {
		return toString(obj, 0, obj.length);
	}
	
	T toString(Object[] obj, int from, int to);
	
	//modifier
	T createModifier(String modifier, Object value);
	
	//objects
	ToStringHelperObjectsInstance<T> createObjectInstance(Object obj);
	
	interface ToStringHelperObjectsInstance<T> {
		
		//primitive
		void add(String name, byte obj);
		
		void add(String name, short obj);
		
		void add(String name, int obj);
		
		void add(String name, long obj);
		
		void add(String name, float obj);
		
		void add(String name, double obj);
		
		void add(String name, boolean obj);
		
		void add(String name, char obj);
		
		//array
		default void add(String name, byte[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, short[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, int[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, long[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, float[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, double[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, boolean[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		default void add(String name, char[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		//array from to
		void add(String name, byte[] obj, int from, int to);
		
		void add(String name, short[] obj, int from, int to);
		
		void add(String name, int[] obj, int from, int to);
		
		void add(String name, long[] obj, int from, int to);
		
		void add(String name, float[] obj, int from, int to);
		
		void add(String name, double[] obj, int from, int to);
		
		void add(String name, boolean[] obj, int from, int to);
		
		void add(String name, char[] obj, int from, int to);
		
		//object
		void add(String name, Object obj);
		
		default void add(String name, Object[] obj) {
			add(name, obj, 0, obj.length);
		}
		
		void add(String name, Object[] obj, int from, int to);
		
		//extra
		default void add(String name, String obj) {
			add(name, (CharSequence) obj);
		}
		
		default void add(String name, CharSequence obj) {
			add(name, (Object) obj);
		}
		
		default void add(String name, String2D obj) {
			add(name, (CharSequence2D) obj);
		}
		
		default void add(String name, CharSequence2D obj) {
			add(name, (Object) obj);
		}
		
		T build();
	}
}
