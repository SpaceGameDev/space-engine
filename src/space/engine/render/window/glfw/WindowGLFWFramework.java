package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFWErrorCallbackI;
import space.engine.render.window.IWindowFramework;
import space.engine.render.window.WindowFormat;
import space.engine.render.window.callback.JoystickCallback;
import space.util.logger.Logger;
import spaceOld.engine.logger.LogLevel;
import spaceOld.engine.logger.SubLogger;
import spaceOld.engine.releasable.IReleasable;
import spaceOld.util.map.TokenMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.memUTF8;

public class WindowGLFWFramework implements IWindowFramework<WindowGLFWWindow> {
	
	public final Object windowCreationSync = new Object();
	public WindowGLFWErrorCallback errorCallback;
	
	public final List<JoystickCallback> joystickCallbacks = Collections.synchronizedList(new ArrayList<>());
	
	@Override
	public void init(Logger logger) {
		errorCallback = new WindowGLFWErrorCallback(logger);
		setErrorBallback();
		if (!glfwInit())
			throw new GLFWException("GLFWInit failed!");
		
		glfwSetJoystickCallback((int joy, int event) -> {
			boolean plugged = event == GLFW_CONNECTED;
			synchronized (joystickCallbacks) {
				for (JoystickCallback c : joystickCallbacks) {
					c.joystickConnect(null, joy, plugged);
				}
			}
		});
	}
	
	@Override
	public void newThread() {
		setErrorBallback();
	}
	
	public void setErrorBallback() {
		glfwSetErrorCallback(errorCallback);
	}
	
	@Override
	public WindowGLFWWindow create(WindowFormat format) {
		return new WindowGLFWWindow(this, format);
	}
	
	//destroy
	@Override
	public void destroy() {
	
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			free();
		} finally {
			super.finalize();
		}
	}
	
	public static class WindowGLFWFrameworkReleaseable implements IReleasable {
		
		public boolean released = false;
		
		@Override
		public synchronized void release() {
			if (released)
				return;
			glfwTerminate();
			released = true;
		}
	}
	
	public static class WindowGLFWErrorCallback implements GLFWErrorCallbackI {
		
		public static final int GLFW_NOT_INITIALIZED = 0x10001;
		public static final int GLFW_NO_CURRENT_CONTEXT = 0x10002;
		public static final int GLFW_INVALID_ENUM = 0x10003;
		public static final int GLFW_INVALID_VALUE = 0x10004;
		public static final int GLFW_OUT_OF_MEMORY = 0x10005;
		public static final int GLFW_API_UNAVAILABLE = 0x10006;
		public static final int GLFW_VERSION_UNAVAILABLE = 0x10007;
		public static final int GLFW_PLATFORM_ERROR = 0x10008;
		public static final int GLFW_FORMAT_UNAVAILABLE = 0x10009;
		public static final int GLFW_NO_WINDOW_CONTEXT = 0x1000A;
		
		public static final Map<Integer, String> GLFW_TOKENMAP = TokenMap.makeErrorTokenMapIntNoMod(TokenMap.ALL, new Class[] {WindowGLFWErrorCallback.class});
		
		public Logger logger;
		
		public WindowGLFWErrorCallback(Logger logger) {
			this.logger = new SubLogger(logger, "GLFWErrorCallback");
		}
		
		@Override
		public void invoke(int error, long description) {
			String errorstr = GLFW_TOKENMAP.get(error);
			String descstr = memUTF8(description);
			logger.print(LogLevel.ERROR, errorstr + ": " + descstr);
		}
	}
}
