package space.util.baseobject;

import space.util.string.toStringHelper.ToStringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public interface ToString {
	
	Map<Class<?>, BiFunction<ToStringHelper<?>, Object, Object>> MAP = new HashMap<>();
	
	//putFunction
	@SuppressWarnings("unchecked")
	static <OBJ> void putToTSHFunction(Class<OBJ> clazz, BiFunction<ToStringHelper<?>, OBJ, Object> function) {
		MAP.put(clazz, (BiFunction<ToStringHelper<?>, Object, Object>) function);
	}
	
	static <T> T toTSH(ToStringHelper<T> api, Object obj) {
		if (obj instanceof ToString)
			return ((ToString) obj).toTSH(api);
		//noinspection unchecked
		BiFunction<ToStringHelper<T>, Object, T> func = (BiFunction<ToStringHelper<T>, Object, T>) (Object) MAP.get(obj.getClass());
		if (func != null)
			return func.apply(api, obj);
		return api.createObjectInstance(obj).build();
	}
	
	//non-static
	<T> T toTSH(ToStringHelper<T> api);
	
	default Object toTSH() {
		return toTSH(ToStringHelper.getDefault());
	}
	
	default String toString0() {
		return toTSH().toString();
	}
}
