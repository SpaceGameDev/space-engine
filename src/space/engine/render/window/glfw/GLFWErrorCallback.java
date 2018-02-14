package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFWErrorCallbackI;
import space.engine.side.Side;
import space.util.buffer.buffers.BufferImpl;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;
import space.util.logger.Logger;
import space.util.string.builder.CharBufferBuilder1D;

public class GLFWErrorCallback implements GLFWErrorCallbackI {
	
	public static final IndexMap<String> ERROR_MAP = new IndexMapArray<>();
	public static final int ERROR_OFFSET = 0x10000;
	public static final String ERROR_UNKNOWN = "UNKNOWN_ERROR_ID";
	
	static {
		ERROR_MAP.put(0x1, "GLFW_NOT_INITIALIZED");
		ERROR_MAP.put(0x2, "GLFW_NO_CURRENT_CONTEXT");
		ERROR_MAP.put(0x3, "GLFW_INVALID_ENUM");
		ERROR_MAP.put(0x4, "GLFW_INVALID_VALUE");
		ERROR_MAP.put(0x5, "GLFW_OUT_OF_MEMORY");
		ERROR_MAP.put(0x6, "GLFW_API_UNAVAILABLE");
		ERROR_MAP.put(0x7, "GLFW_VERSION_UNAVAILABLE");
		ERROR_MAP.put(0x8, "GLFW_PLATFORM_ERROR");
		ERROR_MAP.put(0x9, "GLFW_FORMAT_UNAVAILABLE");
		ERROR_MAP.put(0xA, "GLFW_NO_WINDOW_CONTEXT");
	}
	
	public Logger logger;
	
	public GLFWErrorCallback(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public void invoke(int error, long description) {
		String errorString = ERROR_MAP.get(error - ERROR_OFFSET);
		if (errorString == null)
			errorString = ERROR_UNKNOWN;
		
		//@formatter:off
//		logger.log(LogLevel.ERROR, new CharBufferBuilder1D<>()
//				.append(errorString).append("[0x").append(Integer.toHexString(error)).append("]: ")
//				.append(Side.getSide().get(Side.BUFFER_STRING_CONVERTER).memUTF8String(new BufferImpl(description, Integer.MAX_VALUE)))
//				.toString());
		throw new RuntimeException(new CharBufferBuilder1D<>()
				.append(errorString).append("[0x").append(Integer.toHexString(error)).append("]: ")
				.append(Side.getSide().get(Side.BUFFER_STRING_CONVERTER).memUTF8String(new BufferImpl(description, Integer.MAX_VALUE)))
				.toString());
		//@formatter:on
	}
}
