package space.util.baseobject;

import java.util.ArrayList;
import java.util.LinkedList;

public class InitializationOfStandardObjects {
	
	protected static boolean called = false;
	
	public static byte init2() {
		init();
		return 0;
	}
	
	public static void init() {
		if (called)
			return;
		called = true;
		
		ToString.manualEntry(ArrayList.class, (api, list) -> api.toString(list.toArray()));
		ToString.manualEntry(LinkedList.class, (api, list) -> api.toString(list.toArray()));
	}
}
