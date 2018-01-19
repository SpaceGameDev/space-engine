package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFWErrorCallbackI;
import space.engine.render.window.IWindow;
import space.engine.render.window.IWindowFramework;
import space.engine.render.window.glfw.GLFWWindowFramework.GLFWWindow;
import space.engine.render.window.glfwOld.GLFWException;
import space.engine.side.Side;
import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.BufferImpl;
import space.util.buffer.stack.BufferAllocatorStack;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;
import space.util.logger.LogLevel;
import space.util.logger.Logger;
import space.util.string.builder.CharBufferBuilder1D;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.render.window.WindowFormat.*;
import static space.engine.render.window.WindowFormat.WindowMode.*;

public class GLFWWindowFramework implements IWindowFramework<GLFWWindow> {
	
	public static final Object GLFW_SYNC = new Object();
	
	//instance handling
	private static volatile GLFWWindowFramework instance;
	
	public static GLFWWindowFramework getInstance() {
		return instance;
	}
	
	public static synchronized GLFWWindowFramework newInstance(Logger logger) {
		if (instance != null)
			throw new IllegalStateException("Only one Instance is allowed at a time");
		return instance = new GLFWWindowFramework(logger);
	}
	
	//object stuff
	public GLFWErrorCallback errorCallback;
	
	protected GLFWWindowFramework(Logger logger) {
		glfwSetErrorCallback(errorCallback = new GLFWErrorCallback(logger.subLogger("GLFW")));
		if (!glfwInit())
			throw new GLFWException("glfwInit failed!");
	}
	
	//create Window
	@Override
	public GLFWWindow create(IAttributeList format) {
		return null;
	}
	
	public class GLFWWindow implements IWindow {
		
		public long windowPointer;
		public IAttributeList curr = ATTRIBUTE_LIST_CREATOR.create();
		
		public GLFWWindow(IAttributeList format) {
			synchronized (GLFW_SYNC) {
				glfwWindowHint(GLFW_VISIBLE, curr.pull(format, VISIBLE) ? GLFW_TRUE : GLFW_FALSE);
				glfwWindowHint(GLFW_RESIZABLE, curr.pull(format, RESIZEABLE) ? GLFW_TRUE : GLFW_FALSE);
				glfwWindowHint(GLFW_DOUBLEBUFFER, curr.pull(format, DOUBLEBUFFER) ? GLFW_TRUE : GLFW_FALSE);
				
				//GLApi
				glfwWindowHint(GLFW_CLIENT_API, covertGLApiTypeToGLFWApi(curr.pull(format, GL_API_TYPE)));
				glfwWindowHint(GLFW_OPENGL_PROFILE, covertGLProfileToGLFWProfile(curr.pull(format, GL_PROFILE)));
				glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, curr.pull(format, GL_VERSION_MAJOR));
				glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, curr.pull(format, GL_VERSION_MINOR));
				glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, curr.pull(format, GL_FORWARD_COMPATIBLE) ? GLFW_TRUE : GLFW_FALSE);
				
				//FBO
				
				//windowMode
				long monitorPointer;
				WindowMode windowMode = curr.pull(format, WINDOW_MODE);
				if (windowMode == FULLSCREEN) {
					glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
					monitorPointer = getMonitorPointer(curr.pull(format, MONITOR));
				} else {
					glfwWindowHint(GLFW_DECORATED, windowMode == BORDERLESS ? GLFW_FALSE : GLFW_TRUE);
					curr.reset(MONITOR);
					monitorPointer = 0;
				}
				
				//create
				windowPointer = glfwCreateWindow(curr.pull(format, WINDOW_WIDTH), curr.pull(format, WINDOW_HEIGHT), curr.pull(format, TITLE), monitorPointer, getWindowSharePointer(curr.get(GL_CONTEXT_SHARE)));
			}
		}
		
		@Override
		public void update(IAttributeList format) {
		
		}
		
		@Override
		public void makeContextCurrent() {
		
		}
		
		@Override
		public void swapBuffers() {
		
		}
		
		@Override
		public void pollEvents() {
		
		}
		
		@Override
		public void destroy() {
		
		}
	}
	
	//static window functions
	protected static int covertGLApiTypeToGLFWApi(GLApiType type) {
		switch (type) {
			case GL:
				return GLFW_OPENGL_API;
			case GL_ES:
				return GLFW_OPENGL_ES_API;
			case NONE:
				return GLFW_NO_API;
		}
		throw new IllegalArgumentException("Invalid type: " + type);
	}
	
	protected static int covertGLProfileToGLFWProfile(GLProfile type) {
		switch (type) {
			case PROFILE_ANY:
				return GLFW_OPENGL_ANY_PROFILE;
			case PROFILE_CORE:
				return GLFW_OPENGL_CORE_PROFILE;
			case PROFILE_COMPAT:
				return GLFW_OPENGL_COMPAT_PROFILE;
		}
		throw new IllegalArgumentException("Invalid type: " + type);
	}
	
	protected static long getMonitorPointer(String monitorName) {
		if (monitorName != null && !monitorName.isEmpty()) {
			BufferAllocatorStack allocStack = Side.getSide().get(Side.BUFFER_STACK_ALLOC);
			try {
				allocStack.push();
				Buffer sizeBuffer = allocStack.malloc(8);
				long dest = nglfwGetMonitors(sizeBuffer.address());
				long size = sizeBuffer.getLong(0);
				Buffer list = allocStack.alloc(dest, size);
				
				for (long i = 0; i < size; i += 8) {
					long monitorPointer = list.getLong(i);
					if (monitorName.equals(glfwGetMonitorName(monitorPointer)))
						return monitorPointer;
				}
				
				throw new IllegalArgumentException("Monitor named '" + monitorName + "' not found!");
			} finally {
				allocStack.pop();
			}
		}
		return glfwGetPrimaryMonitor();
	}
	
	protected static long getWindowSharePointer(IWindow windowShare) {
		if (windowShare != null) {
			if (!(windowShare instanceof GLFWWindow))
				throw new IllegalArgumentException("GL_CONTEXT_SHARE was not of type GLFWWindow, instead was " + windowShare.getClass().getName());
			return ((GLFWWindow) windowShare).windowPointer;
		}
		return 0;
	}
	
	//free
	@Override
	public void free() {
		synchronized (GLFWWindowFramework.class) {
			if (instance != this)
				return;
			glfwTerminate();
			instance = null;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			free();
		} finally {
			super.finalize();
		}
	}
	
	//error callback
	public static class GLFWErrorCallback implements GLFWErrorCallbackI {
		
		public static final IndexMap<String> ERROR_MAP = new IndexMapArray<>();
		public static final int ERROR_OFFSET = 0x10000;
		public static final String ERROR_UNKNOWN = "UNKNOWN_ERROR_ID";
		
		static {
			ERROR_MAP.put(0x1, "GLFW_NOT_INITIALIZED");
			ERROR_MAP.put(0x2, "GLFW_NO_CURRENT_CONTEXT");
			ERROR_MAP.put(0x3, "GLFW_INVALID_ENUM");
			ERROR_MAP.put(0x4, "GLFW_INVALID_VALUE");
			ERROR_MAP.put(0x5, "GLFW_OUT_OF_MEMORY");
			ERROR_MAP.put(0x6, "GLFW_API_UNAVAILABLE");
			ERROR_MAP.put(0x7, "GLFW_VERSION_UNAVAILABLE");
			ERROR_MAP.put(0x8, "GLFW_PLATFORM_ERROR");
			ERROR_MAP.put(0x9, "GLFW_FORMAT_UNAVAILABLE");
			ERROR_MAP.put(0xA, "GLFW_NO_WINDOW_CONTEXT");
		}
		
		public Logger logger;
		
		public GLFWErrorCallback(Logger logger) {
			this.logger = logger;
		}
		
		@Override
		public void invoke(int error, long description) {
			String errorString = ERROR_MAP.get(error - ERROR_OFFSET);
			if (errorString == null)
				errorString = ERROR_UNKNOWN;
			
			//@formatter:off
			logger.log(LogLevel.ERROR, new CharBufferBuilder1D<>()
					.append(errorString).append("[0x").append(Integer.toHexString(error)).append("]: ")
					.append(Side.getSide().get(Side.BUFFER_STRING_CONVERTER).memUTF8String(new BufferImpl(description, Integer.MAX_VALUE)))
					.toString());
			//@formatter:on
		}
	}
}
