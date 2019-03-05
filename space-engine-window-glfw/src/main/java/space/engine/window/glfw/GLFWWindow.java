package space.engine.window.glfw;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Freeable.FreeableWithStorage;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.event.EventEntry;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.freeableStorage.FreeableStorageImpl;
import space.engine.key.attribute.AbstractAttributeList;
import space.engine.key.attribute.AttributeList;
import space.engine.sync.TaskCreator;
import space.engine.sync.barrier.Barrier;
import space.engine.sync.future.Future;
import space.engine.window.VideoMode;
import space.engine.window.VideoMode.VideoModeDesktop;
import space.engine.window.Window;
import space.engine.window.glfw.GLFWMonitor.GLFWVideoModeMonitor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.sync.Tasks.runnable;
import static space.engine.window.glfw.GLFWUtil.toGLFWBoolean;

public class GLFWWindow implements Window, FreeableWithStorage {
	
	private static final AtomicInteger WINDOW_THREAD_COUNTER = new AtomicInteger();
	
	/**
	 * keep this reference to prevent freeing of WindowFramework
	 */
	public final @NotNull GLFWContext context;
	public final @NotNull AttributeList<Window> format;
	public final Storage storage;
	
	public static @NotNull Future<GLFWWindow> create(@NotNull GLFWContext context, @NotNull AttributeList<Window> format, FreeableStorage... parents) {
		GLFWWindow glfwWindow = new GLFWWindow(context, format, parents);
		Barrier initTask = runnable(glfwWindow.storage, () -> glfwWindow.initializeNativeWindow(format)).submit();
		format.addHook(new EventEntry<>((modify, changes) -> runnable(glfwWindow.storage, () -> glfwWindow.initializeNativeWindow(modify)).submit()));
		return initTask.toFuture(() -> glfwWindow);
	}
	
	private GLFWWindow(@NotNull GLFWContext context, @NotNull AttributeList<Window> format, FreeableStorage... parents) {
		this.context = context;
		this.format = format;
		this.storage = new Storage(this, parents);
	}
	
	@Override
	public void execute(@NotNull Runnable command) {
		storage.execute(command);
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
	
	//windowThread
	@WindowThread
	private void initializeNativeWindow(AbstractAttributeList<Window> newFormat) {
		deleteNativeWindow();
		
		synchronized (GLFWInstance.GLFW_SYNC) {
			glfwWindowHint(GLFW_VISIBLE, toGLFWBoolean(newFormat.get(VISIBLE)));
			glfwWindowHint(GLFW_DOUBLEBUFFER, toGLFWBoolean(newFormat.get(DOUBLE_BUFFER)));
			
			glfwWindowHint(GLFW_AUX_BUFFERS, 0);
			glfwWindowHint(GLFW_ACCUM_RED_BITS, 0);
			glfwWindowHint(GLFW_ACCUM_GREEN_BITS, 0);
			glfwWindowHint(GLFW_ACCUM_BLUE_BITS, 0);
			glfwWindowHint(GLFW_ACCUM_ALPHA_BITS, 0);
			glfwWindowHint(GLFW_SAMPLES, 0);
			
			glfwWindowHint(GLFW_DEPTH_BITS, newFormat.get(DEPTH_BITS));
			glfwWindowHint(GLFW_STENCIL_BITS, newFormat.get(STENCIL_BITS));
			
			VideoMode videoModeDirect = newFormat.get(VIDEO_MODE);
			if (videoModeDirect instanceof VideoModeDesktop) {
				VideoModeDesktop videoMode = (VideoModeDesktop) videoModeDirect;
				boolean hasTransparency = videoMode.hasTransparency();
				
				//window
				glfwWindowHint(GLFW_DECORATED, toGLFWBoolean(!newFormat.get(BORDERLESS)));
				glfwWindowHint(GLFW_RESIZABLE, toGLFWBoolean(newFormat.get(RESIZEABLE)));
				glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, toGLFWBoolean(hasTransparency));
				
				//fbo
				glfwWindowHint(GLFW_RED_BITS, GLFW_DONT_CARE);
				glfwWindowHint(GLFW_GREEN_BITS, GLFW_DONT_CARE);
				glfwWindowHint(GLFW_BLUE_BITS, GLFW_DONT_CARE);
				glfwWindowHint(GLFW_ALPHA_BITS, hasTransparency ? GLFW_DONT_CARE : newFormat.get(ALPHA_BITS));
				glfwWindowHint(GLFW_REFRESH_RATE, GLFW_DONT_CARE);
				
				//createWindow
				storage.windowPointer = glfwCreateWindow(videoMode.width(), videoMode.height(), newFormat.get(TITLE), 0, context.storage.getWindowPointer());
			} else if (videoModeDirect instanceof GLFWVideoModeMonitor) {
				GLFWVideoModeMonitor videoMode = (GLFWVideoModeMonitor) videoModeDirect;
				
				//fbo
				glfwWindowHint(GLFW_RED_BITS, videoMode.bitsR());
				glfwWindowHint(GLFW_GREEN_BITS, videoMode.bitsG());
				glfwWindowHint(GLFW_BLUE_BITS, videoMode.bitsB());
				glfwWindowHint(GLFW_ALPHA_BITS, newFormat.get(ALPHA_BITS));
				glfwWindowHint(GLFW_REFRESH_RATE, videoMode.refreshRate());
				
				//createWindow
				storage.windowPointer = glfwCreateWindow(videoMode.width(), videoMode.height(), newFormat.get(TITLE), videoMode.getMonitor().pointer, context.storage.getWindowPointer());
			} else {
				throw new IllegalStateException("format[VIDEO_MODE] was unsupported type: " + videoModeDirect.getClass().getName());
			}
			glfwMakeContextCurrent(storage.getWindowPointer());
		}
	}
	
	@WindowThread
	private void deleteNativeWindow() {
		storage.deleteNativeWindow();
	}
	
	public static class Storage extends FreeableStorageImpl implements Executor {
		
		private long windowPointer;
		private ExecutorService exec = Executors.newSingleThreadExecutor(r -> new Thread(r, "GLFWWindowThread-" + WINDOW_THREAD_COUNTER.getAndIncrement()));
		
		public Storage(Object referent, FreeableStorage... parents) {
			super(referent, parents);
		}
		
		@Override
		protected void handleFree() {
			exec.execute(this::deleteNativeWindow);
			exec.shutdown();
			//noinspection ConstantConditions
			exec = null;
		}
		
		@WindowThread
		private void deleteNativeWindow() {
			if (windowPointer != 0) {
				glfwDestroyWindow(windowPointer);
				windowPointer = 0;
			}
		}
		
		public long getWindowPointer() throws FreedException {
			throwIfFreed();
			return windowPointer;
		}
		
		@Override
		public void execute(@NotNull Runnable command) {
			throwIfFreed();
			exec.execute(command);
		}
	}
	
	@Retention(RetentionPolicy.SOURCE)
	@Target(ElementType.METHOD)
	public @interface WindowThread {
	
	}
	
}
