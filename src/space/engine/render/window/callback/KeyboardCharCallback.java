package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface KeyboardCharCallback {
	
	void keyPress(IWindow window, char[] string);
}
