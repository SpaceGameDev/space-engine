package space.engine.window.callback;

import space.engine.window.Window;

@FunctionalInterface
public interface MouseScrollCallback {
	
	void scrollCallback(Window window, double x, double y);
}
