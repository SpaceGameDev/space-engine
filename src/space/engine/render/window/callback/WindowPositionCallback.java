package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface WindowPositionCallback {
	
	void onWindowMove(IWindow window, int x, int y);
}
