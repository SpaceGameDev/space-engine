package space.engine.window;

import org.jetbrains.annotations.NotNull;
import space.util.baseobject.Freeable;
import space.util.key.Key;
import space.util.key.attribute.AttributeListCreator;
import space.util.key.attribute.AttributeListCreator.AttributeList;
import space.util.key.attribute.AttributeListCreatorImpl;

/**
 * The {@link WindowContext} is the Conext you do all your drawing with.
 *
 * <b>If you are using OpenGL or OpenGL ES:</b><br>
 * The Thread you create the Context with is the Thread you render everything with.
 * If you have a finished FrameBuffer, call {@link Window#openGL_SwapFramebuffer(int)} or {@link Window#openGL_ES_SwapFramebuffer(int)} respectively.
 */
@SuppressWarnings("unused")
public interface WindowContext extends Freeable {
	
	@NotNull Window createWindow(@NotNull AttributeList<Window> format);
	
	//attributes
	AttributeListCreatorImpl<WindowContext> CREATOR = new AttributeListCreatorImpl<>();
	
	//api
	Key<Object> API_TYPE = CREATOR.generateKey(ApiType.NONE);
	
	//OpenGL / OpenGL ES
	Key<GLProfile> GL_PROFILE = CREATOR.generateKey(GLProfile.PROFILE_ANY);
	Key<Integer> GL_VERSION_MAJOR = CREATOR.generateKey(2);
	Key<Integer> GL_VERSION_MINOR = CREATOR.generateKey(1);
	Key<Boolean> GL_FORWARD_COMPATIBLE = CREATOR.generateKey(Boolean.TRUE);
	
	//enums
	enum ApiType {
		
		NONE,
		GL,
		GL_ES
		
	}
	
	enum GLProfile {
		
		PROFILE_ANY,
		PROFILE_CORE,
		PROFILE_COMPAT
		
	}
}
