package space.engine.window.glfw;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import space.engine.Side;
import space.engine.buffer.array.ArrayBufferPointer;
import space.engine.buffer.direct.alloc.stack.AllocatorStack;
import space.engine.buffer.pointer.PointerBufferLong;
import space.engine.key.attribute.AttributeList;
import space.engine.sync.future.Future;
import space.engine.window.Monitor;
import space.engine.window.WindowContext;
import space.engine.window.WindowFramework;
import space.engine.window.extensions.BorderlessExtension;
import space.engine.window.extensions.ResizeableExtension;
import space.engine.window.extensions.VideoModeDesktopExtension;
import space.engine.window.extensions.VideoModeFullscreenExtension;
import space.engine.window.extensions.WindowExtension;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
	public Future<? extends WindowContext> createContext(@NotNull AttributeList<WindowContext> format) {
		return GLFWContext.create(this, format, GLFWInstance.instanceRef);
	}
	
	@Override
	public Collection<Class<? extends WindowExtension>> getSupportedWindowExtensions() {
		return List.of(BorderlessExtension.class, ResizeableExtension.class, VideoModeDesktopExtension.class, VideoModeFullscreenExtension.class);
	}
	
	//monitor
	@NotNull
	@Override
	public Future<Collection<? extends Monitor>> getAllMonitors() {
		return Future.finished(getAllMonitorsDirect());
	}
	
	public Collection<GLFWMonitor> getAllMonitorsDirect() {
		AttributeList<Side> side = side();
		AllocatorStack allocStack = side.get(BUFFER_ALLOC_STACK);
		try {
			allocStack.push();
			PointerBufferLong monitorCnt = side.get(BUFFER_ALLOC_STACK_POINTER).allocLong.malloc();
			long monitorAddress = nglfwGetMonitors(monitorCnt.address());
			ArrayBufferPointer monitorList = side.get(BUFFER_ALLOC_STACK_ARRAY).allocPointer.createNoFree(monitorAddress, monitorCnt.getLong());
			
			return LongStream.range(0, monitorList.length())
							 .mapToObj(i -> new GLFWMonitor(monitorList.getPointer(i)))
							 .collect(Collectors.toList());
		} finally {
			allocStack.pop();
		}
	}
	
	@NotNull
	@Override
	public Future<? extends Monitor> getPrimaryMonitor() {
		return Future.finished(getPrimaryMonitorDirect());
	}
	
	@NotNull
	public GLFWMonitor getPrimaryMonitorDirect() {
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
