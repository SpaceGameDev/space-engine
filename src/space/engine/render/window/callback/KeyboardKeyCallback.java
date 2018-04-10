package space.engine.render.window.callback;

import space.engine.render.window.Keys.PressType;
import space.engine.render.window.Window;

@FunctionalInterface
public interface KeyboardKeyCallback {
	
	void keyPress(Window window, int key, PressType pressType);
}
