package space.engine.window.glfw;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import space.engine.window.Monitor;
import space.engine.window.Window;
import space.engine.window.WindowContext;
import space.engine.window.WindowFramework;
import space.util.buffer.array.ArrayBufferLong;
import space.util.buffer.array.ArrayBufferLong.ArrayBufferLongSingle;
import space.util.buffer.array.ArrayBufferPointer;
import space.util.buffer.stack.BufferAllocatorStack;
import space.util.key.attribute.AttributeListCreator.IAttributeList;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.Side.*;

public class GLFWWindowFramework implements WindowFramework {
	
	/**
	 * finalization of the object will {@link GLFW#glfwTerminate()}
	 */
	@SuppressWarnings("unused")
	public GLFWInstance glfwInstance = GLFWInstance.getInstance();
	
	//window
	@NotNull
	@Override
	public WindowContext createContext(IAttributeList<WindowContext> format) {
		return null;
	}
	
	@Override
	public Window createWindow(IAttributeList<Window> format) {
		return new GLFWWindow(this, GLFWInstance.instanceRef, format);
	}
	
	//monitor
	@NotNull
	@Override
	public Monitor[] getAllMonitors() {
		BufferAllocatorStack alloc = getSide().get(BUFFER_STACK_ALLOC);
		try {
			alloc.push();
			ArrayBufferLongSingle monitorCnt = ArrayBufferLong.mallocSingle(alloc::malloc);
			ArrayBufferPointer monitorList = ArrayBufferPointer.alloc(alloc::allocNoFree, nglfwGetMonitors(monitorCnt.address()), monitorCnt.getLong());
			
			int length = (int) monitorList.length();
			Monitor[] ret = new Monitor[length];
			for (int i = 0; i < length; i++)
				ret[i] = new GLFWMonitor(monitorList.getPointer(i));
			return ret;
		} finally {
			alloc.pop();
		}
	}
	
	@NotNull
	@Override
	public Monitor getPrimaryMonitor() {
		return new GLFWMonitor(glfwGetPrimaryMonitor());
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
