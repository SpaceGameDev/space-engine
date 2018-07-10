package space.engine.window.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import space.engine.Side;
import space.engine.window.VideoMode;
import space.engine.window.Window;
import space.engine.window.WindowContext;
import space.engine.window.WindowContext.OpenGLApiType;
import space.engine.window.WindowFramework;
import space.util.buffer.direct.alloc.UnsafeAllocator;
import space.util.buffer.string.DefaultStringConverter;
import space.util.concurrent.task.impl.RunnableTask;
import space.util.freeableStorage.FreeableStorageCleaner;
import space.util.key.attribute.AttributeListCreator.AttributeList;
import space.util.key.attribute.AttributeListCreator.AttributeListModification;
import space.util.logger.BaseLogger;
import space.util.logger.LogLevel;

import java.util.Arrays;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;
import static space.engine.window.Window.*;
import static space.engine.window.WindowContext.API_TYPE;

public class GLFWTest {
	
	public static boolean CRASH = false;
	public static int SECONDS = 5;
	public static boolean FREE_WINDOW = false;
	
	public static final double MULTIPLIER = (2 * PI) / (3 * 60);
	public static final double OFFSET0 = 0;
	public static final double OFFSET1 = (2 * PI) / 3;
	public static final double OFFSET2 = OFFSET1 * 2;
	
	public static void main(String[] args) throws Exception {
		System.setProperty("org.lwjgl.util.NoChecks", "true");
		
		//attributes
		AttributeListModification<Side> mod = Side.ATTRIBUTE_LIST_CREATOR.createModify();
		
		//side alloc buffer
		UnsafeAllocator alloc = new UnsafeAllocator();
		Side.initBufferAlloc(mod, alloc);
		Side.initBufferStringConverter(mod, new DefaultStringConverter(alloc));
		
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
		
		//context
		AttributeListModification<WindowContext> windowContextAtt = WindowContext.CREATOR.createModify();
		windowContextAtt.put(API_TYPE, OpenGLApiType.GL);
		WindowContext windowContext = windowfw.createContext(windowContextAtt.createNewList());
		
		//window
		AttributeListModification<Window> windowAtt = Window.CREATOR.createModify();
		windowAtt.put(VIDEO_MODE, VideoMode.createVideoModeDesktop(1920, 1080));
		windowAtt.put(TITLE, "GLFWTest Window");
		AttributeList<Window> attList = windowAtt.createNewList();
		Window window = windowContext.createWindow(attList);
		
		if (CRASH)
			throw new RuntimeException("Test Crash!");
		
		RunnableTask setup = new RunnableTask(() -> {
			GL.createCapabilities();
			
			int[] viewport = new int[4];
			glGetIntegerv(GL_VIEWPORT, viewport);
			System.out.println(Arrays.toString(viewport));
			System.out.println(glGetInteger(GL_RED_BITS) + "-" + glGetInteger(GL_GREEN_BITS) + "-" + glGetInteger(GL_BLUE_BITS) + "-" + glGetInteger(GL_DEPTH_BITS) + "-" + glGetInteger(GL_STENCIL_BITS));
		});
		setup.submit(window);
		setup.awaitAndRethrow();
		
		int[] w = new int[1];
		int[] h = new int[1];
		GLFW.glfwGetWindowSize(((GLFWWindow) window).storage.getWindowPointer(), w, h);
		System.out.println(w[0] + "x" + h[0]);
		
		for (int i = 0; i < SECONDS * 60; i++) {
			int i2 = i;
			RunnableTask loopCmd = new RunnableTask(() -> {
				glClear(GL_COLOR_BUFFER_BIT);
				
				glColor3f((float) sin(i2 * MULTIPLIER + OFFSET0), (float) sin(i2 * MULTIPLIER + OFFSET1), (float) sin(i2 * MULTIPLIER + OFFSET2));
				glBegin(GL_TRIANGLES);
				glVertex2f(0.0f, 0.5f);
				glVertex2f(0.5f, -0.5f);
				glVertex2f(-0.5f, -0.5f);
				glEnd();
				
				window.swapBuffers();
			});
			loopCmd.submit(window);
			loopCmd.awaitAndRethrow();
			Thread.sleep(1000 / 60);
		}
		
		if (FREE_WINDOW) {
			window.free();
			windowfw.free();
		}
		logger.log(LogLevel.INFO, "Exit!");
	}
}
