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
import space.util.logger.impl.BaseLogger;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;

public class GLFWTest {
	
	public static final double MULTIPLIER = 3 * 60 / (PI * 2);
	public static final double OFFSET0 = 0;
	public static final double OFFSET1 = MULTIPLIER / 3;
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
		
		//framework
		IWindowFramework<?> frame = new GLFWWindowFramework(logger);
		
		//window
		AttributeListModification attListMod = WindowFormat.ATTRIBUTE_LIST_CREATOR.createModify();
		IAttributeList attList = attListMod.createNewList();
		IWindow window = frame.create(attList);
		
		for (int i = 0; i < 5 * 60; i++) {
			
			GL.createCapabilities();
			
			glColor3f((float) sin(i * MULTIPLIER + OFFSET0), (float) sin(i * MULTIPLIER + OFFSET1), (float) sin(i * MULTIPLIER + OFFSET2));
			glBegin(GL_TRIANGLES);
			glVertex2f(0.0f, 0.0f);
			glVertex2f(0.0f, 0.0f);
			glVertex2f(0.0f, 0.0f);
			glEnd();
			
			window.swapBuffers();
			window.pollEvents();
			Thread.sleep(1000 / 60);
		}
		
		window.free();
		frame.free();
	}
}
