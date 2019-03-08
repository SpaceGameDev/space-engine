package space.engine.window.glfw;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.exceptions.FreedException;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.key.attribute.AbstractAttributeList;
import space.engine.key.attribute.AttributeList;
import space.engine.sync.future.Future;
import space.engine.window.Window;
import space.engine.window.WindowContext;
import space.engine.window.exception.WindowUnsupportedApiTypeException;
import space.engine.window.glfw.GLFWWindow.Storage;
import space.engine.window.glfw.GLFWWindow.WindowThread;

import static org.lwjgl.glfw.GLFW.*;
import static space.engine.sync.Tasks.runnable;
import static space.engine.window.WindowContext.FreeableWithStorage;
import static space.engine.window.glfw.GLFWUtil.*;

public class GLFWContext implements WindowContext, FreeableWithStorage {
	
	public final @NotNull GLFWWindowFramework framework;
	private final @NotNull Storage storage;
	
	public static Future<GLFWContext> create(@NotNull GLFWWindowFramework framework, @NotNull AttributeList<WindowContext> format, FreeableStorage... parents) {
		GLFWContext context = new GLFWContext(framework, parents);
		return runnable(context.storage, () -> context.initializeNativeWindow(format)).submit().toFuture(() -> context);
	}
	
	private GLFWContext(@NotNull GLFWWindowFramework framework, FreeableStorage... parents) {
		this.framework = framework;
		this.storage = new Storage(this, parents);
	}
	
	@Override
	public void execute(@NotNull Runnable command) {
		storage.execute(command);
	}
	
	/**
	 * return value always valid pointer
	 */
	public long getWindowPointer() throws FreedException {
		return storage.getWindowPointer();
	}
	
	@Override
	public @NotNull FreeableStorage getStorage() {
		return storage;
	}
	
	@Override
	public @NotNull Future<GLFWWindow> createWindow(@NotNull AttributeList<Window> format) {
		return GLFWWindow.create(this, format, storage);
	}
	
	//windowThread
	@WindowThread
	protected void initializeNativeWindow(AbstractAttributeList<WindowContext> newFormat) {
		synchronized (GLFWInstance.GLFW_SYNC) {
			//additional window settings
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
			glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_FALSE);
			
			//gl api settings
			@NotNull Object apiType = newFormat.get(API_TYPE);
			// noinspection StatementWithEmptyBody
			if (apiType == null) {
				//no context via window api
			} else if (apiType instanceof OpenGLApiType) {
				OpenGLApiType apiTypeGL = (OpenGLApiType) apiType;
				glfwWindowHint(GLFW_CLIENT_API, covertGLApiTypeToGLFWApi(apiTypeGL));
				switch (apiTypeGL) {
					case GL:
						glfwWindowHint(GLFW_OPENGL_PROFILE, GLFWUtil.covertGLProfileToGLFWProfile(newFormat.get(GL_PROFILE)));
						glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, toGLFWBoolean(newFormat.get(GL_FORWARD_COMPATIBLE)));
					case GL_ES:
						glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, newFormat.get(GL_VERSION_MAJOR));
						glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, newFormat.get(GL_VERSION_MINOR));
						break;
				}
			} else {
				throw new WindowUnsupportedApiTypeException(apiType);
			}
			
			//create
			storage.windowPointer = glfwCreateWindow(1, 1, "hidden_context_window", 0L, 0L);
		}
	}
}
