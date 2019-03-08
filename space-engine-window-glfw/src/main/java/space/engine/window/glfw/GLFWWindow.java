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
import space.engine.window.Monitor;
import space.engine.window.Monitor.VideoMode;
import space.engine.window.Window;
import space.engine.window.extensions.VideoModeDesktopExtension;
import space.engine.window.extensions.VideoModeExtension;
import space.engine.window.extensions.VideoModeFullscreenExtension;

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
import static space.engine.window.extensions.BorderlessExtension.BORDERLESS;
import static space.engine.window.extensions.ResizeableExtension.*;
import static space.engine.window.extensions.VideoModeDesktopExtension.HAS_TRANSPARENCY;
import static space.engine.window.extensions.VideoModeExtension.*;
import static space.engine.window.glfw.GLFWUtil.toGLFWBoolean;

public class GLFWWindow implements Window, FreeableWithStorage {
	
	private static final AtomicInteger WINDOW_THREAD_COUNTER = new AtomicInteger();
	
	/**
	 * keep this reference to prevent freeing of WindowFramework
	 */
	public final @NotNull GLFWContext context;
	public final @NotNull AttributeList<Window> format;
	protected final Storage storage;
	
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
	protected void initializeNativeWindow(AbstractAttributeList<Window> newFormat) {
		int[] prevPosition = null;
		if (storage.windowPointer != 0) {
			int[] posx = new int[1];
			int[] posy = new int[1];
			glfwGetWindowPos(storage.windowPointer, posx, posy);
			prevPosition = new int[] {posx[0], posy[0]};
		}
		
		deleteNativeWindow();
		
		synchronized (GLFWInstance.GLFW_SYNC) {
			glfwDefaultWindowHints();
			
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			glfwWindowHint(GLFW_DOUBLEBUFFER, toGLFWBoolean(newFormat.get(DOUBLE_BUFFER)));
			glfwWindowHint(GLFW_DEPTH_BITS, newFormat.get(DEPTH_BITS));
			glfwWindowHint(GLFW_STENCIL_BITS, newFormat.get(STENCIL_BITS));
			
			@NotNull Class<? extends VideoModeExtension> videoMode = newFormat.get(VIDEO_MODE);
			if (videoMode == VideoModeDesktopExtension.class) {
				
				//fbo
				boolean hasTransparency = newFormat.get(HAS_TRANSPARENCY);
				glfwWindowHint(GLFW_ALPHA_BITS, hasTransparency ? GLFW_DONT_CARE : newFormat.get(ALPHA_BITS));
				glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, toGLFWBoolean(hasTransparency));
				
				//extension BorderlessExtension
				glfwWindowHint(GLFW_DECORATED, toGLFWBoolean(!newFormat.get(BORDERLESS)));
				//extension ResizeableExtension
				boolean resizable = newFormat.get(RESIZEABLE);
				glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
				
				//createWindow
				storage.windowPointer = glfwCreateWindow(newFormat.get(WIDTH), newFormat.get(HEIGHT), newFormat.get(TITLE), 0, context.storage.getWindowPointer());
				if (prevPosition != null)
					glfwSetWindowPos(storage.windowPointer, prevPosition[0], prevPosition[1]);
				
				//extension ResizeableExtension
				if (resizable)
					glfwSetWindowSizeLimits(storage.windowPointer, newFormat.get(MINX), newFormat.get(MINY), newFormat.get(MAXX), newFormat.get(MAXY));
			} else if (videoMode == VideoModeFullscreenExtension.class) {
				VideoMode videoModeFullscreen = newFormat.get(VideoModeFullscreenExtension.FULLSCREEN_VIDEO_MODE);
				if (videoModeFullscreen == null)
					videoModeFullscreen = context.framework.getPrimaryMonitor().getDefaultVideoMode();
				Monitor monitor = videoModeFullscreen.monitor();
				if (!(monitor instanceof GLFWMonitor))
					throw new IllegalArgumentException("Monitor not from GLFW");
				
				//fbo
				glfwWindowHint(GLFW_RED_BITS, videoModeFullscreen.bitsR());
				glfwWindowHint(GLFW_GREEN_BITS, videoModeFullscreen.bitsG());
				glfwWindowHint(GLFW_BLUE_BITS, videoModeFullscreen.bitsB());
				glfwWindowHint(GLFW_ALPHA_BITS, newFormat.get(ALPHA_BITS));
				glfwWindowHint(GLFW_REFRESH_RATE, videoModeFullscreen.refreshRate());
				
				//createWindow
				storage.windowPointer = glfwCreateWindow(videoModeFullscreen.width(),
														 videoModeFullscreen.height(),
														 newFormat.get(TITLE),
														 ((GLFWMonitor) monitor).pointer,
														 context.storage.getWindowPointer());
			} else {
				throw new IllegalStateException("format[VIDEO_MODE] was unsupported type: " + videoMode.getName());
			}
			glfwMakeContextCurrent(storage.getWindowPointer());
		}
		
		if (newFormat.get(VISIBLE)) {
			glfwShowWindow(storage.windowPointer);
		}
	}
	
	@WindowThread
	protected void deleteNativeWindow() {
		storage.deleteNativeWindow();
	}
	
	@WindowThread
	public long getWindowPointer() throws FreedException {
		return storage.getWindowPointer();
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
