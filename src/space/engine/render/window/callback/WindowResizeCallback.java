package space.engine.render.window.callback;

import space.engine.render.window.Window;

@FunctionalInterface
public interface WindowResizeCallback {
	
	void onWindowResize(Window window, int width, int height);
}
