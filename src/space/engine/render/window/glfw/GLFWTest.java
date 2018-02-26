package space.engine.render.window.glfw;

import org.lwjgl.opengl.GL;
import space.engine.render.window.IWindow;
import space.engine.render.window.IWindowFramework;
import space.engine.render.window.WindowFormat;
import space.engine.side.Side;
import space.util.buffer.alloc.DefaultBufferAllocator;
import space.util.buffer.string.DefaultStringConverter;
import space.util.keygen.attribute.AttributeListCreator.AttributeListModification;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListModification;
import space.util.logger.BaseLogger;
import space.util.logger.LogLevel;
import space.util.freeableStorage.FreeableStorageCleaner;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;
import static space.engine.render.window.WindowFormat.*;

public class GLFWTest {
	
	public static boolean CRASH = false;
	public static int SECONDS = 5;
	
	public static final double MULTIPLIER = (2 * PI) / (3 * 60);
	public static final double OFFSET0 = 0;
	public static final double OFFSET1 = (2 * PI) / 3;
	public static final double OFFSET2 = OFFSET1 * 2;
	
	public static void main(String[] args) throws Exception {
		//attributes
		System.setProperty("org.lwjgl.util.NoChecks", "true");
		IAttributeListModification mod = Side.ATTRIBUTE_LIST_CREATOR.createModify();
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
		IWindowFramework<?> windowfw = new GLFWWindowFramework();
		
		//window
		AttributeListModification attListMod = WindowFormat.ATTRIBUTE_LIST_CREATOR.createModify();
		attListMod.put(WIDTH, 1920);
		attListMod.put(HEIGHT, 1080);
		attListMod.put(TITLE, "GLFWTest Window");
		attListMod.put(GL_API_TYPE, GLApiType.GL);
		IAttributeList attList = attListMod.createNewList();
		IWindow window = windowfw.create(attList);
		
		if (CRASH)
			throw new RuntimeException("Crash!");
		
		window.makeContextCurrent();
		GL.createCapabilities();
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
		
		window.destroy();
		windowfw.free();
		logger.log(LogLevel.INFO, "Exit!");
	}
}
