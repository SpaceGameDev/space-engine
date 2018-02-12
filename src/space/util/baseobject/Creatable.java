package space.util.baseobject;

import space.util.baseobject.exceptions.MakeNotSupportedException;
import space.util.delegate.map.BufferedMap;
import space.util.delegate.map.specific.ThreadLocalGlobalCachingMap;
import space.util.methodhandle.LambdaMetafactoryUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static space.util.GetClass.gClass;

public final class Creatable {
	
	//maps
	private static volatile Map<Class<?>, Supplier<?>> WRITE_MAP = new BufferedMap<>(new HashMap<>());
	public static ThreadLocalGlobalCachingMap<Class<?>, Supplier<?>> MAP;
	
	//init
	static {
		BaseObjectInit.init();
	}
	
	static void init() {
		if (!(WRITE_MAP instanceof BufferedMap))
			throw new IllegalStateException("already initialized!");
		
		MAP = new ThreadLocalGlobalCachingMap<>(clazz -> {
			try {
				return LambdaMetafactoryUtil.metafactoryConstructor(LambdaMetafactoryUtil.PUBLIC_LOOKUP, clazz);
			} catch (Throwable ignore) {
				return null;
			}
		});
		
		((BufferedMap<Class<?>, Supplier<?>>) WRITE_MAP).setSink(MAP.globalMap);
		WRITE_MAP = MAP.globalMap;
	}
	
	//function
	
	/**
	 * add a manual entry to the create()-Function map
	 *
	 * @param clazz    the {@link Class} to create the entry for
	 * @param supplier the function creating the new {@link Object}
	 * @param <OBJ>    the Object-Type
	 */
	public static <OBJ> void manualEntry(Class<OBJ> clazz, Supplier<OBJ> supplier) {
		WRITE_MAP.put(clazz, supplier);
	}
	
	/**
	 * creates an object of the same type.
	 * If there is no manual entry it will try to find an empty constructor.
	 *
	 * @param obj the {@link Object} from which {@link Class} to create a new Instance from
	 * @return the new Instance
	 * @throws MakeNotSupportedException if creation of an new Instance failed.
	 */
	public static <OBJ> OBJ create(OBJ obj) throws MakeNotSupportedException {
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
	public static <OBJ> OBJ create(Class<OBJ> clazz) throws MakeNotSupportedException {
		Supplier<?> supplier = MAP.get(clazz);
		if (supplier != null)
			return (OBJ) supplier.get();
		throw new MakeNotSupportedException(clazz);
	}
}
