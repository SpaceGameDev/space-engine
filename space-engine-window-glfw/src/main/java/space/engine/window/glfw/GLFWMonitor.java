package space.engine.window.glfw;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;
import space.engine.window.WindowMonitor;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWMonitor implements WindowMonitor {
	
	public long pointer;
	public String name;
	public int physicalWidth;
	public int physicalHeight;
	public int posX;
	public int posY;
	public IVideoMode currentVideoMode;
	public IVideoMode[] availableVideoModes;
	
	public GLFWMonitor(long pointer) {
		this.pointer = pointer;
		int[] x = new int[1];
		int[] y = new int[1];
		
		//name
		name = glfwGetMonitorName(pointer);
		
		//physicalSize
		glfwGetMonitorPhysicalSize(pointer, x, y);
		physicalWidth = x[0];
		physicalHeight = y[0];
		
		//pos
		glfwGetMonitorPos(pointer, x, y);
		posX = x[0];
		posY = y[0];
		
		//videoMode
		currentVideoMode = new GLFWVideoMode(glfwGetVideoMode(pointer));
		Buffer modes = glfwGetVideoModes(pointer);
		IVideoMode[] array = new IVideoMode[modes.remaining()];
		for (GLFWVidMode mode : modes)
			array[modes.position()] = new GLFWVideoMode(mode);
		availableVideoModes = array;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public int physicalWidth() {
		return physicalWidth;
	}
	
	@Override
	public int physicalHeight() {
		return physicalHeight;
	}
	
	@Override
	public int posX() {
		return posX;
	}
	
	@Override
	public int posY() {
		return posY;
	}
	
	@Override
	public IVideoMode getCurrentVideoMode() {
		return currentVideoMode;
	}
	
	@Override
	public IVideoMode[] getAvailableVideoModes() {
		return availableVideoModes;
	}
	
	public class GLFWVideoMode implements IVideoMode<GLFWMonitor> {
		
		public GLFWVidMode mode;
		
		public GLFWVideoMode(GLFWVidMode mode) {
			this.mode = mode;
		}
		
		@Override
		public GLFWMonitor getMonitor() {
			return GLFWMonitor.this;
		}
		
		@Override
		public int width() {
			return mode.width();
		}
		
		@Override
		public int height() {
			return mode.height();
		}
		
		@Override
		public int bitsR() {
			return mode.redBits();
		}
		
		@Override
		public int bitsG() {
			return mode.greenBits();
		}
		
		@Override
		public int bitsB() {
			return mode.blueBits();
		}
		
		@Override
		public int refreshRate() {
			return mode.refreshRate();
		}
	}
}
