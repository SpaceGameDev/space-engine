package space.util.baseobject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public interface Makeable {
	
	Map<Class<?>, Supplier<?>> MAP = new HashMap<>();
	
	/**
	 * this will create an object of the same type
	 */
	@SuppressWarnings("unchecked")
	static <OBJ> OBJ make(Class<OBJ> clazz) {
		Supplier<?> supplier = MAP.get(clazz);
		if (supplier != null)
			return (OBJ) supplier.get();

//		MethodType methodType = MethodType.methodType(Object.class);
//		MethodType invokedType = MethodType.methodType(Supplier.class);
//		LambdaMetafactory.metafactory(caller, "get", invokedType, methodType, , methodType);
	}
}
