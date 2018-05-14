package space.engine.render.window.callback;

import space.engine.render.window.Window;

@FunctionalInterface
public interface KeyboardCharCallback {
	
	void keyPress(Window window, char[] string);
}
