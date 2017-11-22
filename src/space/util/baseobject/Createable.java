package space.util.baseobject;

import space.util.baseobject.exceptions.MakeNotSupportedException;
import space.util.delegate.map.specific.ThreadLocalGlobalCachingMap;
import space.util.mh.LambdaMetafactoryUtil;

import java.util.function.Supplier;

import static space.util.GetClass.gClass;

public interface Createable {
	
	ThreadLocalGlobalCachingMap<Class<?>, Supplier<?>> MAP = new ThreadLocalGlobalCachingMap<>(clazz -> {
		try {
			return LambdaMetafactoryUtil.metafactoryConstructor(LambdaMetafactoryUtil.PUBLICLOOKUP, clazz);
		} catch (Throwable ignore) {
			return null;
		}
	});
	
	/**
	 * add a manual entry to the create()-Function map
	 *
	 * @param clazz    the {@link Class} to create the entry for
	 * @param supplier the function creating the new {@link Object}
	 * @param <OBJ>    the Object-Type
	 */
	static <OBJ> void manualEntry(Class<OBJ> clazz, Supplier<OBJ> supplier) {
		MAP.globalMap.put(clazz, supplier);
	}
	
	/**
	 * creates an object of the same type.
	 * If there is no manual entry it will try to find an empty constructor.
	 *
	 * @param obj the {@link Object} from which {@link Class} to create a new Instance from
	 * @return the new Instance
	 * @throws MakeNotSupportedException if creation of an new Instance failed.
	 */
	static <OBJ> OBJ create(OBJ obj) {
		return create(gClass(obj));
	}
	
	/**
	 * creates an object of the same type.
	 * If there is no manual entry it will try to find an empty constructor.
	 *
	 * @param clazz the {@link Class} to create a new Instance from
	 * @return the new Instance
	 * @throws MakeNotSupportedException if creation of an new Instance failed.
	 */
	@SuppressWarnings("unchecked")
	static <OBJ> OBJ create(Class<OBJ> clazz) throws MakeNotSupportedException {
		Supplier<?> supplier = MAP.get(clazz);
		if (supplier == null)
			throw new MakeNotSupportedException(clazz);
		return (OBJ) supplier.get();
	}
}
