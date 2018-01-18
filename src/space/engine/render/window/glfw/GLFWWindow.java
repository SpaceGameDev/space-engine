package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFW;
import space.engine.render.window.IWindow;
import space.engine.render.window.WindowFormat;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;

public class GLFWWindow implements IWindow {
	
	public long windowPointer;
	public IAttributeList curr = WindowFormat.ATTRIBUTE_LIST_CREATOR.create();
	
	public GLFWWindow(IAttributeList format) {
		windowPointer = GLFW.glfwCreateWindow(curr.push(WindowFormat.WINDOW_WIDTH, format), curr.push(WindowFormat.WINDOW_HEIGHT, format), curr.push(WindowFormat.TITLE, format), (long) curr.push(WindowFormat.MONTIOR, format), (long) curr.push(WindowFormat.GL_CONTEXT_SHARE, format));
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
