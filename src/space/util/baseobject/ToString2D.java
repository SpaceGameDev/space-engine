package space.util.baseobject;

import space.util.string.String2D;

public interface ToString2D {
	
	//static
	static String2D toString2D(Object obj) {
		if (obj instanceof ToString2D)
			return ((ToString2D) obj).toString2D();
		return String2D.parse(obj.toString());
	}
	
	static String2D toString2D(ToString2D obj) {
		return obj.toString2D();
	}
	
	//non-static
	String2D toString2D();
	
	default String toString0() {
		return toString2D().toString();
	}
}
