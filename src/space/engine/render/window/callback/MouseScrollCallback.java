package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface MouseScrollCallback {
	
	void scrollCallback(IWindow window, double x, double y);
}
