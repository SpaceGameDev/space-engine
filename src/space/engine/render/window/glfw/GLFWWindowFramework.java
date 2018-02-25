package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFW;
import space.engine.render.window.IWindowFramework;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;

public class GLFWWindowFramework implements IWindowFramework<GLFWWindow> {
	
	//object
	/**
	 * finalization of the object will {@link GLFW#glfwTerminate()}
	 */
	@SuppressWarnings("unused")
	public GLFWInstance glfwInstance = GLFWInstance.getInstance();
	
	@Override
	public GLFWWindow create(IAttributeList format) {
		return new GLFWWindow(this, GLFWInstance.instanceRef, format);
	}
	
	//free
	@Override
	public void free() {
	
	}
	
	@Override
	public boolean isFreed() {
		return false;
	}
}
