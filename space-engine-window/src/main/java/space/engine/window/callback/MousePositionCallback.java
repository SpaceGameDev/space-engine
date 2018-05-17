package space.engine.window.callback;

import space.engine.window.Window;

@FunctionalInterface
public interface MousePositionCallback {
	
	void onMouseMove(Window window, double x, double y);
}
