package space.engine.window.callback;

import space.engine.window.Window;

@FunctionalInterface
public interface WindowCloseRequestedCallback {
	
	void onCloseRequested(Window window);
}
