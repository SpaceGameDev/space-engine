package space.engine.render.window;

import space.util.baseobject.additional.Freeable;
import space.util.key.IKey;
import space.util.key.attribute.AttributeListCreator;

@SuppressWarnings("unused")
public interface IContext extends Freeable {
	
	//AttributeList
	AttributeListCreator<IContext> CREATOR = new AttributeListCreator<>();
	IKey<ApiType> API_TYPE = CREATOR.generateKey(ApiType.NONE);
	
	//OpenGL / OpenGL ES
	IKey<GLProfile> GL_PROFILE = CREATOR.generateKey(GLProfile.PROFILE_ANY);
	IKey<Integer> GL_VERSION_MAJOR = CREATOR.generateKey(2);
	IKey<Integer> GL_VERSION_MINOR = CREATOR.generateKey(1);
	IKey<Boolean> GL_FORWARD_COMPATIBLE = CREATOR.generateKey(Boolean.TRUE);
	
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
