package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface WindowResizeCallback {
	
	void onWindowResize(IWindow window, int width, int height);
}
