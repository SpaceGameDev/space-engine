package space.engine.window.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import space.engine.Side;
import space.engine.buffer.direct.alloc.UnsafeAllocator;
import space.engine.buffer.string.DefaultStringConverter;
import space.engine.freeableStorage.FreeableStorageCleaner;
import space.engine.key.attribute.AttributeList;
import space.engine.key.attribute.AttributeListModify;
import space.engine.logger.BaseLogger;
import space.engine.logger.LogLevel;
import space.engine.sync.Tasks;
import space.engine.window.Window;
import space.engine.window.WindowContext;
import space.engine.window.WindowContext.OpenGLApiType;
import space.engine.window.WindowFramework;
import space.engine.window.extensions.VideoModeDesktopExtension;

import java.util.Arrays;
import java.util.function.Consumer;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;
import static space.engine.window.Window.*;
import static space.engine.window.WindowContext.API_TYPE;
import static space.engine.window.extensions.BorderlessExtension.BORDERLESS;
import static space.engine.window.extensions.VideoModeDesktopExtension.*;
import static space.engine.window.extensions.VideoModeExtension.HEIGHT;

public class GLFWTest {
	
	public static boolean CRASH = false;
	public static int SECONDS = 30;
	public static boolean FREE_WINDOW = false;
	public static ExampleDraw exampleDraw = ExampleDraw.ROTATING_CUBE;
	
	public static final double MULTIPLIER = (2 * PI) / 3;
	public static final double OFFSET0 = 0;
	public static final double OFFSET1 = (2 * PI) / 3;
	public static final double OFFSET2 = OFFSET1 * 2;
	
	public static void main(String[] args) throws Exception {
		System.setProperty("org.lwjgl.util.NoChecks", "true");
		
		//side
		AttributeListModify<Side> mod = Side.ATTRIBUTE_LIST_CREATOR.createModify();
		UnsafeAllocator alloc = new UnsafeAllocator();
		Side.initBufferAlloc(mod, alloc);
		Side.initBufferStringConverter(mod, new DefaultStringConverter(alloc));
		mod.apply();
		
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
		AttributeListModify<WindowContext> windowContextAttInitial = WindowContext.CREATOR.createModify();
		windowContextAttInitial.put(API_TYPE, OpenGLApiType.GL);
		WindowContext windowContext = windowfw.createContext(windowContextAttInitial.createNewAttributeList());
		GLFW.glfwMakeContextCurrent(0);
		
		//window
		AttributeListModify<Window> windowAttInitial = Window.CREATOR.createModify();
		windowAttInitial.put(VIDEO_MODE, VideoModeDesktopExtension.class);
		windowAttInitial.put(WIDTH, 1080);
		windowAttInitial.put(HEIGHT, 1080);
		windowAttInitial.put(BORDERLESS, Boolean.TRUE);
		windowAttInitial.put(TITLE, "GLFWTest Window");
		AttributeList<Window> windowAtt = windowAttInitial.createNewAttributeList();
		Window window = windowContext.createWindow(windowAtt).awaitGet();
		
		if (CRASH)
			throw new RuntimeException("Test Crash!");
		
		Tasks.runnable(window, () -> {
			GL.createCapabilities();
			
			int[] viewport = new int[4];
			glGetIntegerv(GL_VIEWPORT, viewport);
			System.out.println(Arrays.toString(viewport));
			System.out.println(glGetInteger(GL_RED_BITS) + "-" + glGetInteger(GL_GREEN_BITS) + "-" + glGetInteger(GL_BLUE_BITS) + "-" + glGetInteger(GL_ALPHA_BITS) + "-" + glGetInteger(GL_DEPTH_BITS) + "-" + glGetInteger(GL_STENCIL_BITS));
		}).submit().await();
		
		int[] w = new int[1];
		int[] h = new int[1];
		GLFW.glfwGetWindowSize(((GLFWWindow) window).storage.getWindowPointer(), w, h);
		System.out.println(w[0] + "x" + h[0]);
		
		for (int i = 0; i < SECONDS * 60; i++) {
			int i2 = i;
			Tasks.runnable(window, () -> {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				
				exampleDraw.run.accept(i2 / 60f);
				
				window.swapBuffers();
			}).submit().await();
			Thread.sleep(1000 / 60);
			
			//changing out window
			if (i % 600 == 299) {
				System.out.println("windowed");
				AttributeListModify<Window> modify = windowAtt.createModify();
				modify.put(HAS_TRANSPARENCY, false);
				modify.put(BORDERLESS, false);
				modify.apply().await();
			} else if (i % 600 == 599) {
				System.out.println("transparent");
				AttributeListModify<Window> modify = windowAtt.createModify();
				modify.put(HAS_TRANSPARENCY, true);
				modify.put(BORDERLESS, true);
				modify.apply().await();
			}
		}
		
		if (FREE_WINDOW) {
			window.free();
			windowfw.free();
		}
		logger.log(LogLevel.INFO, "Exit!");
	}
	
	public enum ExampleDraw {
		
		COLORED_TRIANGLE(f -> {
			glColor3f((float) sin(f * MULTIPLIER + OFFSET0), (float) sin(f * MULTIPLIER + OFFSET1), (float) sin(f * MULTIPLIER + OFFSET2));
			glBegin(GL_TRIANGLES);
			glVertex2f(0.0f, 0.5f);
			glVertex2f(0.5f, -0.5f);
			glVertex2f(-0.5f, -0.5f);
			glEnd();
		}),
		
		ROTATING_CUBE(f -> {
			glPushMatrix();
			glScalef(0.5f, 0.5f, 0.5f);
			glRotatef(f * 90f, 0, 1, 0);
			glRotatef(30, 1, 0, 0);
			
			float[] floats = {
					//@formatter:off
					//1
					-1, -1, 1,
					-1, 1, 1,
					-1, 1, -1,
					-1, -1, -1,
					
					//2
					1, -1, 1,
					1, 1, 1,
					1, 1, -1,
					1, -1, -1,
					
					//3
					1, -1, -1,
					1, -1, 1,
					-1, -1, 1,
					-1, -1, -1,
					
					//4
					1, 1, -1,
					1, 1, 1,
					-1, 1, 1,
					-1, 1, -1,
					
					//5
					-1, -1, -1,
					-1, 1, -1,
					1, 1, -1,
					1, -1, -1,
					
					//6
					-1, -1, 1,
					-1, 1, 1,
					1, 1, 1,
					1, -1, 1,
					//@formatter:on
			};
			
			glColor3f((float) sin(f * MULTIPLIER + OFFSET0), (float) sin(f * MULTIPLIER + OFFSET1), (float) sin(f * MULTIPLIER + OFFSET2));
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			glBegin(GL_QUADS);
			for (int i = 0; i < floats.length; i += 3)
				glVertex3f(floats[i], floats[i + 1], floats[i + 2]);
			glEnd();
			
			glColor3f(1, 1, 1);
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			glBegin(GL_QUADS);
			for (int i = 0; i < floats.length; i += 3)
				glVertex3f(floats[i], floats[i + 1], floats[i + 2]);
			glEnd();
			
			glPopMatrix();
		});
		
		public final Consumer<Float> run;
		
		ExampleDraw(Consumer<Float> run) {
			this.run = run;
		}
	}
}
