package space.engine.render.window.glfw;

import space.engine.render.window.exception.WindowFrameworkInitializationException;
import space.util.logger.Logger;
import space.util.ref.freeable.FreeableReferenceCleaner;
import space.util.ref.freeable.IFreeableReference;
import space.util.ref.freeable.types.FreeableReferenceWeak;

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
		instanceRef = new Storage(inst, FreeableReferenceCleaner.LIST_ROOT);
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
	
	protected static class Storage extends FreeableReferenceWeak<GLFWInstance> {
		
		public Storage(GLFWInstance referent, IFreeableReference getSubList) {
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
