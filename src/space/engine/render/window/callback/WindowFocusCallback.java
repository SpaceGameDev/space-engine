package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface WindowFocusCallback {
	
	void onWindowFocus(IWindow window, boolean focus);
}
