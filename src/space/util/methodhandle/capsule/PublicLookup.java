package space.util.mh.capsule;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;

public class PublicLookup {
	
	public static Lookup lookup() {
		return MethodHandles.lookup();
	}
}
