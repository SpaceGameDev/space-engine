package space.engine.render.window.callback;

import space.engine.render.window.Window;

@FunctionalInterface
public interface WindowPositionCallback {
	
	void onWindowMove(Window window, int x, int y);
}
