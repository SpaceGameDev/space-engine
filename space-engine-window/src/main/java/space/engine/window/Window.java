package space.engine.window;

import space.engine.window.WindowMonitor.IVideoMode;
import space.engine.window.callback.KeyboardCharCallback;
import space.engine.window.callback.KeyboardKeyCallback;
import space.engine.window.callback.MouseClickCallback;
import space.engine.window.callback.MousePositionCallback;
import space.engine.window.callback.MouseScrollCallback;
import space.engine.window.callback.WindowCloseRequestedCallback;
import space.engine.window.callback.WindowFBOResizeCallback;
import space.engine.window.callback.WindowFocusCallback;
import space.engine.window.callback.WindowPositionCallback;
import space.engine.window.callback.WindowResizeCallback;
import space.util.baseobject.Freeable;
import space.util.key.Key;
import space.util.key.attribute.AttributeListCreatorImpl;

import static java.lang.Boolean.FALSE;

public interface Window extends Freeable {
	
	void makeContextCurrent();
	
	void swapBuffers();
	
	void pollEvents();
	
	//AttributeList
	AttributeListCreatorImpl<Window> CREATOR = new AttributeListCreatorImpl<>();
	
	//main window settings
	Key<Integer> POSX = CREATOR.generateKey();
	Key<Integer> POSY = CREATOR.generateKey();
	Key<WindowMode> WINDOW_MODE = CREATOR.generateKey(WindowMode.WINDOWED);
	Key<IVideoMode<?>> VIDEO_MODE = CREATOR.generateKey(WindowMonitor.createVideoModeWindowed(800, 600));
	
	//additional window settings
	Key<String> TITLE = CREATOR.generateKey("Untitled Window");
	Key<Boolean> VISIBLE = CREATOR.generateKey(Boolean.TRUE);
	Key<Boolean> RESIZEABLE = CREATOR.generateKey(Boolean.FALSE);
	Key<Boolean> DOUBLEBUFFER = CREATOR.generateKey(Boolean.TRUE);
	
	//fbo
	//RGB are defined with VIDEO_MODE
	Key<Integer> FBO_A = CREATOR.generateKey(0);
	Key<Integer> FBO_DEPTH = CREATOR.generateKey(0);
	Key<Integer> FBO_STENCIL = CREATOR.generateKey(0);
	
	Key<GLApiType> GL_API_TYPE = CREATOR.generateKey(GLApiType.NONE);
	Key<GLProfile> GL_PROFILE = CREATOR.generateKey(GLProfile.PROFILE_ANY);
	Key<Integer> GL_VERSION_MAJOR = CREATOR.generateKey(2);
	Key<Integer> GL_VERSION_MINOR = CREATOR.generateKey(1);
	Key<Boolean> GL_FORWARD_COMPATIBLE = CREATOR.generateKey(FALSE);
	Key<Window> GL_CONTEXT_SHARE = CREATOR.generateKey();
	
	//callbacks
	Key<KeyboardCharCallback> CHAR_CALLBACK = CREATOR.generateKey();
	Key<KeyboardKeyCallback> KEY_CALLBACK = CREATOR.generateKey();
	Key<MouseClickCallback> MOUSE_CLICK_CALLBACK = CREATOR.generateKey();
	Key<MousePositionCallback> MOUSE_POSITION_CALLBACK = CREATOR.generateKey();
	Key<MouseScrollCallback> MOUSE_SCROLL_CALLBACK = CREATOR.generateKey();
	Key<WindowCloseRequestedCallback> WINDOW_CLOSE_REQUESTED_CALLBACK = CREATOR.generateKey();
	Key<WindowFBOResizeCallback> WINDOW_FBO_RESIZE_CALLBACK = CREATOR.generateKey();
	Key<WindowFocusCallback> WINDOW_FOCUS_CALLBACK = CREATOR.generateKey();
	Key<WindowPositionCallback> WINDOW_POSITION_CALLBACK = CREATOR.generateKey();
	Key<WindowResizeCallback> WINDOW_RESIZE_CALLBACK = CREATOR.generateKey();
	
	enum WindowMode {
		
		WINDOWED,
		FULLSCREEN,
		BORDERLESS
		
	}
	
	enum GLApiType {
		
		GL,
		GL_ES,
		NONE
		
	}
	
	enum GLProfile {
		
		PROFILE_ANY,
		PROFILE_CORE,
		PROFILE_COMPAT
		
	}
}
