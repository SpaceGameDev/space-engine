package space.engine.render.window.glfw;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import space.engine.side.Side;
import space.util.buffer.alloc.DefaultBufferAllocator;
import space.util.buffer.string.DefaultStringConverter;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListModification;
import space.util.logger.impl.BaseLogger;

public class GLFWTest {
	
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.util.NoChecks", "true");
		
		IAttributeListModification mod = Side.ATTRIBUTE_LIST_CREATOR.createModify();
		mod.put(Side.BUFFER_STRING_CONVERTER, new DefaultStringConverter(new DefaultBufferAllocator()));
		Side.getSide().apply(mod);
		
		BaseLogger logger = new BaseLogger();
		BaseLogger.defaultHandler(logger);
		BaseLogger.defaultPrinter(logger);
		
		GLFWWindowFramework frame = new GLFWWindowFramework(logger);
		PointerBuffer monitors = GLFW.glfwGetMonitors();
		for (int i = 0; i < monitors.capacity(); i++) {
			long l = monitors.get(i);
			System.out.println(l + " - " + GLFW.glfwGetMonitorName(l));
		}
		
		//causes an error, to test GLFWErrorCallback
//		GLFW.glfwSwapInterval(-1);
	}
}
