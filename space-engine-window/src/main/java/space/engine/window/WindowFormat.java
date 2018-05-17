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
import space.util.key.Key;
import space.util.key.attribute.AttributeListCreator;
import space.util.key.attribute.AttributeListCreatorImpl;

import static java.lang.Boolean.*;
import static space.engine.window.WindowFormat.GLApiType.NONE;
import static space.engine.window.WindowFormat.GLProfile.PROFILE_ANY;
import static space.engine.window.WindowFormat.WindowMode.WINDOWED;

@Deprecated
@SuppressWarnings("unused")
public class WindowFormat {
	
	public static final AttributeListCreator<WindowFormat> ATT_CREATOR = new AttributeListCreatorImpl<>();
	
	//main window settings
	public static final Key<Integer> POSX = ATT_CREATOR.generateKey();
	public static final Key<Integer> POSY = ATT_CREATOR.generateKey();
	public static final Key<WindowMode> WINDOW_MODE = ATT_CREATOR.generateKey(WINDOWED);
	public static final Key<IVideoMode<?>> VIDEO_MODE = ATT_CREATOR.generateKey(WindowMonitor.createVideoModeWindowed(800, 600));
	
	//additional window settings
	public static final Key<String> TITLE = ATT_CREATOR.generateKey("Untitled Window");
	public static final Key<Boolean> VISIBLE = ATT_CREATOR.generateKey(TRUE);
	public static final Key<Boolean> RESIZEABLE = ATT_CREATOR.generateKey(FALSE);
	public static final Key<Boolean> DOUBLEBUFFER = ATT_CREATOR.generateKey(TRUE);
	
	//gl api settings
	public static final Key<GLApiType> GL_API_TYPE = ATT_CREATOR.generateKey(NONE);
	public static final Key<GLProfile> GL_PROFILE = ATT_CREATOR.generateKey(PROFILE_ANY);
	public static final Key<Integer> GL_VERSION_MAJOR = ATT_CREATOR.generateKey(2);
	public static final Key<Integer> GL_VERSION_MINOR = ATT_CREATOR.generateKey(1);
	public static final Key<Boolean> GL_FORWARD_COMPATIBLE = ATT_CREATOR.generateKey(FALSE);
	public static final Key<Window> GL_CONTEXT_SHARE = ATT_CREATOR.generateKey();
	
	//fbo
	//RGB are defined with VIDEO_MODE
	public static final Key<Integer> FBO_A = ATT_CREATOR.generateKey(0);
	public static final Key<Integer> FBO_DEPTH = ATT_CREATOR.generateKey(0);
	public static final Key<Integer> FBO_STENCIL = ATT_CREATOR.generateKey(0);
	
	//callbacks
	public static final Key<KeyboardCharCallback> CHAR_CALLBACK = ATT_CREATOR.generateKey();
	public static final Key<KeyboardKeyCallback> KEY_CALLBACK = ATT_CREATOR.generateKey();
	public static final Key<MouseClickCallback> MOUSE_CLICK_CALLBACK = ATT_CREATOR.generateKey();
	public static final Key<MousePositionCallback> MOUSE_POSITION_CALLBACK = ATT_CREATOR.generateKey();
	public static final Key<MouseScrollCallback> MOUSE_SCROLL_CALLBACK = ATT_CREATOR.generateKey();
	public static final Key<WindowCloseRequestedCallback> WINDOW_CLOSE_REQUESTED_CALLBACK = ATT_CREATOR.generateKey();
	public static final Key<WindowFBOResizeCallback> WINDOW_FBO_RESIZE_CALLBACK = ATT_CREATOR.generateKey();
	public static final Key<WindowFocusCallback> WINDOW_FOCUS_CALLBACK = ATT_CREATOR.generateKey();
	public static final Key<WindowPositionCallback> WINDOW_POSITION_CALLBACK = ATT_CREATOR.generateKey();
	public static final Key<WindowResizeCallback> WINDOW_RESIZE_CALLBACK = ATT_CREATOR.generateKey();
	
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
