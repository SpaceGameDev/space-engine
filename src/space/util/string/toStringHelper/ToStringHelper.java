package space.util.string.toStringHelper;

import space.util.baseobject.ToString;
import space.util.indexmap.IndexMapArray;
import space.util.indexmap.multi.IndexMultiMap;
import space.util.indexmap.multi.IndexMultiMap2D;
import space.util.indexmap.multi.IndexMultiMapFrom1DIndexMap;
import space.util.indexmap.multi.IndexMultiMapLayered.IndexMultiMapLayeredImpl;
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
	
	//object
	default T toString(Object o) {
		//null
		if (o == null)
			return toStringNull();
		
		//string
		if (o instanceof String)
			return toString((String) o);
		if (o instanceof CharSequence)
			return toString((CharSequence2D) o);
		if (o instanceof String2D)
			return toString((String2D) o);
		if (o instanceof CharSequence2D)
			return toString((CharSequence2D) o);
		
		//native autoboxing
		if (o instanceof Byte)
			return toString(((Byte) o).byteValue());
		if (o instanceof Short)
			return toString(((Short) o).shortValue());
		if (o instanceof Integer)
			return toString(((Integer) o).intValue());
		if (o instanceof Long)
			return toString(((Long) o).longValue());
		if (o instanceof Float)
			return toString(((Float) o).floatValue());
		if (o instanceof Double)
			return toString(((Double) o).doubleValue());
		if (o instanceof Boolean)
			return toString(((Boolean) o).booleanValue());
		if (o instanceof Character)
			return toString(((Character) o).charValue());
		
		//native array
		if (o instanceof byte[])
			return toString((byte[]) o);
		if (o instanceof short[])
			return toString((short[]) o);
		if (o instanceof int[])
			return toString((int[]) o);
		if (o instanceof long[])
			return toString((long[]) o);
		if (o instanceof float[])
			return toString((float[]) o);
		if (o instanceof double[])
			return toString((double[]) o);
		if (o instanceof boolean[])
			return toString((boolean[]) o);
		if (o instanceof char[])
			return toString((char[]) o);
		
		return ToString.toTSH(this, o);
	}
	
	//modifier
	T createModifier(String modifier, Object value);
	
	//objects
	
	/**
	 * an object mapper mapping names of fields to values
	 */
	ToStringHelperObjectsInstance<T> createObjectInstance(Object obj);
	
	/**
	 * an object mapper mapping names of fields to values
	 */
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
	
	//mapper
	
	/**
	 * mappers map one thing (pos[1] = 0) to another (pos[1] = 1)
	 */
	ToStringHelperTable<T> createMapper(String name, String separator, boolean align);
	
	//tables
	
	/**
	 * tables can be infinite in any of their (infinite) dimensions
	 */
	ToStringHelperTable<T> createTable(String name, int dimensions);
	
	interface ToStringHelperTable<T> {
		
		Object put(int[] pos, Object object);
		
		T build();
	}
	
	static <VALUE> IndexMultiMap<VALUE> getOptimalMultiMap(int dimensions) {
		if (dimensions <= 0)
			throw new IllegalArgumentException("Dimension " + dimensions + " <= 0");
		
		switch (dimensions) {
			case 1:
				return new IndexMultiMapFrom1DIndexMap<>(new IndexMapArray<>());
			case 2:
				return new IndexMultiMap2D<>();
			default:
				return new IndexMultiMapLayeredImpl<>();
		}
	}
}
