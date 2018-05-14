package space.engine.render.window.glfw;

import space.engine.render.window.Window;
import space.engine.render.window.WindowMonitor.IVideoMode;
import space.engine.render.window.exception.WindowException;
import space.engine.render.window.glfw.GLFWMonitor.GLFWVideoMode;
import space.util.baseobject.Freeable.FreeableWithStorage;
import space.util.baseobject.exceptions.FreedException;
import space.util.freeableStorage.FreeableStorage;
import space.util.freeableStorage.FreeableStorageImpl;
import space.util.key.attribute.AttributeListChangeEventHelper;
import space.util.key.attribute.AttributeListCreator.ChangeEvent;
import space.util.key.attribute.AttributeListCreator.ChangeEventEntry;
import space.util.key.attribute.AttributeListCreator.IAttributeList;
import space.util.string.builder.CharBufferBuilder2D;

import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.render.window.Window.WindowMode.*;
import static space.engine.render.window.glfw.GLFWUtil.toGLFWBoolean;

public class GLFWWindow implements Window, FreeableWithStorage {
	
	public GLFWWindowFramework windowFramework;
	public Storage storage;
	
	public IAttributeList<Window> format;
	public AttributeListChangeEventHelper changeEventHelper;
	
	public GLFWWindow(GLFWWindowFramework windowFramework, FreeableStorage getSubList, IAttributeList<Window> format) {
		this.windowFramework = windowFramework;
		this.format = format;
		setupChangeEventHelper();
		
		//main window settings
		WindowMode windowMode = format.get(WINDOW_MODE);
		boolean fullscreen = windowMode == FULLSCREEN;
		
		IVideoMode<?> videoMode = format.get(VIDEO_MODE);
		GLFWMonitor monitor = null;
		if (fullscreen) {
			checkVideoMode(videoMode);
			monitor = ((GLFWVideoMode) videoMode).getMonitor();
		}
		
		synchronized (GLFWInstance.GLFW_SYNC) {
			//main window settings
			glfwWindowHint(GLFW_REFRESH_RATE, fullscreen ? videoMode.refreshRate() : GLFW_DONT_CARE);
			glfwWindowHint(GLFW_DECORATED, toGLFWBoolean(windowMode != BORDERLESS));
			
			//additional window settings
			String title = format.get(TITLE);
			glfwWindowHint(GLFW_VISIBLE, toGLFWBoolean(format.get(VISIBLE)));
			glfwWindowHint(GLFW_RESIZABLE, toGLFWBoolean(format.get(RESIZEABLE)));
			glfwWindowHint(GLFW_DOUBLEBUFFER, toGLFWBoolean(format.get(DOUBLEBUFFER)));
			
			//gl api settings
			GLApiType glApiType = format.get(GL_API_TYPE);
			glfwWindowHint(GLFW_CLIENT_API, covertGLApiTypeToGLFWApi(glApiType));
			long windowSharePointer = 0;
			switch (glApiType) {
				case GL:
					glfwWindowHint(GLFW_OPENGL_PROFILE, covertGLProfileToGLFWProfile(format.get(GL_PROFILE)));
					glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, toGLFWBoolean(format.get(GL_FORWARD_COMPATIBLE)));
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
		AttributeListChangeEventHelper changeEventHelper = new AttributeListChangeEventHelper();
		
		//main window settings
		Consumer<ChangeEvent<?>> windowChange = changeEvent -> {
			ChangeEventEntry<WindowMode> windowMode = changeEvent.getEntry(WINDOW_MODE);
			ChangeEventEntry<IVideoMode<?>> videoMode = changeEvent.getEntry(VIDEO_MODE);
			ChangeEventEntry<Integer> posx = changeEvent.getEntry(POSX);
			ChangeEventEntry<Integer> posy = changeEvent.getEntry(POSY);
			
			if (videoMode.hasChanged() || windowMode.hasChanged()) {
				IVideoMode<?> videoModeNew = videoMode.getNew();
				long monitorPointer = 0;
				if (windowMode.getNew() == FULLSCREEN) {
					checkVideoMode(videoModeNew);
					monitorPointer = ((GLFWVideoMode) videoModeNew).getMonitor().pointer;
				}
				glfwSetWindowMonitor(storage.getWindowPointer(), monitorPointer, posx.getNew(), posy.getNew(), videoModeNew.width(), videoModeNew.height(), videoModeNew.refreshRate());
			} else if (posx.hasChanged() || posy.hasChanged()) {
				glfwSetWindowPos(storage.getWindowPointer(), posx.getNew(), posy.getNew());
			}
		};
		changeEventHelper.putEntry(changeEventEntry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_DECORATED, toGLFWBoolean(changeEventEntry.getNew() == BORDERLESS)), WINDOW_MODE);
		
		//additional window settings
		changeEventHelper.putEntry(changeEventEntry -> glfwSetWindowTitle(storage.getWindowPointer(), changeEventEntry.getNew()), TITLE);
		changeEventHelper.putEntry(changeEventEntry -> {
			if (changeEventEntry.getNew())
				glfwShowWindow(storage.getWindowPointer());
			else
				glfwHideWindow(storage.getWindowPointer());
		}, VISIBLE);
		changeEventHelper.putEntry(changeEventEntry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_RESIZABLE, toGLFWBoolean(changeEventEntry.getNew())), RESIZEABLE);
		changeEventHelper.putEntry(changeEventEntry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_DOUBLEBUFFER, toGLFWBoolean(changeEventEntry.getNew())), DOUBLEBUFFER);
		
		//fbo
		changeEventHelper.putEntry(changeEventEntry -> {
			IVideoMode<?> videoMode = changeEventEntry.getNew();
			glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_RED_BITS, videoMode.bitsR());
			glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_GREEN_BITS, videoMode.bitsG());
			glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_BLUE_BITS, videoMode.bitsB());
		}, VIDEO_MODE);
		changeEventHelper.putEntry(changeEventEntry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_ALPHA_BITS, changeEventEntry.getNew()), FBO_A);
		changeEventHelper.putEntry(changeEventEntry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_DEPTH_BITS, changeEventEntry.getNew()), FBO_DEPTH);
		changeEventHelper.putEntry(changeEventEntry -> glfwSetWindowAttrib(storage.getWindowPointer(), GLFW_STENCIL_BITS, changeEventEntry.getNew()), FBO_STENCIL);
		
		//added last so it is executed last -> all changes within one update
		changeEventHelper.put(windowChange, WINDOW_MODE, VIDEO_MODE, POSX, POSY);
		format.getChangeEvent().addHook(changeEventHelper);
	}
	
	@Override
	public FreeableStorage getStorage() {
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
	
	protected static long getWindowSharePointer(Window windowShare) {
		if (windowShare == null)
			return 0;
		if ((windowShare instanceof GLFWWindow))
			return ((GLFWWindow) windowShare).storage.getWindowPointer();
		throw new IllegalArgumentException("GL_CONTEXT_SHARE was not of type GLFWWindow, instead was " + windowShare.getClass().getName());
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
	
	public static class Storage extends FreeableStorageImpl {
		
		private long windowPointer;
		
		public Storage(Object referent, FreeableStorage getSubList, long windowPointer) {
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
	
	private static void checkVideoMode(IVideoMode<?> videoMode) {
		if (!(videoMode instanceof GLFWVideoMode))
			throw new WindowException(new CharBufferBuilder2D<>().append("VIDEO_MODE was not of Type GLFWVideoMode, instead was").append(videoMode.getClass().getName()).append(": ").append(videoMode).toString());
	}
}
