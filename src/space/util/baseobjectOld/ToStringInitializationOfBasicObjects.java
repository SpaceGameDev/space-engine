package space.util.baseobjectOld;

import java.util.ArrayList;
import java.util.LinkedList;

public class ToStringInitializationOfBasicObjects {
	
	public static boolean called = false;
	
	public static void init() {
		if (called)
			return;
		called = true;
		ToString.putToTSHFunction(ArrayList.class, (api, list) -> api.toString(list.toArray()));
		ToString.putToTSHFunction(LinkedList.class, (api, list) -> api.toString(list.toArray()));
	}
}
