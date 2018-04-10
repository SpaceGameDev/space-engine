package space.engine.render.window;

import space.engine.render.window.WindowMonitor.IVideoMode;
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
import space.util.baseobject.Freeable;
import space.util.key.Key;
import space.util.key.attribute.AttributeListCreatorImpl;

public interface Window extends Freeable {

//	void makeContextCurrent();
//
//	void swapBuffers();
//
//	void pollEvents();
	
	//AttributeList
	AttributeListCreatorImpl<WindowContext> CREATOR = new AttributeListCreatorImpl<>();
	
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
}
