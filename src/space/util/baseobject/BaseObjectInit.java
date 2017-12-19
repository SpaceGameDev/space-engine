package space.util.baseobject;

import java.util.ArrayList;
import java.util.LinkedList;

public class BaseObjectInit {
	
	protected static boolean called = false;
	
	public static byte init2() {
		init();
		return 0;
	}
	
	public static void init() {
		synchronized (BaseObjectInit.class) {
			if (called)
				return;
			called = true;
		}
		
		Setable.init();
		Creatable.init();
		Copyable.init();
		ToString.init();
		
		ToString.manualEntry(ArrayList.class, (api, list) -> api.toString(list.toArray()));
		ToString.manualEntry(LinkedList.class, (api, list) -> api.toString(list.toArray()));
	}
}
