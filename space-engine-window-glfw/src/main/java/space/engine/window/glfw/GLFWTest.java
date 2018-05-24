package space.engine.window.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import space.engine.Side;
import space.engine.window.Monitor;
import space.engine.window.Window;
import space.engine.window.WindowFramework;
import space.util.buffer.alloc.DefaultBufferAllocator;
import space.util.buffer.string.DefaultStringConverter;
import space.util.freeableStorage.FreeableStorageCleaner;
import space.util.key.attribute.AttributeListCreator.IAttributeList;
import space.util.key.attribute.AttributeListCreator.IAttributeListModification;
import space.util.logger.BaseLogger;
import space.util.logger.LogLevel;

import java.util.Arrays;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;
import static space.engine.window.Window.*;

public class GLFWTest {
	
	public static boolean CRASH = false;
	public static int SECONDS = 5;
	public static boolean FREE_WINDOW = false;
	
	public static final double MULTIPLIER = (2 * PI) / (3 * 60);
	public static final double OFFSET0 = 0;
	public static final double OFFSET1 = (2 * PI) / 3;
	public static final double OFFSET2 = OFFSET1 * 2;
	
	public static void main(String[] args) throws Exception {
		System.out.println();
		//attributes
		System.setProperty("org.lwjgl.util.NoChecks", "true");
		IAttributeListModification<Side> mod = Side.ATTRIBUTE_LIST_CREATOR.createModify();
		mod.put(Side.BUFFER_STRING_CONVERTER, new DefaultStringConverter(new DefaultBufferAllocator()));
		Side.getSide().apply(mod);
		
		//logger
		BaseLogger logger = new BaseLogger();
		BaseLogger.defaultHandler(logger);
		BaseLogger.defaultPrinter(logger);
		
		//cleaner
		FreeableStorageCleaner.setCleanupLogger(logger);
		FreeableStorageCleaner.startCleanupThread();
		
		//framework
		WindowFramework windowfw = new GLFWWindowFramework();
		
		//window
		IAttributeListModification<Window> attListMod = Window.CREATOR.createModify();
		attListMod.put(WINDOW_MODE, WindowMode.WINDOWED);
		attListMod.put(VIDEO_MODE, Monitor.createVideoModeWindowed(800, 600));
		
		attListMod.put(TITLE, "GLFWTest Window");
		attListMod.put(GL_API_TYPE, GLApiType.GL);
		IAttributeList<Window> attList = attListMod.createNewList();
		Window window = windowfw.createWindow(attList);
		
		if (CRASH)
			throw new RuntimeException("Crash!");
		
		window.makeContextCurrent();
		GL.createCapabilities();
		
		int[] viewport = new int[4];
		glGetIntegerv(GL_VIEWPORT, viewport);
		System.out.println(Arrays.toString(viewport));
		System.out.println(glGetInteger(GL_RED_BITS) + "-" + glGetInteger(GL_GREEN_BITS) + "-" + glGetInteger(GL_BLUE_BITS) + "-" + glGetInteger(GL_DEPTH_BITS) + "-" + glGetInteger(GL_STENCIL_BITS));
		
		int[] w = new int[1];
		int[] h = new int[1];
		GLFW.glfwGetWindowSize(((GLFWWindow) window).storage.getWindowPointer(), w, h);
		System.out.println(w[0] + "x" + h[0]);
		
		for (int i = 0; i < SECONDS * 60; i++) {
			glClear(GL_COLOR_BUFFER_BIT);
			
			glColor3f((float) sin(i * MULTIPLIER + OFFSET0), (float) sin(i * MULTIPLIER + OFFSET1), (float) sin(i * MULTIPLIER + OFFSET2));
			glBegin(GL_TRIANGLES);
			glVertex2f(0.0f, 0.5f);
			glVertex2f(0.5f, -0.5f);
			glVertex2f(-0.5f, -0.5f);
			glEnd();
			
			window.swapBuffers();
			window.pollEvents();
			Thread.sleep(1000 / 60);
		}
		
		if (FREE_WINDOW) {
			window.free();
			windowfw.free();
		}
		logger.log(LogLevel.INFO, "Exit!");
	}
}
