package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFW;
import space.engine.render.window.IWindowFramework;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;
import space.util.logger.Logger;

public class GLFWWindowFramework implements IWindowFramework<GLFWWindow> {
	
	//static
	public static void setLogger(Logger logger) {
		GLFWInstance.setLogger(logger);
	}
	
	//object
	/**
	 * finalization of the object below will {@link GLFW#glfwTerminate()}
	 */
	@SuppressWarnings("unused")
	public GLFWInstance glfwInstance = GLFWInstance.getInstance();
	
	@Override
	public GLFWWindow create(IAttributeList format) {
		return new GLFWWindow(this, GLFWInstance.instanceRef, format);
	}
	
	@Override
	public void free() {
	
	}
}
