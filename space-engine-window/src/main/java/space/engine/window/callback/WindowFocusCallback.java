package space.engine.window.callback;

import space.engine.window.Window;

@FunctionalInterface
public interface WindowFocusCallback {
	
	void onWindowFocus(Window window, boolean focus);
}
