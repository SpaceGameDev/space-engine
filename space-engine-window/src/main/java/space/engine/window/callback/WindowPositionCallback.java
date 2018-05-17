package space.engine.window.callback;

import space.engine.window.Window;

@FunctionalInterface
public interface WindowPositionCallback {
	
	void onWindowMove(Window window, int x, int y);
}
