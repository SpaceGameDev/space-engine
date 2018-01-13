package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface WindowCloseRequestedCallback {
	
	void onCloseRequested(IWindow window);
}
