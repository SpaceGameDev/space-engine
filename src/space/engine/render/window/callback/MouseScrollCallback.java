package space.engine.render.window.callback;

import space.engine.render.window.Window;

@FunctionalInterface
public interface MouseScrollCallback {
	
	void scrollCallback(Window window, double x, double y);
}
