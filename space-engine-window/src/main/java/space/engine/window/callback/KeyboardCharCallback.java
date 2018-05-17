package space.engine.window.callback;

import space.engine.window.Window;

@FunctionalInterface
public interface KeyboardCharCallback {
	
	void keyPress(Window window, char[] string);
}
