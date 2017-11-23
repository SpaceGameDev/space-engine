package space.util.baseobject;

import space.util.baseobject.exceptions.SetNotSupportedException;
import space.util.delegate.map.specific.ThreadLocalGlobalCachingMap;

import java.util.function.BiConsumer;

public interface Setable {
	
	ThreadLocalGlobalCachingMap<Class<?>, BiConsumer<?, ?>> MAP = new ThreadLocalGlobalCachingMap<>(clazz -> {
		if (Setable.class.isAssignableFrom(clazz))
			return (Setable obj, Setable to) -> obj.set(to);
		
		return null;
	});
	
	/**
	 * add a manual entry to the set()-Function map
	 *
	 * @param clazz       the {@link Class} to create the entry for
	 * @param setFunction the function creating the new {@link Object}
	 * @param <OBJ>       the Object-Type
	 */
	static <OBJ> void manualEntry(Class<OBJ> clazz, BiConsumer<OBJ, OBJ> setFunction) {
		MAP.globalMap.put(clazz, setFunction);
	}
	
	/**
	 * sets one obj to the values of another
	 *
	 * @param obj the {@link Object} being set to a different state
	 * @param to  the {@link Object} having a certain state
	 * @throws SetNotSupportedException if setting failed
	 */
	static <OBJ> void set(OBJ obj, OBJ to) throws SetNotSupportedException {
		BiConsumer<?, ?> function = MAP.get(obj.getClass());
		if (function != null) {
			//noinspection unchecked
			((BiConsumer<OBJ, OBJ>) function).accept(obj, to);
			return;
		}
		throw new SetNotSupportedException(obj.getClass());
	}
	
	/**
	 * sets one obj to the values of another
	 *
	 * @param obj the {@link Object} being set to a different state
	 * @param to  the {@link Object} having a certain state
	 * @throws SetNotSupportedException if setting failed
	 */
	static <OBJ extends Setable> void set(OBJ obj, OBJ to) throws SetNotSupportedException {
		obj.set(to);
	}
	
	void set(Object obj) throws SetNotSupportedException;
}
