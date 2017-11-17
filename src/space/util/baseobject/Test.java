package space.util.baseobject;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static void main(String[] args) throws Throwable {
		Class<?> clazz = ArrayList.class;
		
		Lookup caller = MethodHandles.publicLookup();
		MethodHandle constructor = caller.findConstructor(clazz, MethodType.methodType(void.class));
		List<?> list = (List<?>) constructor.invoke();
		System.out.println(list);
	}
}
