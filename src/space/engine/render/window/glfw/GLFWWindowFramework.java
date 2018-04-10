package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFW;
import space.engine.render.window.WindowFramework;
import space.engine.render.window.WindowMonitor;
import space.util.buffer.array.ArrayBufferLong;
import space.util.buffer.array.ArrayBufferLong.ArrayBufferLongSingle;
import space.util.buffer.array.ArrayBufferPointer;
import space.util.buffer.stack.BufferAllocatorStack;
import space.util.key.attribute.AttributeListCreator.IAttributeList;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.side.Side.*;

public class GLFWWindowFramework implements WindowFramework<GLFWWindow> {
	
	/**
	 * finalization of the object will {@link GLFW#glfwTerminate()}
	 */
	@SuppressWarnings("unused")
	public GLFWInstance glfwInstance = GLFWInstance.getInstance();
	
	//window
	@Override
	public GLFWWindow create(IAttributeList format) {
		return new GLFWWindow(this, GLFWInstance.instanceRef, format);
	}
	
	//monitor
	@Override
	public WindowMonitor[] getMonitors() {
		BufferAllocatorStack alloc = getSide().get(BUFFER_STACK_ALLOC);
		try {
			alloc.push();
			ArrayBufferLongSingle monitorCnt = ArrayBufferLong.mallocSingle(alloc::malloc);
			ArrayBufferPointer monitorList = ArrayBufferPointer.alloc(alloc::allocNoFree, nglfwGetMonitors(monitorCnt.address()), monitorCnt.getLong());
			
			int length = (int) monitorList.length();
			WindowMonitor[] ret = new WindowMonitor[length];
			for (int i = 0; i < length; i++)
				ret[i] = new GLFWMonitor(monitorList.getPointer(i));
			return ret;
		} finally {
			alloc.pop();
		}
	}
	
	@Override
	public WindowMonitor getPrimaryMonitor() {
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
