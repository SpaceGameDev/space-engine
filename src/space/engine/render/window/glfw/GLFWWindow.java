package space.engine.render.window.glfw;

import space.engine.render.window.IMonitor.IVideoMode;
import space.engine.render.window.IWindow;
import space.engine.render.window.exception.WindowException;
import space.engine.render.window.glfw.GLFWMonitor.GLFWVideoMode;
import space.util.baseobject.additional.Freeable.FreeableWithStorage;
import space.util.baseobject.exceptions.FreedException;
import space.util.concurrent.event.IEvent;
import space.util.freeableStorage.FreeableStorage;
import space.util.freeableStorage.IFreeableStorage;
import space.util.keygen.attribute.AttributeListChangeEventHelper;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListChangeEvent;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListModification;
import space.util.string.builder.CharBufferBuilder2D;

import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.render.window.WindowFormat.*;
import static space.engine.render.window.WindowFormat.WindowMode.*;

public class GLFWWindow implements IWindow, FreeableWithStorage {
	
	public GLFWWindowFramework windowFramework;
	public Storage storage;
	
	public IAttributeList format;
	public AttributeListChangeEventHelper changeEventHelper;
	
	public GLFWWindow(GLFWWindowFramework windowFramework, IFreeableStorage getSubList, IAttributeList format) {
		this.windowFramework = windowFramework;
		this.format = format;
		setupChangeEventHelper();
		
		//main window settings
		WindowMode windowMode = format.get(WINDOW_MODE);
		boolean fullscreen = windowMode == FULLSCREEN;
		
		IVideoMode videoMode = format.get(VIDEO_MODE);
		GLFWMonitor monitor = null;
		if (fullscreen) {
			if (!(videoMode instanceof GLFWVideoMode))
				throw new WindowException(new CharBufferBuilder2D<>().append("VIDEO_MODE was not of Type GLFWVideoMode, instead was").append(videoMode.getClass().getName()).append(": ").append(videoMode).toString());
			monitor = ((GLFWVideoMode) videoMode).getMonitor();
		}
		
		synchronized (GLFWInstance.GLFW_SYNC) {
			//main window settings
			glfwWindowHint(GLFW_REFRESH_RATE, fullscreen ? videoMode.refreshRate() : GLFW_DONT_CARE);
			glfwWindowHint(GLFW_DECORATED, fullscreen ? GLFW_DONT_CARE : windowMode == BORDERLESS ? GLFW_FALSE : GLFW_TRUE);
			
			//additional window settings
			String title = format.get(TITLE);
			glfwWindowHint(GLFW_VISIBLE, format.get(VISIBLE) ? GLFW_TRUE : GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, format.get(RESIZEABLE) ? GLFW_TRUE : GLFW_FALSE);
			glfwWindowHint(GLFW_DOUBLEBUFFER, format.get(DOUBLEBUFFER) ? GLFW_TRUE : GLFW_FALSE);
			
			//gl api settings
			GLApiType glApiType = format.get(GL_API_TYPE);
			glfwWindowHint(GLFW_CLIENT_API, covertGLApiTypeToGLFWApi(glApiType));
			long windowSharePointer = 0;
			switch (glApiType) {
				case GL:
					glfwWindowHint(GLFW_OPENGL_PROFILE, covertGLProfileToGLFWProfile(format.get(GL_PROFILE)));
					glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, format.get(GL_FORWARD_COMPATIBLE) ? GLFW_TRUE : GLFW_FALSE);
				case GL_ES:
					glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, format.get(GL_VERSION_MAJOR));
					glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, format.get(GL_VERSION_MINOR));
					windowSharePointer = getWindowSharePointer(format.get(GL_CONTEXT_SHARE));
					break;
				case NONE:
					break;
			}
			
			//fbo
			glfwWindowHint(GLFW_RED_BITS, fullscreen ? videoMode.bitsR() : GLFW_DONT_CARE);
			glfwWindowHint(GLFW_GREEN_BITS, fullscreen ? videoMode.bitsG() : GLFW_DONT_CARE);
			glfwWindowHint(GLFW_BLUE_BITS, fullscreen ? videoMode.bitsB() : GLFW_DONT_CARE);
			glfwWindowHint(GLFW_ALPHA_BITS, format.get(FBO_A));
			glfwWindowHint(GLFW_DEPTH_BITS, format.get(FBO_DEPTH));
			glfwWindowHint(GLFW_STENCIL_BITS, format.get(FBO_STENCIL));
			
			//create
			storage = new Storage(this, getSubList, glfwCreateWindow(videoMode.width(), videoMode.height(), title, monitor != null ? monitor.pointer : 0, windowSharePointer));
		}
	}
	
	public void setupChangeEventHelper() {
		IEvent<Consumer<IAttributeListChangeEvent>> hookable = format.getChangeEvent();
		AttributeListChangeEventHelper changeEventHelper = new AttributeListChangeEventHelper();
		changeEventHelper.put(VISIBLE, entry -> {
			if (entry.getNew())
				glfwShowWindow(storage.getWindowPointer());
			else
				glfwHideWindow(storage.getWindowPointer());
		});
		changeEventHelper.put(RESIZEABLE, entry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_RESIZABLE, entry.getNew() ? GLFW_TRUE : GLFW_FALSE));
		changeEventHelper.put(DOUBLEBUFFER, entry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_DOUBLEBUFFER, entry.getNew() ? GLFW_TRUE : GLFW_FALSE));
		changeEventHelper.put(TITLE, entry -> glfwSetWindowTitle(storage.getWindowPointer(), entry.getNew()));
		hookable.addHook(this.changeEventHelper = changeEventHelper);
		
		//windowMode
		hookable.addHook(changeEvent -> {
			IAttributeListModification mod = changeEvent.getMod();
			if (mod.isNotUnchanged(WIDTH) || mod.isNotUnchanged(HEIGHT) || mod.isNotUnchanged(WINDOW_MODE) || mod.isNotUnchanged(MONITOR)) {
				long monitorPointer;
				WindowMode windowMode = format.get(WINDOW_MODE);
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
	public IFreeableStorage getStorage() {
		return storage;
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
	
	public static class Storage extends FreeableStorage {
		
		private long windowPointer;
		
		public Storage(Object referent, IFreeableStorage getSubList, long windowPointer) {
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
	
	protected static long getWindowSharePointer(IWindow windowShare) {
		if (windowShare == null)
			return 0;
		if ((windowShare instanceof GLFWWindow))
			return ((GLFWWindow) windowShare).storage.getWindowPointer();
		throw new IllegalArgumentException("GL_CONTEXT_SHARE was not of type GLFWWindow, instead was " + windowShare.getClass().getName());
	}
}
