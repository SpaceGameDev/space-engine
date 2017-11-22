package space.util.baseobject;

import space.util.baseobject.exceptions.CopyNotSupportedException;
import space.util.delegate.map.specific.ThreadLocalGlobalCachingMap;

import java.util.function.UnaryOperator;

public interface Copyable {
	
	//FIXME: write the default method
	ThreadLocalGlobalCachingMap<Class<?>, UnaryOperator<?>> MAP = new ThreadLocalGlobalCachingMap<>(clazz -> {
		return null;
	});
	
	/**
	 * add a manual entry to the copy()-Function map
	 *
	 * @param clazz    the {@link Class} to create the entry for
	 * @param supplier the function creating the new {@link Object}
	 * @param <OBJ>    the Object-Type
	 */
	static <OBJ> void manualEntry(Class<OBJ> clazz, UnaryOperator<OBJ> supplier) {
		MAP.globalMap.put(clazz, supplier);
	}
	
	/**
	 * creates an object with a deep-copy.
	 *
	 * @param obj the {@link Class} to deep-copy
	 * @return the new copied Instance
	 * @throws CopyNotSupportedException if creation of an new copied Instance failed.
	 */
	@SuppressWarnings("unchecked")
	static <OBJ> OBJ copy(OBJ obj) throws CopyNotSupportedException {
		UnaryOperator<?> operator = MAP.get(obj.getClass());
		if (operator == null)
			throw new CopyNotSupportedException(obj.getClass());
		return ((UnaryOperator<OBJ>) operator).apply(obj);
	}
}
