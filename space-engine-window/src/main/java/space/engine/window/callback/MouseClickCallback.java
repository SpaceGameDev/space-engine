package space.engine.window.callback;

import space.engine.window.Window;

@FunctionalInterface
public interface MouseClickCallback {
	
	void onMouseClick(Window window, int key, PressType pressType);
}
