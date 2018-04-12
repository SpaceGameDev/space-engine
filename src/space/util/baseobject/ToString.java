package space.util.baseobject;

import space.util.string.toStringHelper.ToStringHelper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public interface ToString {
	
	ConcurrentHashMap<Class<?>, BiFunction<ToStringHelper<?>, ?, Object>> MAP = new ConcurrentHashMap<>();
	
	//functions
	
	/**
	 * add a manual entry to the toTSH()-Function map
	 *
	 * @param clazz    the {@link Class} to create the entry for
	 * @param function the function converting {@link Object}s into {@link ToStringHelper}-return-types
	 * @param <OBJ>    the Object-Type
	 */
	static <OBJ> void manualEntry(Class<OBJ> clazz, BiFunction<ToStringHelper<?>, ? super OBJ, Object> function) {
		MAP.put(clazz, function);
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
		
		BiFunction<ToStringHelper<?>, ?, Object> function = MAP.get(obj.getClass());
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
}
