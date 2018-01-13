package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface CharCallback {
	
	void keyPress(IWindow window, char[] string);
}
