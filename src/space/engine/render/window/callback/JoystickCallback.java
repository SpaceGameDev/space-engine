package space.engine.render.window.callback;

import space.engine.render.window.IWindow;

@FunctionalInterface
public interface JoystickCallback {
	
	/**
	 * window may be null
	 */
	void joystickConnect(IWindow window, int joy, boolean plugged);
}
