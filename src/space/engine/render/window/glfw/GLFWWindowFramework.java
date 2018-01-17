package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFWErrorCallbackI;
import space.engine.render.window.IWindowFramework;
import space.engine.render.window.glfwOld.GLFWException;
import space.engine.side.Side;
import space.util.buffer.buffers.BufferImpl;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;
import space.util.logger.LogLevel;
import space.util.logger.Logger;
import space.util.string.builder.CharBufferBuilder1D;
import space.util.unsafe.UnsafeInstance;
import sun.misc.Unsafe;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWWindowFramework implements IWindowFramework<GLFWWindow> {
	
	GLFWErrorCallback errorCallback;
	
	public GLFWWindowFramework() {
	}
	
	public GLFWWindowFramework(Logger logger) {
		init(logger);
	}
	
	public void init(Logger logger) {
		glfwSetErrorCallback(errorCallback = new GLFWErrorCallback(logger.subLogger("GLFW")));
		if (!glfwInit())
			throw new GLFWException("glfwInit failed!");
	}
	
	@Override
	public GLFWWindow create(IAttributeList format) {
		return null;
	}
	
	@Override
	public void free() {
	
	}
	
	public static class GLFWErrorCallback implements GLFWErrorCallbackI {
		
		public Logger logger;
		
		public GLFWErrorCallback(Logger logger) {
			this.logger = logger;
		}
		
		@Override
		public void invoke(int error, long description) {
			Unsafe UNSAFE = UnsafeInstance.getUnsafe();
			
			System.out.println(Integer.toHexString(error));
			for (long i = description; ; i++) {
				byte b = UNSAFE.getByte(i);
				if (b == 0)
					break;
				System.out.println(Integer.toHexString(Byte.toUnsignedInt(b)) + " - " + (char) b);
			}
			System.out.println("0 - eof");
			
			
			//@formatter:off
			logger.log(LogLevel.ERROR, new CharBufferBuilder1D<>()
					.append(Integer.toHexString(error))
					.append(": ")
					.append(Side.getSide().get(Side.BUFFER_STRING_CONVERTER).memUTF8String(new BufferImpl(description, Integer.MAX_VALUE)))
					.toString());
			//@formatter:on
		}
	}
}
