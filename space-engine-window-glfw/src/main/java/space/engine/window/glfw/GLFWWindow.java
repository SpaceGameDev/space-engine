package space.engine.window.glfw;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Freeable.FreeableWithStorage;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.freeableStorage.FreeableStorageImpl;
import space.engine.key.attribute.AttributeList;
import space.engine.sync.TaskCreator;
import space.engine.window.VideoMode;
import space.engine.window.VideoMode.VideoModeDesktop;
import space.engine.window.Window;
import space.engine.window.glfw.GLFWMonitor.GLFWVideoModeMonitor;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.window.glfw.GLFWUtil.toGLFWBoolean;

public class GLFWWindow implements Window, FreeableWithStorage {
	
	/**
	 * @implNote prevents gc / freeing of WindowFramework and with that destruction of the window
	 */
	public final GLFWContext context;
	public final AttributeList<Window> format;
	public final FreeableStorage dummy;
	
	public Storage storage;
	
	public GLFWWindow(GLFWContext context, AttributeList<Window> format, FreeableStorage... parents) {
		this.context = context;
		this.format = format;
		this.dummy = FreeableStorage.createDummy(parents);
		setup();
	}
	
	//FIXME never called
	protected void setup() {
		VideoMode videoModeDirect = format.get(VIDEO_MODE);
		synchronized (GLFWInstance.GLFW_SYNC) {
			glfwWindowHint(GLFW_VISIBLE, toGLFWBoolean(format.get(VISIBLE)));
			glfwWindowHint(GLFW_DOUBLEBUFFER, toGLFWBoolean(format.get(DOUBLE_BUFFER)));
			
			glfwWindowHint(GLFW_AUX_BUFFERS, 0);
			glfwWindowHint(GLFW_ACCUM_RED_BITS, 0);
			glfwWindowHint(GLFW_ACCUM_GREEN_BITS, 0);
			glfwWindowHint(GLFW_ACCUM_BLUE_BITS, 0);
			glfwWindowHint(GLFW_ACCUM_ALPHA_BITS, 0);
			glfwWindowHint(GLFW_SAMPLES, 0);
			
			glfwWindowHint(GLFW_DEPTH_BITS, format.get(DEPTH_BITS));
			glfwWindowHint(GLFW_STENCIL_BITS, format.get(STENCIL_BITS));
			
			long windowPointer;
			if (videoModeDirect instanceof VideoMode.VideoModeDesktop) {
				VideoModeDesktop videoMode = (VideoModeDesktop) videoModeDirect;
				boolean hasTransparency = videoMode.hasTransparency();
				
				//window
				glfwWindowHint(GLFW_DECORATED, toGLFWBoolean(!format.get(BORDERLESS)));
				glfwWindowHint(GLFW_RESIZABLE, toGLFWBoolean(format.get(RESIZEABLE)));
				glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, toGLFWBoolean(hasTransparency));
				
				//fbo
				glfwWindowHint(GLFW_RED_BITS, GLFW_DONT_CARE);
				glfwWindowHint(GLFW_GREEN_BITS, GLFW_DONT_CARE);
				glfwWindowHint(GLFW_BLUE_BITS, GLFW_DONT_CARE);
				glfwWindowHint(GLFW_ALPHA_BITS, hasTransparency ? GLFW_DONT_CARE : format.get(ALPHA_BITS));
				glfwWindowHint(GLFW_REFRESH_RATE, GLFW_DONT_CARE);
				
				//createWindow
				windowPointer = glfwCreateWindow(videoMode.width(), videoMode.height(), format.get(TITLE), 0, context.storage.getWindowPointer());
			} else if (videoModeDirect instanceof GLFWVideoModeMonitor) {
				GLFWVideoModeMonitor videoMode = (GLFWVideoModeMonitor) videoModeDirect;
				
				//fbo
				glfwWindowHint(GLFW_RED_BITS, videoMode.bitsR());
				glfwWindowHint(GLFW_GREEN_BITS, videoMode.bitsG());
				glfwWindowHint(GLFW_BLUE_BITS, videoMode.bitsB());
				glfwWindowHint(GLFW_ALPHA_BITS, format.get(ALPHA_BITS));
				glfwWindowHint(GLFW_REFRESH_RATE, videoMode.refreshRate());
				
				//createWindow
				windowPointer = glfwCreateWindow(videoMode.width(), videoMode.height(), format.get(TITLE), videoMode.getMonitor().pointer, context.storage.getWindowPointer());
			} else {
				throw new IllegalStateException("format[VIDEO_MODE] was unsupported type: " + videoModeDirect.getClass().getName());
			}
			storage = new Storage(this, windowPointer, dummy);
		}
	}
	
	@Override
	public void execute(@NotNull Runnable command) {
		command.run();
	}
	
	@Override
	public TaskCreator openGL_SwapBuffer(int opengl_texture_id) {
		throw new UnsupportedOperationException("Not implemented!");
	}
	
	@Override
	public TaskCreator openGL_ES_SwapBuffer(int opengl_es_texture_id) {
		throw new UnsupportedOperationException("Not implemented!");
	}
	
	@Override
	public void swapBuffers() {
		glfwSwapBuffers(storage.getWindowPointer());
	}
	
	//storage
	@Override
	public @NotNull FreeableStorage getStorage() {
		return storage;
	}
	
	public static class Storage extends FreeableStorageImpl {
		
		private long windowPointer;
		
		public Storage(Object referent, long windowPointer, FreeableStorage... getSubList) {
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
}
