package space.util.baseobject;

import space.util.baseobject.exceptions.CopyNotSupportedException;
import space.util.delegate.map.BufferedMap;
import space.util.delegate.map.specific.ThreadLocalGlobalCachingMap;
import space.util.methodhandle.LambdaMetafactoryUtil;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public final class Copyable {
	
	//constant
	/**
	 * this may shallow-copy objects, instead of deep-copy!
	 */
	public static boolean ALLOW_CREATE_THEN_SET = false;
	
	/**
	 * this may shallow-copy objects, instead of deep-copy!
	 */
	public static final UnaryOperator<Setable> CREATE_THEN_SET_UNARY_OPERATOR = obj -> {
		Setable ret = Creatable.create(obj);
		Setable.set(ret, obj);
		return ret;
	};
	
	@SuppressWarnings("unchecked")
	public static <OBJ extends Setable> UnaryOperator<OBJ> getCreateThenSetUnaryOperator() {
		return (UnaryOperator<OBJ>) CREATE_THEN_SET_UNARY_OPERATOR;
	}
	
	//maps
	private static volatile Map<Class<?>, UnaryOperator<?>> WRITE_MAP = new BufferedMap<>(new HashMap<>());
	public static ThreadLocalGlobalCachingMap<Class<?>, UnaryOperator<?>> MAP;
	
	//init
	static {
		BaseObjectInit.init();
	}
	
	static void init() {
		if (!(WRITE_MAP instanceof BufferedMap))
			throw new IllegalStateException("already initialized!");
		
		MAP = new ThreadLocalGlobalCachingMap<>(clazz -> {
			//constructor with same type as argument, see java.util.ArrayList
			try {
				MethodHandle constructor = LambdaMetafactoryUtil.PUBLICLOOKUP.findConstructor(clazz, MethodType.methodType(clazz));
				if (constructor != null)
					return LambdaMetafactoryUtil.metafactoryUnaryOperator(LambdaMetafactoryUtil.PUBLICLOOKUP, constructor, clazz);
			} catch (Throwable ignore) {
			}
			
			//this may shallow-copy objects, instead of deep-copy!
			//create() and then set()
			if (ALLOW_CREATE_THEN_SET && Setable.class.isAssignableFrom(clazz))
				return CREATE_THEN_SET_UNARY_OPERATOR;
			
			return null;
		});
		
		((BufferedMap<Class<?>, UnaryOperator<?>>) WRITE_MAP).setSink(MAP.globalMap);
		WRITE_MAP = MAP.globalMap;
	}
	
	//functions
	
	/**
	 * add a manual entry to the copy()-Function map
	 *
	 * @param clazz    the {@link Class} to create the entry for
	 * @param function the function copying the {@link Object}
	 * @param <OBJ>    the Object-Type
	 */
	public static <OBJ> void manualEntry(Class<OBJ> clazz, UnaryOperator<OBJ> function) {
		WRITE_MAP.put(clazz, function);
	}
	
	/**
	 * creates an {@link Object} with a deep-copy.
	 * If there is no manual entry it will try to find a constructor with one argument, the {@link Object} itself.
	 * If that fails, it will try to create() the object and then set() it.
	 *
	 * @param obj the {@link Object} to deep-copy
	 * @return the new copied instance
	 * @throws CopyNotSupportedException if creation of an new copied instance failed.
	 */
	@SuppressWarnings("unchecked")
	public static <OBJ> OBJ copy(OBJ obj) throws CopyNotSupportedException {
		UnaryOperator<?> operator = MAP.get(obj.getClass());
		if (operator != null)
			return ((UnaryOperator<OBJ>) operator).apply(obj);
		throw new CopyNotSupportedException(obj.getClass());
	}
}
