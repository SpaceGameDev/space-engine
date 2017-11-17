package space.util.baseobjectOld;

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
	
	@SuppressWarnings("unchecked")
	static <T> BiFunction<ToStringHelper<T>, Object, T> get(Class<?> clazz) {
		BiFunction<ToStringHelper<?>, Object, Object> func = MAP.get(clazz);
		return func == null ? null : (BiFunction<ToStringHelper<T>, Object, T>) (Object) func;
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
