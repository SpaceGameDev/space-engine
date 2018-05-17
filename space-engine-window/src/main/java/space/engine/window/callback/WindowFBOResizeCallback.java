package space.engine.window.callback;

import space.engine.window.Window;

@FunctionalInterface
public interface WindowFBOResizeCallback {
	
	void onFBOResize(Window window, int width, int height);
}
