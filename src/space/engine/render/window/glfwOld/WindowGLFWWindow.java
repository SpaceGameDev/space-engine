package space.engine.render.window.glfwOld;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import space.engine.render.window.IWindow;
import space.engine.render.window.Keys.PressType;
import space.engine.render.window.WindowException;
import space.engine.render.window.WindowFormat;
import space.engine.render.window.WindowFormat.GLApiType;
import space.engine.render.window.WindowFormat.WindowMode;
import space.engine.render.window.callback.JoystickConnectCallback;
import space.engine.render.window.callback.KeyboardCharCallback;
import space.engine.render.window.callback.KeyboardKeyCallback;
import space.engine.render.window.callback.MouseClickCallback;
import space.engine.render.window.callback.MousePositionCallback;
import space.engine.render.window.callback.MouseScrollCallback;
import space.engine.render.window.callback.WindowCloseRequestedCallback;
import space.engine.render.window.callback.WindowFBOResizeCallback;
import space.engine.render.window.callback.WindowFocusCallback;
import space.engine.render.window.callback.WindowPositionCallback;
import space.engine.render.window.callback.WindowResizeCallback;
import spaceOld.engine.releasable.IReleasable;
import spaceOld.engine.releasable.IReleasableWrapper;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowGLFWWindow implements IWindow {
	
	public WindowGLFWFramework glfwFrame;
	public WindowGLFWWindow glfwShare;
	public WindowGLFWWindowStorage storage = new WindowGLFWWindowStorage();
	
	public KeyboardCharCallback charCallback;
	public JoystickConnectCallback joystickCallback;
	public KeyboardKeyCallback keyCallback;
	public MouseClickCallback mouseClickCallback;
	public MousePositionCallback mousePositionCallback;
	public MouseScrollCallback scrollCallback;
	public WindowCloseRequestedCallback windowCloseRequestedCallback;
	public WindowFBOResizeCallback windowFBOResizeCallback;
	public WindowFocusCallback windowFocusCallback;
	public WindowPositionCallback windowPositionCallback;
	public WindowResizeCallback windowResizeCallback;
	
	public GLFWCharCallbackI GLFWCharCallback;
	//GLFWJoystickCallbackI is in the framework, not window dependent
	public GLFWKeyCallbackI GLFWKeyCallback;
	public GLFWMouseButtonCallbackI GLFWMouseClickCallback;
	public GLFWCursorPosCallbackI GLFWMousePositionCallback;
	public GLFWScrollCallbackI GLFWScrollCallback;
	public GLFWWindowCloseCallbackI GLFWWindowCloseRequestedCallback;
	public GLFWFramebufferSizeCallbackI GLFWWindowFBOResizeCallback;
	public GLFWWindowFocusCallbackI GLFWWindowFocusCallback;
	public GLFWWindowPosCallbackI GLFWWindowPositionCallback;
	public GLFWWindowSizeCallbackI GLFWWindowResizeCallback;
	
	public WindowGLFWWindow(WindowGLFWFramework glfwFrame) {
		this.glfwFrame = glfwFrame;
	}
	
	public WindowGLFWWindow(WindowGLFWFramework glfwFrame, WindowFormat format) {
		this(glfwFrame);
		create(format);
	}
	
	public void setShare(WindowFormat f) {
		IWindow w = f.share;
		if (w != null) {
			if (!(w instanceof WindowGLFWWindow))
				throw new IllegalArgumentException("Window not an glfw window!");
			glfwShare = (WindowGLFWWindow) w;
		} else {
			glfwShare = null;
			glfwWindowHint(GLFW_CLIENT_API, getApiId(f.GLApi));
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, f.GLVersionMajor);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, f.GLVersionMinor);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, f.GLDeprecated ? GLFW_FALSE : GLFW_TRUE);
		}
	}
	
	private static int getApiId(GLApiType type) {
		switch (type) {
			case GL:
				return GLFW_OPENGL_API;
			case GLES:
				return GLFW_OPENGL_ES_API;
			case NONE:
				return GLFW_NO_API;
		}
		throw new IllegalArgumentException();
	}
	
	protected long getShareId() {
		if (glfwShare != null)
			return glfwShare.storage.window;
		return 0;
	}
	
	public static void setHints(WindowFormat f) {
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, WindowFormat.ALLOW_RESIZE ? GLFW_TRUE : GLFW_FALSE);
		glfwWindowHint(GLFW_DOUBLEBUFFER, f.doubleDuffer ? GLFW_TRUE : GLFW_FALSE);
		
		glfwWindowHint(GLFW_RED_BITS, f.r);
		glfwWindowHint(GLFW_GREEN_BITS, f.g);
		glfwWindowHint(GLFW_BLUE_BITS, f.b);
		glfwWindowHint(GLFW_ALPHA_BITS, f.a);
		glfwWindowHint(GLFW_DEPTH_BITS, f.depth);
		glfwWindowHint(GLFW_STENCIL_BITS, f.stencil);
	}
	
	public void createWindow(WindowFormat f) {
		long mon = getMonitor(f.monitor);
		
		if (f.mode == WindowMode.FULLSCREEN) {
			GLFWVidMode.Buffer vidModeArray = glfwGetVideoModes(mon);
			if (!vidModeArray.hasRemaining())
				throw new WindowException("No avaible Displaymodes on window id " + f.monitor);
			GLFWVidMode vidMode = null;
			
			while (vidModeArray.hasRemaining()) {
				GLFWVidMode currVidMode = vidModeArray.get();
				if (currVidMode.width() < f.width && currVidMode.height() < f.height) {
					if (vidMode == null || (currVidMode.width() < vidMode.width() && currVidMode.height() < vidMode.height() && currVidMode.refreshRate() < vidMode.refreshRate())) {
						vidMode = currVidMode;
					}
				}
			}
			
			if (vidMode != null) {
				storage.window = glfwCreateWindow(vidMode.width(), vidMode.height(), WindowFormat.TITLE, 0, getShareId());
				glfwSetWindowMonitor(storage.window, mon, 0, 0, vidMode.width(), vidMode.height(), vidMode.refreshRate());
				return;
			}
		}
		
		GLFWVidMode vidMode = glfwGetVideoMode(mon);
		glfwWindowHint(GLFW_DECORATED, f.mode == WindowMode.BORDERLESS ? GLFW_FALSE : GLFW_TRUE);
		storage.window = glfwCreateWindow(f.width, f.height, WindowFormat.TITLE, 0, getShareId());
		glfwSetWindowMonitor(storage.window, NULL, (vidMode.width() - f.width) / 2, (vidMode.height() - f.height) / 2, f.width, f.height, 0);
	}
	
	public void copyCallbacks(WindowFormat f) {
		this.charCallback = WindowFormat.CHAR_CALLBACK;
		this.joystickCallback = f.joystickCallback;
		this.keyCallback = f.keyCallback;
		this.mouseClickCallback = f.mouseClickCallback;
		this.mousePositionCallback = f.mousePositionCallback;
		this.scrollCallback = f.scrollCallback;
		this.windowCloseRequestedCallback = f.windowCloseRequestedCallback;
		this.windowFBOResizeCallback = f.windowFBOResizeCallback;
		this.windowFocusCallback = f.windowFocusCallback;
		this.windowPositionCallback = f.windowPositionCallback;
		this.windowResizeCallback = f.windowResizeCallback;
	}
	
	public void configWindow(WindowFormat f) {
		if (charCallback != null) {
			glfwSetCharCallback(storage.window, GLFWCharCallback = (long window, int codepoint) -> charCallback.keyPress(this, Character.toChars(codepoint)));
		} else {
			GLFWCharCallback = null;
		}
		
		if (storage.oldJoystickCallback != null) {
			glfwFrame.joystickCallbacks.remove(storage.oldJoystickCallback);
		}
		if (joystickCallback != null) {
			glfwFrame.joystickCallbacks.add(storage.oldJoystickCallback = joystickCallback);
		}
		
		if (keyCallback != null) {
			glfwSetKeyCallback(storage.window, GLFWKeyCallback = (long window, int key, int scancode, int action, int mods) -> keyCallback.keyPress(this, key, getPressType(action)));
		} else {
			GLFWKeyCallback = null;
		}
		
		if (mouseClickCallback != null) {
			glfwSetMouseButtonCallback(storage.window, GLFWMouseClickCallback = (long window, int button, int action, int mods) -> mouseClickCallback.onMouseClick(this, button, getPressType(action)));
		} else {
			GLFWMouseClickCallback = null;
		}
		
		if (mousePositionCallback != null) {
			glfwSetCursorPosCallback(storage.window, GLFWMousePositionCallback = (long window, double xpos, double ypos) -> mousePositionCallback.onMouseMove(this, xpos, ypos));
		} else {
			GLFWMousePositionCallback = null;
		}
		
		if (scrollCallback != null) {
			glfwSetScrollCallback(storage.window, GLFWScrollCallback = (long window, double xoffset, double yoffset) -> scrollCallback.scrollCallback(this, xoffset, yoffset));
		} else {
			GLFWScrollCallback = null;
		}
		
		if (windowCloseRequestedCallback != null) {
			glfwSetWindowCloseCallback(storage.window, GLFWWindowCloseRequestedCallback = (long window) -> windowCloseRequestedCallback.onCloseRequested(this));
		} else {
			GLFWWindowCloseRequestedCallback = null;
		}
		
		if (windowFBOResizeCallback != null) {
			glfwSetFramebufferSizeCallback(storage.window, GLFWWindowFBOResizeCallback = (long window, int width, int height) -> windowFBOResizeCallback.onFBOResize(this, width, height));
		} else {
			GLFWWindowFBOResizeCallback = null;
		}
		
		if (windowFocusCallback != null) {
			glfwSetWindowFocusCallback(storage.window, GLFWWindowFocusCallback = (long window, boolean focused) -> windowFocusCallback.onWindowFocus(this, focused));
		} else {
			GLFWWindowFocusCallback = null;
		}
		
		if (windowPositionCallback != null) {
			glfwSetWindowPosCallback(storage.window, GLFWWindowPositionCallback = (long window, int xpos, int ypos) -> windowPositionCallback.onWindowMove(this, xpos, ypos));
		} else {
			GLFWWindowPositionCallback = null;
		}
		
		if (windowResizeCallback != null) {
			glfwSetWindowSizeCallback(storage.window, GLFWWindowResizeCallback = (long window, int width, int height) -> windowResizeCallback.onWindowResize(this, width, height));
		} else {
			GLFWWindowResizeCallback = null;
		}
		
		if (WindowFormat.IS_VISIBLE)
			glfwShowWindow(storage.window);
	}
	
	private static PressType getPressType(int action) {
		switch (action) {
			case GLFW_PRESS:
				return PressType.PRESSED;
			case GLFW_REPEAT:
				return PressType.CLOSED;
			case GLFW_RELEASE:
				return PressType.RELEASED;
		}
		throw new IllegalArgumentException();
	}
	
	public void create(WindowFormat f) {
		synchronized (glfwFrame.windowCreationSync) {
			setShare(f);
			setHints(f);
			createWindow(f);
			copyCallbacks(f);
			configWindow(f);
		}
	}
	
	@Override
	public void makeContextCurrent() {
		glfwMakeContextCurrent(storage.window);
	}
	
	@Override
	public void update(WindowFormat format) {
		destroy();
		create(format);
	}
	
	@Override
	public void swapBuffers() {
		glfwSwapBuffers(storage.window);
	}
	
	@Override
	public void pollEvents() {
		glfwPollEvents();
	}
	
	@Override
	public void destroy() {
		IReleasableWrapper.super.release();
	}
	
	@Override
	public void release() {
		IReleasableWrapper.super.release();
	}
	
	@Override
	public IReleasable getReleasable() {
		return storage;
	}
	
	private static long getMonitor(int id) {
		PointerBuffer monpb = glfwGetMonitors();
		if (monpb == null)
			throw new WindowException("No monitors avaible!");
		int moncnt = monpb.remaining();
		if (moncnt < id)
			throw new WindowException("Monitor id " + id + " is not avaible, only " + moncnt + " Monitors are connected!");
		return monpb.get(id);
	}
	
	public static class WindowGLFWWindowStorage implements IReleasable {
		
		long window;
		JoystickConnectCallback oldJoystickCallback;
		
		@Override
		public synchronized void release() {
			if (window == 0)
				return;
			glfwDestroyWindow(window);
			window = 0;
		}
	}
}
