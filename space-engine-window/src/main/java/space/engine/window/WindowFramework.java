package space.engine.window;

import space.util.baseobject.Freeable;
import space.util.key.attribute.AttributeListCreator.IAttributeList;

public interface WindowFramework extends Freeable {
	
	WindowContext createContext(IAttributeList<WindowContext> format);
	
	Monitor[] getMonitors();
	
	Monitor getPrimaryMonitor();
}
