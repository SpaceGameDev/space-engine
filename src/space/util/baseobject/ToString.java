package space.util.baseobject;

import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public interface ToString {
	
	Map<Class<?>, BiFunction<ToStringHelperCollection, Object, ToStringHelperCollection>> MAP = new HashMap<>();
	
	//putFunction
	@SuppressWarnings("unchecked")
	static <OBJ> void putToTSHFunction(Class<OBJ> clazz, BiFunction<ToStringHelperCollection, OBJ, ToStringHelperCollection> function) {
		MAP.put(clazz, (BiFunction<ToStringHelperCollection, Object, ToStringHelperCollection>) function);
	}
	
	static Object toTSH(ToStringHelperCollection api, Object obj) {
		if (obj instanceof ToString)
			return ((ToString) obj).toTSH();
		BiFunction<ToStringHelperCollection, Object, ToStringHelperCollection> func = MAP.get(obj.getClass());
		if (func != null)
			return func.apply(api, obj);
		return api.getObjectPhaser().getInstance(obj);
	}
	
	//non-static
	ToStringHelperInstance toTSH(ToStringHelperCollection api);
	
	default ToStringHelperInstance toTSH() {
		return toTSH(ToStringHelper.get());
	}
	
	default String toString0() {
		return toTSH().toString();
	}
}
