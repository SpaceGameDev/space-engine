package space.engine.window;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Freeable;
import space.engine.key.attribute.AttributeList;
import space.engine.sync.future.Future;
import space.engine.window.extensions.WindowExtension;

import java.util.Collection;

/**
 * The {@link WindowFramework} is the first Interface you interact with when opening any {@link Window Windows}.
 * <ul>
 * <li>Get all {@link WindowFrameworkCreator} by calling {@link WindowFramework#getAvailableFrameworks()}</li>
 * <li>Select one and call {@link WindowFrameworkCreator#createFramework()} to initialize it</li>
 * <li>Create a context with {@link WindowFramework#createContext(AttributeList) createContext(AttributeList)} and a properly filled {@link WindowContext#CREATOR AttributeList form the WindowContext}</li>
 * <li>You can also query {@link WindowFramework#getPrimaryMonitor()} or {@link WindowFramework#getAllMonitors()}</li>
 * </ul>
 */
public interface WindowFramework extends Freeable {
	
	Collection<Class<? extends WindowExtension>> getSupportedWindowExtensions();
	
	@NotNull Future<? extends WindowContext> createContext(@NotNull AttributeList<WindowContext> format);
	
	//monitor
	@NotNull Future<? extends Monitor> getPrimaryMonitor();
	
	@NotNull Future<Collection<? extends Monitor>> getAllMonitors();
}
