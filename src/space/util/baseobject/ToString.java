package space.util.baseobject;

import space.util.delegate.map.BufferedMap;
import space.util.delegate.map.specific.ThreadLocalGlobalCachingMap;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public interface ToString {
	
	//init
	/**
	 * used to have a static-init-function in the interface
	 */
	byte zero = BaseObjectInit.init2();
	
	static void init() {
		ToStringClass.init();
	}
	
	//functions
	
	/**
	 * add a manual entry to the toTSH()-Function map
	 *
	 * @param clazz    the {@link Class} to create the entry for
	 * @param function the function converting {@link Object}s into {@link ToStringHelper}-return-types
	 * @param <OBJ>    the Object-Type
	 */
	static <OBJ> void manualEntry(Class<OBJ> clazz, BiFunction<ToStringHelper<?>, ? super OBJ, Object> function) {
		ToStringClass.WRITE_MAP.put(clazz, function);
	}
	
	/**
	 * get the TSH-return-type of a specific object.
	 *
	 * @param api the {@link ToStringHelper} which should be used
	 * @param obj the {@link Object} which should be turned into a String
	 * @return the TSH-return-type Object
	 */
	static <OBJ, T> T toTSH(ToStringHelper<T> api, OBJ obj) {
		if (obj instanceof ToString)
			return ((ToString) obj).toTSH(api);
		
		BiFunction<ToStringHelper<?>, ?, Object> function = ToStringClass.MAP.get(obj.getClass());
		if (function != null)
			//noinspection unchecked
			return (T) ((BiFunction<ToStringHelper<?>, OBJ, Object>) function).apply(api, obj);
		
		return api.toString(obj.toString());
	}
	
	//implementable
	
	/**
	 * get the TSH-return-type of a specific object.
	 *
	 * @param api the {@link ToStringHelper} which should be used
	 * @return the TSH-return-type Object
	 */
	<T> T toTSH(ToStringHelper<T> api);
	
	/**
	 * get the TSH-return-type of a specific object.
	 * The default {@link ToStringHelper} API will be used.
	 *
	 * @return the TSH-return-type Object
	 */
	default Object toTSH() {
		return toTSH(ToStringHelper.getDefault());
	}
	
	default String toString0() {
		return toTSH().toString();
	}
	
	//class
	class ToStringClass {
		
		//maps
		private static volatile Map<Class<?>, BiFunction<ToStringHelper<?>, ?, Object>> WRITE_MAP = new BufferedMap<>(new HashMap<>());
		public static ThreadLocalGlobalCachingMap<Class<?>, BiFunction<ToStringHelper<?>, ?, Object>> MAP;
		
		//init
		static {
			BaseObjectInit.init();
		}
		
		static void init() {
			if (!(WRITE_MAP instanceof BufferedMap))
				throw new IllegalStateException("already initialized!");
			
			MAP = new ThreadLocalGlobalCachingMap<>();
			
			((BufferedMap<Class<?>, BiFunction<ToStringHelper<?>, ?, Object>>) WRITE_MAP).setSink(MAP.globalMap);
			WRITE_MAP = MAP.globalMap;
		}
	}
}
