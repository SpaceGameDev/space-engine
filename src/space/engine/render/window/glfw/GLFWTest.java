package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFW;
import space.engine.side.Side;
import space.util.buffer.alloc.DefaultBufferAllocator;
import space.util.buffer.string.DefaultStringConverter;
import space.util.logger.impl.BaseLogger;

public class GLFWTest {
	
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.util.NoChecks", "true");
		Side.getSide().put(Side.BUFFER_STRING_CONVERTER, new DefaultStringConverter(new DefaultBufferAllocator()));
		
		BaseLogger logger = new BaseLogger();
		BaseLogger.defaultHandler(logger);
		BaseLogger.defaultPrinter(logger);
		
		GLFWWindowFramework frame = new GLFWWindowFramework(logger);
		GLFW.glfwSwapInterval(-1);
	}
}
