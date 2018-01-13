package space.engine.render.window;

import space.engine.render.window.callback.CharCallback;
import space.engine.render.window.callback.JoystickCallback;
import space.engine.render.window.callback.KeyCallback;
import space.engine.render.window.callback.MouseClickCallback;
import space.engine.render.window.callback.MousePositionCallback;
import space.engine.render.window.callback.ScrollCallback;
import space.engine.render.window.callback.WindowCloseRequestedCallback;
import space.engine.render.window.callback.WindowFBOResizeCallback;
import space.engine.render.window.callback.WindowFocusCallback;
import space.engine.render.window.callback.WindowPositionCallback;
import space.engine.render.window.callback.WindowResizeCallback;

public class WindowFormat {
	
	public int width = 800;
	public int height = 600;
	public WindowMode mode = WindowMode.WINDOWED;
	
	public boolean isVisible = true;
	public int monitor = 0;
	public String title = "No Title";
	public boolean allowResize = false;
	public boolean doubleDuffer = true;
	
	public IWindow share = null;
	public GLApiType GLApi = GLApiType.GL;
	public int GLVersionMajor = 1;
	public int GLVersionMinor = 1;
	public boolean GLDeprecated = true;
	
	public int r = 8;
	public int g = 8;
	public int b = 8;
	public int a = 0;
	public int depth = 24;
	public int stencil = 8;
	
	public boolean asyncEventPoll = false;
	
	public CharCallback charCallback;
	public JoystickCallback joystickCallback;
	public KeyCallback keyCallback;
	public MouseClickCallback mouseClickCallback;
	public MousePositionCallback mousePositionCallback;
	public ScrollCallback scrollCallback;
	public WindowCloseRequestedCallback windowCloseRequestedCallback;
	public WindowFBOResizeCallback windowFBOResizeCallback;
	public WindowFocusCallback windowFocusCallback;
	public WindowPositionCallback windowPositionCallback;
	public WindowResizeCallback windowResizeCallback;
	
	public WindowFormat setWindow(int width, int height, WindowMode mode) {
		this.width = width;
		this.height = height;
		this.mode = mode;
		return this;
	}
	
	public WindowFormat setVisible(boolean isVisible) {
		this.isVisible = isVisible;
		return this;
	}
	
	public WindowFormat confContextOnly() {
		setVisible(false);
		setWindow(1, 1, WindowMode.WINDOWED);
		setWindowSettings(0, "ContextOnly", false, false);
		setOutputFBO(0, 0, 0, 0, 0, 0);
		setAsyncEventPoll(true);
		return this;
	}
	
	public WindowFormat setWindowSettings(int monitor, String title, boolean allowResize, boolean doubleBuffer) {
		this.monitor = monitor;
		this.title = title;
		this.allowResize = allowResize;
		this.doubleDuffer = doubleBuffer;
		return this;
	}
	
	public WindowFormat setOutputFBO(int r, int g, int b, int a, int depth, int stencil) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.depth = depth;
		this.stencil = stencil;
		return this;
	}
	
	public WindowFormat setShare(IWindow share) {
		this.share = share;
		return this;
	}
	
	public WindowFormat setContextConfig(GLApiType GLApiType, int GLVersionMajor, int GLVersionMinor, boolean GLDeprecated) {
		setShare(null);
		this.GLApi = GLApiType;
		this.GLVersionMajor = GLVersionMajor;
		this.GLVersionMinor = GLVersionMinor;
		this.GLDeprecated = GLDeprecated;
		return this;
	}
	
	public WindowFormat setAsyncEventPoll(boolean asyncEventPoll) {
		this.asyncEventPoll = asyncEventPoll;
		return this;
	}
	
	public WindowFormat setCharCallback(CharCallback charCallback) {
		this.charCallback = charCallback;
		return this;
	}
	
	public WindowFormat setJoystickCallback(JoystickCallback joystickCallback) {
		this.joystickCallback = joystickCallback;
		return this;
	}
	
	public WindowFormat setKeyCallback(KeyCallback keyCallback) {
		this.keyCallback = keyCallback;
		return this;
	}
	
	public WindowFormat setMouseClickCallback(MouseClickCallback mouseClickCallback) {
		this.mouseClickCallback = mouseClickCallback;
		return this;
	}
	
	public WindowFormat setMousePositionCallback(MousePositionCallback mousePositionCallback) {
		this.mousePositionCallback = mousePositionCallback;
		return this;
	}
	
	public WindowFormat setScrollCallback(ScrollCallback scrollCallback) {
		this.scrollCallback = scrollCallback;
		return this;
	}
	
	public WindowFormat setWindowCloseRequestedCallback(WindowCloseRequestedCallback windowCloseRequestedCallback) {
		this.windowCloseRequestedCallback = windowCloseRequestedCallback;
		return this;
	}
	
	public WindowFormat setWindowFBOResizeCallback(WindowFBOResizeCallback windowFBOResizeCallback) {
		this.windowFBOResizeCallback = windowFBOResizeCallback;
		return this;
	}
	
	public WindowFormat setWindowFocusCallback(WindowFocusCallback windowFocusCallback) {
		this.windowFocusCallback = windowFocusCallback;
		return this;
	}
	
	public WindowFormat setWindowPositionCallback(WindowPositionCallback windowPositionCallback) {
		this.windowPositionCallback = windowPositionCallback;
		return this;
	}
	
	public WindowFormat setWindowResizeCallback(WindowResizeCallback windowResizeCallback) {
		this.windowResizeCallback = windowResizeCallback;
		return this;
	}
	
	public enum WindowMode {
		
		WINDOWED,
		FULLSCREEN,
		BORDERLESS
		
	}
	
	public enum GLApiType {
		
		GL,
		GLES,
		NONE
		
	}
}
