package space.engine.window;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.Freeable;
import space.util.key.attribute.AttributeListCreator.IAttributeList;

public interface WindowFramework extends Freeable {
	
	@NotNull WindowContext createContext(@NotNull IAttributeList<WindowContext> format);
	
	@NotNull Monitor[] getMonitors();
	
	@NotNull Monitor getPrimaryMonitor();
}
