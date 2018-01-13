package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface MousePositionCallback {
	
	void onMouseMove(IWindow window, double x, double y);
}
