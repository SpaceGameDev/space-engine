package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFWErrorCallbackI;
import space.engine.render.window.exception.WindowException;
import space.engine.side.Side;
import space.util.buffer.direct.DirectBufferImpl;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWErrorCallback implements GLFWErrorCallbackI {
	
	public static final IndexMap<String> ERROR_MAP = new IndexMapArray<>();
	public static final int ERROR_OFFSET = 0x10000;
	
	static {
		addError(GLFW_NOT_INITIALIZED, "GLFW_NOT_INITIALIZED");
		addError(GLFW_NO_CURRENT_CONTEXT, "GLFW_NO_CURRENT_CONTEXT");
		addError(GLFW_INVALID_ENUM, "GLFW_INVALID_ENUM");
		addError(GLFW_INVALID_VALUE, "GLFW_INVALID_VALUE");
		addError(GLFW_OUT_OF_MEMORY, "GLFW_OUT_OF_MEMORY");
		addError(GLFW_API_UNAVAILABLE, "GLFW_API_UNAVAILABLE");
		addError(GLFW_VERSION_UNAVAILABLE, "GLFW_VERSION_UNAVAILABLE");
		addError(GLFW_PLATFORM_ERROR, "GLFW_PLATFORM_ERROR");
		addError(GLFW_FORMAT_UNAVAILABLE, "GLFW_FORMAT_UNAVAILABLE");
		addError(GLFW_NO_WINDOW_CONTEXT, "GLFW_NO_WINDOW_CONTEXT");
	}
	
	private static void addError(int id, String name) {
		ERROR_MAP.put(id - ERROR_OFFSET, name);
	}
	
	@Override
	public void invoke(int error, long description) {
		String errorName = ERROR_MAP.get(error - ERROR_OFFSET);
		String desc = Side.getSide().get(Side.BUFFER_STRING_CONVERTER).memUTF8String(new DirectBufferImpl(description, Integer.MAX_VALUE));
		throw new WindowException(error, errorName, desc);
	}
}
