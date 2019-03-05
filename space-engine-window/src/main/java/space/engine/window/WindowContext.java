package space.engine.window;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Freeable;
import space.engine.key.attribute.AttributeKey;
import space.engine.key.attribute.AttributeList;
import space.engine.key.attribute.AttributeListCreator;
import space.engine.sync.future.Future;

/**
 * The {@link WindowContext} is the Conext you do all your drawing with.
 *
 * <b>If you are using OpenGL or OpenGL ES:</b><br>
 * The Thread you create the Context with is the Thread you render everything with.
 * If you have a finished FrameBuffer, call {\@link Window#openGL_SwapFramebuffer(int)} or {\@link Window#openGL_ES_SwapFramebuffer(int)} respectively.
 */
@SuppressWarnings("unused")
public interface WindowContext extends Freeable {
	
	Future<? extends Window> createWindow(@NotNull AttributeList<Window> format);
	
	//attributes
	AttributeListCreator<WindowContext> CREATOR = new AttributeListCreator<>();
	
	//api
	AttributeKey<Object> API_TYPE = CREATOR.createKeyNormal();
	
	//OpenGL / OpenGL ES
	AttributeKey<GLProfile> GL_PROFILE = CREATOR.createKeyWithDefault(GLProfile.PROFILE_ANY);
	AttributeKey<Integer> GL_VERSION_MAJOR = CREATOR.createKeyWithDefault(2);
	AttributeKey<Integer> GL_VERSION_MINOR = CREATOR.createKeyWithDefault(1);
	AttributeKey<Boolean> GL_FORWARD_COMPATIBLE = CREATOR.createKeyWithDefault(false);
	
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
