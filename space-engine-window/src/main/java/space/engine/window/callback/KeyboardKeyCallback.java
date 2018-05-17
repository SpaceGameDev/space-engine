package space.engine.window.callback;

import space.engine.window.Keys.PressType;
import space.engine.window.Window;

@FunctionalInterface
public interface KeyboardKeyCallback {
	
	void keyPress(Window window, int key, PressType pressType);
}
