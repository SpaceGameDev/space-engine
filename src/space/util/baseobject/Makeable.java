package space.util.baseobject;

import space.util.baseobject.exceptions.MakeNotSupportedException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public interface Makeable {
	
	Map<Class<?>, Supplier<Object>> MAP = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	static <OBJ> void putMakeFunction(Class<OBJ> clazz, Supplier<OBJ> function) {
		MAP.put(clazz, (Supplier<Object>) function);
	}
	
	/**
	 * this will create an object of the same type
	 */
	@SuppressWarnings("unchecked")
	static <OBJ> OBJ make(OBJ object) {
		Supplier<?> func = MAP.get(object.getClass());
		if (func == null)
			throw new MakeNotSupportedException(object.getClass().getName());
		return (OBJ) func.get();
	}
}
