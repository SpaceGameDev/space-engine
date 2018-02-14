package space.engine.render.window.glfw;

import space.engine.render.window.IWindow;
import space.engine.side.Side;
import space.util.buffer.buffers.Buffer;
import space.util.buffer.stack.BufferAllocatorStack;
import space.util.concurrent.event.IEvent;
import space.util.keygen.attribute.AttributeListChangeEventHelper;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListChangeEvent;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListModification;
import space.util.ref.freeable.IFreeableReference;
import space.util.ref.freeable.exception.FreedException;
import space.util.ref.freeable.types.FreeableReference;

import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.render.window.WindowFormat.*;
import static space.engine.render.window.WindowFormat.WindowMode.*;

public class GLFWWindow implements IWindow {
	
	public GLFWWindowFramework windowFramework;
	public Storage storage;
	
	public IAttributeList format;
	public AttributeListChangeEventHelper changeEventHelper;
	
	public GLFWWindow(GLFWWindowFramework windowFramework, IFreeableReference getSubList, IAttributeList format) {
		this.windowFramework = windowFramework;
		this.format = format;
		setupChangeEventHelper();
		
		synchronized (GLFWInstance.GLFW_SYNC) {
			glfwWindowHint(GLFW_VISIBLE, format.get(VISIBLE) ? GLFW_TRUE : GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, format.get(RESIZEABLE) ? GLFW_TRUE : GLFW_FALSE);
			glfwWindowHint(GLFW_DOUBLEBUFFER, format.get(DOUBLEBUFFER) ? GLFW_TRUE : GLFW_FALSE);
			
			//GLApi
			GLApiType glApiType = format.get(GL_API_TYPE);
			glfwWindowHint(GLFW_CLIENT_API, covertGLApiTypeToGLFWApi(glApiType));
			switch (glApiType) {
				case GL:
					glfwWindowHint(GLFW_OPENGL_PROFILE, covertGLProfileToGLFWProfile(format.get(GL_PROFILE)));
					glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, format.get(GL_FORWARD_COMPATIBLE) ? GLFW_TRUE : GLFW_FALSE);
				case GL_ES:
					glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, format.get(GL_VERSION_MAJOR));
					glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, format.get(GL_VERSION_MINOR));
					break;
				case NONE:
					break;
			}
			
			//FBO
			glfwWindowHint(GLFW_RED_BITS, format.get(FBO_R));
			glfwWindowHint(GLFW_GREEN_BITS, format.get(FBO_G));
			glfwWindowHint(GLFW_BLUE_BITS, format.get(FBO_B));
			glfwWindowHint(GLFW_ALPHA_BITS, format.get(FBO_A));
			glfwWindowHint(GLFW_DEPTH_BITS, format.get(FBO_DEPTH));
			glfwWindowHint(GLFW_STENCIL_BITS, format.get(FBO_STENCIL));
			
			//windowMode
			long monitorPointer;
			WindowMode windowMode = format.get(MODE);
			if (windowMode == FULLSCREEN) {
				glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
				monitorPointer = getMonitorPointer(format.get(MONITOR));
			} else {
				glfwWindowHint(GLFW_DECORATED, windowMode == BORDERLESS ? GLFW_FALSE : GLFW_TRUE);
				monitorPointer = 0;
			}
			
			//create
			storage = new Storage(this, getSubList, glfwCreateWindow(format.get(WIDTH), format.get(HEIGHT), format.get(TITLE), monitorPointer, getWindowSharePointer(format.get(GL_CONTEXT_SHARE))));
		}
	}
	
	public void setupChangeEventHelper() {
		changeEventHelper = new AttributeListChangeEventHelper();
		changeEventHelper.put(VISIBLE, entry -> {
			if (entry.getNew())
				glfwShowWindow(storage.getWindowPointer());
			else
				glfwHideWindow(storage.getWindowPointer());
		});
		changeEventHelper.put(RESIZEABLE, entry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_RESIZABLE, entry.getNew() ? GLFW_TRUE : GLFW_FALSE));
		changeEventHelper.put(DOUBLEBUFFER, entry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_DOUBLEBUFFER, entry.getNew() ? GLFW_TRUE : GLFW_FALSE));
		
		IEvent<Consumer<IAttributeListChangeEvent>> hookable = format.getChangeEvent();
		hookable.addHook(changeEventHelper);
		
		//windowMode
		hookable.addHook(changeEvent -> {
			IAttributeListModification mod = changeEvent.getMod();
			if (mod.isNotUnchanged(WIDTH) || mod.isNotUnchanged(HEIGHT) || mod.isNotUnchanged(MODE) || mod.isNotUnchanged(MONITOR)) {
				long monitorPointer;
				WindowMode windowMode = format.get(MODE);
				if (windowMode == FULLSCREEN) {
					glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_DECORATED, GLFW_TRUE);
					monitorPointer = getMonitorPointer(format.get(MONITOR));
				} else {
					glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_DECORATED, windowMode == BORDERLESS ? GLFW_FALSE : GLFW_TRUE);
					monitorPointer = 0;
				}
				//FIXME: default refresh rate
				glfwSetWindowMonitor(storage.getWindowPointer(), monitorPointer, changeEvent.getEntry(POSX).getNew(), changeEvent.getEntry(POSY).getNew(), changeEvent.getEntry(WIDTH).getNew(), changeEvent.getEntry(HEIGHT).getNew(), changeEvent.getEntry(REFRESH_RATE).getNew());
			} else if (mod.isNotUnchanged(POSX) || mod.isNotUnchanged(POSX)) {
				glfwSetWindowPos(storage.getWindowPointer(), changeEvent.getEntry(POSX).getNew(), changeEvent.getEntry(POSY).getNew());
			}
		});
	}
	
	@Override
	public void makeContextCurrent() {
		glfwMakeContextCurrent(storage.getWindowPointer());
	}
	
	@Override
	public void swapBuffers() {
		glfwSwapBuffers(storage.getWindowPointer());
	}
	
	@Override
	public void pollEvents() {
		glfwPollEvents();
	}
	
	@Override
	public void free() {
		storage.free();
	}
	
	public static class Storage extends FreeableReference {
		
		private long windowPointer;
		
		public Storage(Object referent, IFreeableReference getSubList, long windowPointer) {
			super(referent, getSubList);
			this.windowPointer = windowPointer;
		}
		
		@Override
		protected void handleFree() {
			glfwDestroyWindow(windowPointer);
		}
		
		public long getWindowPointer() throws FreedException {
			throwIfFreed();
			return windowPointer;
		}
	}
	
	//static
	protected static int covertGLApiTypeToGLFWApi(GLApiType type) {
		switch (type) {
			case GL:
				return GLFW_OPENGL_API;
			case GL_ES:
				return GLFW_OPENGL_ES_API;
			case NONE:
				return GLFW_NO_API;
		}
		throw new IllegalArgumentException("Invalid type: " + type);
	}
	
	protected static int covertGLProfileToGLFWProfile(GLProfile type) {
		switch (type) {
			case PROFILE_ANY:
				return GLFW_OPENGL_ANY_PROFILE;
			case PROFILE_CORE:
				return GLFW_OPENGL_CORE_PROFILE;
			case PROFILE_COMPAT:
				return GLFW_OPENGL_COMPAT_PROFILE;
		}
		throw new IllegalArgumentException("Invalid type: " + type);
	}
	
	protected static long getMonitorPointer(String monitorName) {
		if (monitorName == null || monitorName.isEmpty())
			return glfwGetPrimaryMonitor();
		
		BufferAllocatorStack allocStack = Side.getSide().get(Side.BUFFER_STACK_ALLOC);
		try {
			allocStack.push();
			Buffer sizeBuffer = allocStack.malloc(8);
			long dest = nglfwGetMonitors(sizeBuffer.address());
			long size = sizeBuffer.getLong(0);
			Buffer list = allocStack.alloc(dest, size);
			
			for (long i = 0; i < size; i += 8) {
				long monitorPointer = list.getLong(i);
				if (monitorName.equals(glfwGetMonitorName(monitorPointer)))
					return monitorPointer;
			}
			throw new IllegalArgumentException("Monitor named '" + monitorName + "' not found!");
		} finally {
			allocStack.pop();
		}
	}
	
	protected static long getWindowSharePointer(IWindow windowShare) {
		if (windowShare == null)
			return 0;
		
		if (!(windowShare instanceof GLFWWindow))
			throw new IllegalArgumentException("GL_CONTEXT_SHARE was not of type GLFWWindow, instead was " + windowShare.getClass().getName());
		return ((GLFWWindow) windowShare).storage.getWindowPointer();
	}
}
