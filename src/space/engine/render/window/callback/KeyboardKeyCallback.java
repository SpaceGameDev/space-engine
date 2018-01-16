package space.engine.render.window.callback;

import space.engine.render.window.IWindow;
import space.engine.render.window.Keys.PressType;

@FunctionalInterface
public interface KeyboardKeyCallback {
	
	void keyPress(IWindow window, int key, PressType pressType);
}
