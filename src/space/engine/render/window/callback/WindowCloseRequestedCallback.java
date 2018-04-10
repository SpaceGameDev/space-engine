package space.engine.render.window.callback;

import space.engine.render.window.Window;

@FunctionalInterface
public interface WindowCloseRequestedCallback {
	
	void onCloseRequested(Window window);
}
