package space.engine.render.window.callback;

import space.engine.render.window.Window;

@FunctionalInterface
public interface WindowFBOResizeCallback {
	
	void onFBOResize(Window window, int width, int height);
}
