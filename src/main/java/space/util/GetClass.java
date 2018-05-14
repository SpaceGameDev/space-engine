package space.util;

public class GetClass {
	
	@SuppressWarnings("unchecked")
	public static <T> Class<? extends T> gClass(T t) {
		return (Class<? extends T>) t.getClass();
	}
}
