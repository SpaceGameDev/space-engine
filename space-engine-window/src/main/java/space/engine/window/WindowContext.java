package space.engine.window;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Freeable;
import space.engine.key.Key;
import space.engine.key.attribute.AttributeList;
import space.engine.key.attribute.AttributeListCreator;

/**
 * The {@link WindowContext} is the Conext you do all your drawing with.
 *
 * <b>If you are using OpenGL or OpenGL ES:</b><br>
 * The Thread you create the Context with is the Thread you render everything with.
 * If you have a finished FrameBuffer, call {\@link Window#openGL_SwapFramebuffer(int)} or {\@link Window#openGL_ES_SwapFramebuffer(int)} respectively.
 */
@SuppressWarnings("unused")
public interface WindowContext extends Freeable {
	
	@NotNull Window createWindow(@NotNull AttributeList<Window> format);
	
	//attributes
	AttributeListCreator<WindowContext> CREATOR = new AttributeListCreator<>();
	
	//api
	Key<Object> API_TYPE = CREATOR.createKey();
	
	//OpenGL / OpenGL ES
	Key<GLProfile> GL_PROFILE = CREATOR.createKey(GLProfile.PROFILE_ANY);
	Key<Integer> GL_VERSION_MAJOR = CREATOR.createKey(2);
	Key<Integer> GL_VERSION_MINOR = CREATOR.createKey(1);
	Key<Boolean> GL_FORWARD_COMPATIBLE = CREATOR.createKey(Boolean.FALSE);
	
	//enums
	enum OpenGLApiType {
		
		GL,
		GL_ES
		
	}
	
	enum GLProfile {
		
		PROFILE_ANY,
		PROFILE_CORE,
		PROFILE_COMPAT
		
	}
}
