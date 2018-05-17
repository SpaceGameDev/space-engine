package space.engine.window.glfw;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWUtil {
	
	public static int toGLFWBoolean(boolean b) {
		return b ? GLFW_TRUE : GLFW_FALSE;
	}
	
	public static boolean fromGLFWBoolean(int b) {
		return b == GLFW_TRUE;
	}
}
