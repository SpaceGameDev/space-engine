package space.util.baseobject;

import space.util.baseobject.exceptions.MakeNotSupportedException;
import space.util.baseobject.exceptions.SetNotSupportedException;
import space.util.delegate.map.specific.ThreadLocalGlobalCachingMap;

import java.util.function.BiConsumer;

public interface Setable {
	
	ThreadLocalGlobalCachingMap<Class<?>, BiConsumer<?, ?>> MAP = new ThreadLocalGlobalCachingMap<>(clazz -> null);
	
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
	 * creates an object of the same type.
	 * If there is no manual entry it will try to find an empty constructor.
	 *
	 * @param obj the {@link Object} from which {@link Class} to create a new Instance from
	 * @throws MakeNotSupportedException if creation of an new Instance failed.
	 */
	static <OBJ> void set(OBJ obj, OBJ to) {
		if (obj instanceof Setable) {
			((Setable) obj).set(to);
			return;
		}
		
		BiConsumer<?, ?> function = MAP.get(obj.getClass());
		if (function != null) {
			//noinspection unchecked
			((BiConsumer<OBJ, OBJ>) function).accept(obj, to);
			return;
		}
		throw new SetNotSupportedException(obj.getClass());
	}
	
	void set(Object obj);
}
