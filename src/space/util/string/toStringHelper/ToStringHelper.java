package space.util.string.toStringHelper;

import space.util.string.CharSequence2D;
import space.util.string.String2D;

import java.util.function.Supplier;

/**
 * a helper class for creating {@link String}s for {@link Object}s
 *
 * @param <T> type of return from the {@link ToStringHelper}
 */
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
		return obj == null ? toStringNull() : toString(obj, 0, obj.length);
	}
	
	default T toString(short[] obj) {
		return obj == null ? toStringNull() : toString(obj, 0, obj.length);
	}
	
	default T toString(int[] obj) {
		return obj == null ? toStringNull() : toString(obj, 0, obj.length);
	}
	
	default T toString(long[] obj) {
		return obj == null ? toStringNull() : toString(obj, 0, obj.length);
	}
	
	default T toString(float[] obj) {
		return obj == null ? toStringNull() : toString(obj, 0, obj.length);
	}
	
	default T toString(double[] obj) {
		return obj == null ? toStringNull() : toString(obj, 0, obj.length);
	}
	
	default T toString(boolean[] obj) {
		return obj == null ? toStringNull() : toString(obj, 0, obj.length);
	}
	
	default T toString(char[] obj) {
		return obj == null ? toStringNull() : toString(obj, 0, obj.length);
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
	
	//String
	T toString(CharSequence str);
	
	T toString(String str);
	
	T toString(CharSequence2D str);
	
	T toString(String2D str);
	
	//null
	T toStringNull();
	
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
			if (obj == null)
				addNull(name);
			else
				add(name, obj, 0, obj.length);
		}
		
		default void add(String name, short[] obj) {
			if (obj == null)
				addNull(name);
			else
				add(name, obj, 0, obj.length);
		}
		
		default void add(String name, int[] obj) {
			if (obj == null)
				addNull(name);
			else
				add(name, obj, 0, obj.length);
		}
		
		default void add(String name, long[] obj) {
			if (obj == null)
				addNull(name);
			else
				add(name, obj, 0, obj.length);
		}
		
		default void add(String name, float[] obj) {
			if (obj == null)
				addNull(name);
			else
				add(name, obj, 0, obj.length);
		}
		
		default void add(String name, double[] obj) {
			if (obj == null)
				addNull(name);
			else
				add(name, obj, 0, obj.length);
		}
		
		default void add(String name, boolean[] obj) {
			if (obj == null)
				addNull(name);
			else
				add(name, obj, 0, obj.length);
		}
		
		default void add(String name, char[] obj) {
			if (obj == null)
				addNull(name);
			else
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
		
		void addNull(String name);
		
		default void add(String name, Object[] obj) {
			if (obj == null)
				addNull(name);
			else
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
	
	//tables
	ToStringHelperTable<T> createTable(Object name, int dimensions);
	
	interface ToStringHelperTable<T> {
		
		/**
		 * adds a separator inbetween the table
		 *
		 * @param pos       the position to start at
		 * @param direction the direction the separator should go.
		 *                  Most implementations can only go sideways.
		 * @param multiple  an offset to position marking an area where all separators should be set.
		 *                  If this value can longer than all entries, in this case it will only print as many as needed for all entries.
		 * @param separator the separator itself
		 * @param align     is the separators should be aligned with one another or can be anywhere, implementation specific
		 */
		void setSeparator(int[] pos, int[] direction, int[] multiple, String2D separator, boolean align);
		
		void put(int[] pos, T object);
		
		T build();
	}
}
