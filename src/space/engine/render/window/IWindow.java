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
import space.util.baseobject.additional.Freeable;
import space.util.key.IKey;
import space.util.key.attribute.AttributeListCreator;

public interface IWindow extends Freeable {

//	void makeContextCurrent();
//
//	void swapBuffers();
//
//	void pollEvents();
	
	//AttributeList
	AttributeListCreator<IContext> CREATOR = new AttributeListCreator<>();
	
	//main window settings
	IKey<Integer> POSX = CREATOR.generateKey();
	IKey<Integer> POSY = CREATOR.generateKey();
	IKey<WindowMode> WINDOW_MODE = CREATOR.generateKey(WindowMode.WINDOWED);
	IKey<IVideoMode<?>> VIDEO_MODE = CREATOR.generateKey(IMonitor.createVideoModeWindowed(800, 600));
	
	//additional window settings
	IKey<String> TITLE = CREATOR.generateKey("Untitled Window");
	IKey<Boolean> VISIBLE = CREATOR.generateKey(Boolean.TRUE);
	IKey<Boolean> RESIZEABLE = CREATOR.generateKey(Boolean.FALSE);
	IKey<Boolean> DOUBLEBUFFER = CREATOR.generateKey(Boolean.TRUE);
	
	//fbo
	//RGB are defined with VIDEO_MODE
	IKey<Integer> FBO_A = CREATOR.generateKey(0);
	IKey<Integer> FBO_DEPTH = CREATOR.generateKey(0);
	IKey<Integer> FBO_STENCIL = CREATOR.generateKey(0);
	
	//callbacks
	IKey<KeyboardCharCallback> CHAR_CALLBACK = CREATOR.generateKey();
	IKey<KeyboardKeyCallback> KEY_CALLBACK = CREATOR.generateKey();
	IKey<MouseClickCallback> MOUSE_CLICK_CALLBACK = CREATOR.generateKey();
	IKey<MousePositionCallback> MOUSE_POSITION_CALLBACK = CREATOR.generateKey();
	IKey<MouseScrollCallback> MOUSE_SCROLL_CALLBACK = CREATOR.generateKey();
	IKey<WindowCloseRequestedCallback> WINDOW_CLOSE_REQUESTED_CALLBACK = CREATOR.generateKey();
	IKey<WindowFBOResizeCallback> WINDOW_FBO_RESIZE_CALLBACK = CREATOR.generateKey();
	IKey<WindowFocusCallback> WINDOW_FOCUS_CALLBACK = CREATOR.generateKey();
	IKey<WindowPositionCallback> WINDOW_POSITION_CALLBACK = CREATOR.generateKey();
	IKey<WindowResizeCallback> WINDOW_RESIZE_CALLBACK = CREATOR.generateKey();
	
	enum WindowMode {
		
		WINDOWED,
		FULLSCREEN,
		BORDERLESS
		
	}
}
