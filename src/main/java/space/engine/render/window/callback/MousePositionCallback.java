package space.engine.render.window.callback;

import space.engine.render.window.Window;

@FunctionalInterface
public interface MousePositionCallback {
	
	void onMouseMove(Window window, double x, double y);
}
