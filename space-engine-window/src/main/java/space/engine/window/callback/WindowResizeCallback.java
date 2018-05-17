package space.engine.window.callback;

import space.engine.window.Window;

@FunctionalInterface
public interface WindowResizeCallback {
	
	void onWindowResize(Window window, int width, int height);
}
