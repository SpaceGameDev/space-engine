package space.engine.render.window;

import space.util.baseobject.additional.Freeable;
import space.util.key.attribute.IAttributeListCreator.IAttributeList;

public interface IWindowFramework extends Freeable {
	
	IContext createContext(IAttributeList<IContext> format);
	
	IWindow createWindow(IAttributeList<IWindow> format);
	
	IMonitor[] getMonitors();
	
	IMonitor getPrimaryMonitor();
}
