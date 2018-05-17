package space.engine.window.glfw;

import org.lwjgl.glfw.GLFWErrorCallbackI;
import space.engine.window.exception.WindowFrameworkInitializationException;
import space.util.freeableStorage.FreeableStorage;
import space.util.freeableStorage.FreeableStorageWeak;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWInstance {
	
	public static final Object GLFW_SYNC = new Object();
	
	public static Storage instanceRef;
	public static GLFWErrorCallbackI glfwErrorCallback = new GLFWErrorCallback();
	
	//static
	public static synchronized GLFWInstance getInstance() {
		if (instanceRef != null) {
			GLFWInstance inst = instanceRef.get();
			if (inst != null)
				return inst;
		}
		
		GLFWInstance inst = new GLFWInstance();
		instanceRef = new Storage(inst, FreeableStorage.ROOT_LIST);
		return inst;
	}
	
	//object
	public GLFWInstance() {
		if (!glfwInit())
			throw new WindowFrameworkInitializationException("glfwInit() returned false!");
		glfwSetErrorCallback(glfwErrorCallback);
	}
	
	protected static class Storage extends FreeableStorageWeak<GLFWInstance> {
		
		public Storage(GLFWInstance referent, FreeableStorage getSubList) {
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
