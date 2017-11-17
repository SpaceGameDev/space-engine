package space.util.baseobjectOld;

import space.util.baseobjectOld.exceptions.CopyNotSupportedException;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public interface Copyable {
	
	Map<Class<?>, UnaryOperator<Object>> MAP = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	static <OBJ> void putCopyFunction(Class<OBJ> clazz, UnaryOperator<OBJ> function) {
		MAP.put(clazz, (UnaryOperator<Object>) function);
	}
	
	/**
	 * this should create a copy of the object with the same values in the fields
	 * looping references will cause {@link StackOverflowError}
	 */
	@SuppressWarnings("unchecked")
	static <OBJ> OBJ copy(OBJ object) {
		if (object instanceof Object[]) {
			Object[] array = (Object[]) object;
			Object[] n = (Object[]) Array.newInstance(array.getClass().getComponentType(), array.length);
			for (int i = 0; i < array.length; i++)
				n[i] = copy(array[i]);
		}
		
		UnaryOperator<Object> func = MAP.get(object.getClass());
		if (func == null)
			throw new CopyNotSupportedException(object.getClass().getName());
		return (OBJ) func.apply(object);
	}
}
