package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface WindowFBOResizeCallback {
	
	void onFBOResize(IWindow window, int width, int height);
}
