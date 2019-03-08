package space.engine.window.glfw;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.freeableStorage.FreeableStorageImpl;
import space.engine.key.attribute.AttributeList;
import space.engine.sync.future.Future;
import space.engine.window.Window;
import space.engine.window.WindowContext;
import space.engine.window.exception.WindowUnsupportedApiTypeException;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.window.WindowContext.FreeableWithStorage;
import static space.engine.window.glfw.GLFWUtil.*;

public class GLFWContext implements WindowContext, FreeableWithStorage {
	
	public final GLFWWindowFramework framework;
	protected Storage storage;
	
	public GLFWContext(GLFWWindowFramework framework, AttributeList<WindowContext> format, FreeableStorage... parents) {
		this.framework = framework;
		
		synchronized (GLFWInstance.GLFW_SYNC) {
			//additional window settings
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
			glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_FALSE);
			
			//gl api settings
			@NotNull Object apiType = format.get(API_TYPE);
			// noinspection StatementWithEmptyBody
			if (apiType == null) {
				//no context via window api
			} else if (apiType instanceof OpenGLApiType) {
				OpenGLApiType apiTypeGL = (OpenGLApiType) apiType;
				glfwWindowHint(GLFW_CLIENT_API, covertGLApiTypeToGLFWApi(apiTypeGL));
				switch (apiTypeGL) {
					case GL:
						glfwWindowHint(GLFW_OPENGL_PROFILE, covertGLProfileToGLFWProfile(format.get(GL_PROFILE)));
						glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, toGLFWBoolean(format.get(GL_FORWARD_COMPATIBLE)));
					case GL_ES:
						glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, format.get(GL_VERSION_MAJOR));
						glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, format.get(GL_VERSION_MINOR));
						break;
				}
			} else {
				throw new WindowUnsupportedApiTypeException(apiType);
			}
			
			//create
			this.storage = new Storage(this, glfwCreateWindow(1, 1, "always_hidden_never_shown", 0L, 0L), parents);
		}
	}
	
	@Override
	public @NotNull FreeableStorage getStorage() {
		return storage;
	}
	
	@Override
	public @NotNull Future<GLFWWindow> createWindow(@NotNull AttributeList<Window> format) {
		return GLFWWindow.create(this, format, storage);
	}
	
	public static class Storage extends FreeableStorageImpl {
		
		private long windowPointer;
		
		public Storage(Object referent, long windowPointer, FreeableStorage... getSubList) {
			super(referent, getSubList);
			this.windowPointer = windowPointer;
		}
		
		@Override
		protected void handleFree() {
			glfwDestroyWindow(windowPointer);
		}
		
		public long getWindowPointer() throws FreedException {
			throwIfFreed();
			return windowPointer;
		}
	}
}
