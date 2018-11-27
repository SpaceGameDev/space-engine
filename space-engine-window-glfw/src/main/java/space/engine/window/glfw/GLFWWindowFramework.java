package space.engine.window.glfw;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import space.engine.Side;
import space.engine.window.Monitor;
import space.engine.window.WindowContext;
import space.engine.window.WindowFramework;
import space.util.buffer.array.ArrayBufferPointer;
import space.util.buffer.direct.alloc.stack.AllocatorStack;
import space.util.buffer.pointer.PointerBufferLong;
import space.util.key.attribute.AttributeList;

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
	public WindowContext createContext(@NotNull AttributeList<WindowContext> format) {
		return new GLFWContext(format, GLFWInstance.instanceRef);
	}

//	@Override
//	public Window createWindow(AttributeList<Window> format) {
//		return new GLFWWindow(this, GLFWInstance.instanceRef, format);
//	}
	
	//monitor
	@NotNull
	@Override
	public Monitor[] getAllMonitors() {
		AttributeList<Side> side = getSide();
		AllocatorStack allocStack = side.get(BUFFER_ALLOC_STACK);
		try {
			allocStack.push();
			PointerBufferLong monitorCnt = side.get(BUFFER_ALLOC_STACK_POINTER).allocLong.malloc();
			ArrayBufferPointer monitorList = side.get(BUFFER_ALLOC_STACK_ARRAY).allocPointer.createNoFree(nglfwGetMonitors(monitorCnt.address()), monitorCnt.getLong());
			
			int length = (int) monitorList.length();
			Monitor[] ret = new Monitor[length];
			for (int i = 0; i < length; i++)
				ret[i] = new GLFWMonitor(monitorList.getPointer(i));
			return ret;
		} finally {
			allocStack.pop();
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
