package space.engine.render.window;

import space.engine.render.window.IMonitor.IVideoMode;
import space.engine.render.window.callback.KeyboardCharCallback;
import space.engine.render.window.callback.KeyboardKeyCallback;
import space.engine.render.window.callback.MouseClickCallback;
import space.engine.render.window.callback.MousePositionCallback;
import space.engine.render.window.callback.MouseScrollCallback;
import space.engine.render.window.callback.WindowCloseRequestedCallback;
import space.engine.render.window.callback.WindowFBOResizeCallback;
import space.engine.render.window.callback.WindowFocusCallback;
import space.engine.render.window.callback.WindowPositionCallback;
import space.engine.render.window.callback.WindowResizeCallback;
import space.util.key.IKey;
import space.util.key.attribute.AttributeListCreator;

import static java.lang.Boolean.*;
import static space.engine.render.window.WindowFormat.GLApiType.NONE;
import static space.engine.render.window.WindowFormat.GLProfile.PROFILE_ANY;
import static space.engine.render.window.WindowFormat.WindowMode.WINDOWED;

@Deprecated
@SuppressWarnings("unused")
public class WindowFormat {
	
	public static final AttributeListCreator ATT_CREATOR = new AttributeListCreator();
	
	//main window settings
	public static final IKey<Integer> POSX = ATT_CREATOR.generateKey();
	public static final IKey<Integer> POSY = ATT_CREATOR.generateKey();
	public static final IKey<WindowMode> WINDOW_MODE = ATT_CREATOR.generateKey(WINDOWED);
	public static final IKey<IVideoMode<?>> VIDEO_MODE = ATT_CREATOR.generateKey(IMonitor.createVideoModeWindowed(800, 600));
	
	//additional window settings
	public static final IKey<String> TITLE = ATT_CREATOR.generateKey("Untitled Window");
	public static final IKey<Boolean> VISIBLE = ATT_CREATOR.generateKey(TRUE);
	public static final IKey<Boolean> RESIZEABLE = ATT_CREATOR.generateKey(FALSE);
	public static final IKey<Boolean> DOUBLEBUFFER = ATT_CREATOR.generateKey(TRUE);
	
	//gl api settings
	public static final IKey<GLApiType> GL_API_TYPE = ATT_CREATOR.generateKey(NONE);
	public static final IKey<GLProfile> GL_PROFILE = ATT_CREATOR.generateKey(PROFILE_ANY);
	public static final IKey<Integer> GL_VERSION_MAJOR = ATT_CREATOR.generateKey(2);
	public static final IKey<Integer> GL_VERSION_MINOR = ATT_CREATOR.generateKey(1);
	public static final IKey<Boolean> GL_FORWARD_COMPATIBLE = ATT_CREATOR.generateKey(FALSE);
	public static final IKey<IWindow> GL_CONTEXT_SHARE = ATT_CREATOR.generateKey();
	
	//fbo
	//RGB are defined with VIDEO_MODE
	public static final IKey<Integer> FBO_A = ATT_CREATOR.generateKey(0);
	public static final IKey<Integer> FBO_DEPTH = ATT_CREATOR.generateKey(0);
	public static final IKey<Integer> FBO_STENCIL = ATT_CREATOR.generateKey(0);
	
	//callbacks
	public static final IKey<KeyboardCharCallback> CHAR_CALLBACK = ATT_CREATOR.generateKey();
	public static final IKey<KeyboardKeyCallback> KEY_CALLBACK = ATT_CREATOR.generateKey();
	public static final IKey<MouseClickCallback> MOUSE_CLICK_CALLBACK = ATT_CREATOR.generateKey();
	public static final IKey<MousePositionCallback> MOUSE_POSITION_CALLBACK = ATT_CREATOR.generateKey();
	public static final IKey<MouseScrollCallback> MOUSE_SCROLL_CALLBACK = ATT_CREATOR.generateKey();
	public static final IKey<WindowCloseRequestedCallback> WINDOW_CLOSE_REQUESTED_CALLBACK = ATT_CREATOR.generateKey();
	public static final IKey<WindowFBOResizeCallback> WINDOW_FBO_RESIZE_CALLBACK = ATT_CREATOR.generateKey();
	public static final IKey<WindowFocusCallback> WINDOW_FOCUS_CALLBACK = ATT_CREATOR.generateKey();
	public static final IKey<WindowPositionCallback> WINDOW_POSITION_CALLBACK = ATT_CREATOR.generateKey();
	public static final IKey<WindowResizeCallback> WINDOW_RESIZE_CALLBACK = ATT_CREATOR.generateKey();
	
	public enum WindowMode {
		
		WINDOWED,
		FULLSCREEN,
		BORDERLESS
		
	}
	
	public enum GLApiType {
		
		GL,
		GL_ES,
		NONE
		
	}
	
	public enum GLProfile {
		
		PROFILE_ANY,
		PROFILE_CORE,
		PROFILE_COMPAT
		
	}
}
