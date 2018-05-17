package space.engine.window.callback;

@FunctionalInterface
public interface JoystickConnectCallback {
	
	void joystickConnect(int joy, boolean plugged);
}
