package space.engine.render.window.glfw;

import space.engine.render.window.exception.WindowFrameworkInitializationException;
import space.util.logger.Logger;
import space.util.ref.freeable.FreeableStorageWeak;
import space.util.ref.freeable.IFreeableStorage;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWInstance {
	
	public static final Object GLFW_SYNC = new Object();
	
	public static Storage instanceRef;
	public static GLFWErrorCallback glfwErrorCallback;
	
	//static
	public static synchronized GLFWInstance getInstance() {
		if (instanceRef != null) {
			GLFWInstance inst = instanceRef.get();
			if (inst != null)
				return inst;
		}
		
		GLFWInstance inst = new GLFWInstance();
		instanceRef = new Storage(inst, IFreeableStorage.ROOT_LIST);
		return inst;
	}
	
	public static synchronized void setLogger(Logger logger) {
		glfwSetErrorCallback(glfwErrorCallback = new GLFWErrorCallback(logger.subLogger("GLFW")));
	}
	
	//object
	public GLFWInstance() {
		if (!glfwInit())
			throw new WindowFrameworkInitializationException("glfwInit() returns false!");
	}
	
	protected static class Storage extends FreeableStorageWeak<GLFWInstance> {
		
		public Storage(GLFWInstance referent, IFreeableStorage getSubList) {
			super(referent, getSubList);
		}
		
		@Override
		protected void handleFree() {
			synchronized (GLFWInstance.class) {
				if (this == instanceRef) {
					instanceRef = null;
					glfwTerminate();
				}
			}
		}
	}
}
