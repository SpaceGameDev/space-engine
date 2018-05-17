package space.engine.window;

import space.util.baseobject.Freeable;
import space.util.key.attribute.AttributeListCreator.IAttributeList;

public interface WindowFramework extends Freeable {
	
	WindowContext createContext(IAttributeList<WindowContext> format);
	
	Window createWindow(IAttributeList<Window> format);
	
	WindowMonitor[] getMonitors();
	
	WindowMonitor getPrimaryMonitor();
}
