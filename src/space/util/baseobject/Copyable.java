package space.util.baseobject;

import space.util.baseobject.exceptions.CopyNotSupportedException;
import space.util.delegate.map.specific.ThreadLocalGlobalCachingMap;
import space.util.mh.LambdaMetafactoryUtil;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.function.UnaryOperator;

public interface Copyable {
	
	/**
	 * this may shallow-copy objects, instead of deep-copy
	 */
	UnaryOperator<Setable> SETABLE_UNARY_OPERATOR = obj -> {
		Setable ret = Creatable.create(obj);
		Setable.set(ret, obj);
		return ret;
	};
	
	ThreadLocalGlobalCachingMap<Class<?>, UnaryOperator<?>> MAP = new ThreadLocalGlobalCachingMap<>(clazz -> {
		//constructor with same type as argument, see java.util.ArrayList
		try {
			MethodHandle constructor = LambdaMetafactoryUtil.PUBLICLOOKUP.findConstructor(clazz, MethodType.methodType(clazz));
			if (constructor != null)
				return LambdaMetafactoryUtil.metafactoryUnaryOperator(LambdaMetafactoryUtil.PUBLICLOOKUP, constructor, clazz);
		} catch (Throwable ignore) {
		}
		
		//doesn't work as this may shallow-copy
		//create() and then set()
//		if (Setable.class.isAssignableFrom(clazz))
//			return SETABLE_UNARY_OPERATOR;
		
		return null;
	});
	
	/**
	 * add a manual entry to the copy()-Function map
	 *
	 * @param clazz    the {@link Class} to create the entry for
	 * @param function the function copying the {@link Object}
	 * @param <OBJ>    the Object-Type
	 */
	static <OBJ> void manualEntry(Class<OBJ> clazz, UnaryOperator<OBJ> function) {
		MAP.globalMap.put(clazz, function);
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
	static <OBJ> OBJ copy(OBJ obj) throws CopyNotSupportedException {
		UnaryOperator<?> operator = MAP.get(obj.getClass());
		if (operator != null)
			return ((UnaryOperator<OBJ>) operator).apply(obj);
		throw new CopyNotSupportedException(obj.getClass());
	}
}
