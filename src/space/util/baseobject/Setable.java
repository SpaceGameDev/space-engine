package space.util.baseobject;

import space.util.baseobject.exceptions.SetNotSupportedException;
import space.util.delegate.map.BufferedMap;
import space.util.delegate.map.specific.ThreadLocalGlobalCachingMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public interface Setable {
	
	//init
	/**
	 * used to have a static-init-function in the interface
	 */
	byte zero = BaseObjectInit.init2();
	
	static void init() {
		SetableClass.init();
	}
	
	//functions
	
	/**
	 * add a manual entry to the set()-Function map
	 *
	 * @param clazz       the {@link Class} to create the entry for
	 * @param setFunction the function creating the new {@link Object}
	 * @param <OBJ>       the Object-Type
	 */
	static <OBJ> void manualEntry(Class<OBJ> clazz, BiConsumer<OBJ, OBJ> setFunction) {
		SetableClass.WRITE_MAP.put(clazz, setFunction);
	}
	
	/**
	 * sets one obj to the values of another
	 *
	 * @param obj the {@link Object} being set to a different state
	 * @param to  the {@link Object} having a certain state
	 * @throws SetNotSupportedException if setting failed
	 */
	static <OBJ> void set(OBJ obj, OBJ to) throws SetNotSupportedException {
		if (obj instanceof Setable) {
			((Setable) obj).set(to);
			return;
		}
		
		BiConsumer<?, ?> function = SetableClass.MAP.get(obj.getClass());
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
	
	//implementable
	void set(Object obj) throws SetNotSupportedException;
	
	//class
	class SetableClass {
		
		//maps
		private static volatile Map<Class<?>, BiConsumer<?, ?>> WRITE_MAP = new BufferedMap<>(new HashMap<>());
		public static ThreadLocalGlobalCachingMap<Class<?>, BiConsumer<?, ?>> MAP;
		
		//init
		static {
			BaseObjectInit.init();
		}
		
		static void init() {
			if (!(WRITE_MAP instanceof BufferedMap))
				throw new IllegalStateException("already initialized!");
			
			MAP = new ThreadLocalGlobalCachingMap<>();
			
			((BufferedMap<Class<?>, BiConsumer<?, ?>>) WRITE_MAP).setSink(MAP.globalMap);
			WRITE_MAP = MAP.globalMap;
		}
	}
}
