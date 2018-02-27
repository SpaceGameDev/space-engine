package space.engine.render.window.glfw;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;
import space.engine.render.window.IMonitor;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWMonitor implements IMonitor {
	
	public long monitor;
	public String name;
	public int physicalWidth;
	public int physicalHeight;
	public int posX;
	public int posY;
	public IVideoMode currentVideoMode;
	public IVideoMode[] availableVideoModes;
	
	public GLFWMonitor(long monitor) {
		this.monitor = monitor;
		int[] x = new int[1];
		int[] y = new int[1];
		
		//name
		name = glfwGetMonitorName(monitor);
		
		//physicalSize
		glfwGetMonitorPhysicalSize(monitor, x, y);
		physicalWidth = x[0];
		physicalHeight = y[0];
		
		//pos
		glfwGetMonitorPos(monitor, x, y);
		posX = x[0];
		posY = y[0];
		
		//videoMode
		currentVideoMode = new GLFWVideoMode(glfwGetVideoMode(monitor));
		Buffer modes = glfwGetVideoModes(monitor);
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
	
	public static class GLFWVideoMode implements IVideoMode {
		
		public GLFWVidMode mode;
		
		public GLFWVideoMode(GLFWVidMode mode) {
			this.mode = mode;
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
