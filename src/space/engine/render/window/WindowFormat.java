package space.engine.render.window;

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
import space.util.gui.monofont.MonofontGuiApi;
import space.util.keygen.IKey;
import space.util.keygen.attribute.AttributeListCreator;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;
import space.util.string.toStringHelper.ToStringHelper;

import static java.lang.Boolean.*;
import static space.engine.render.window.WindowFormat.GLApiType.GL;
import static space.engine.render.window.WindowFormat.GLProfile.PROFILE_ANY;
import static space.engine.render.window.WindowFormat.WindowMode.WINDOWED;

@SuppressWarnings("unused")
public class WindowFormat {
	
	public static final AttributeListCreator ATTRIBUTE_LIST_CREATOR = new AttributeListCreator();
	
	//main window settings
	public static final IKey<Integer> WINDOW_WIDTH = ATTRIBUTE_LIST_CREATOR.generateKey(800); //done
	public static final IKey<Integer> WINDOW_HEIGHT = ATTRIBUTE_LIST_CREATOR.generateKey(600); //done
	public static final IKey<WindowMode> WINDOW_MODE = ATTRIBUTE_LIST_CREATOR.generateKey(WINDOWED); //done
	
	//additional window settings
	public static final IKey<Boolean> VISIBLE = ATTRIBUTE_LIST_CREATOR.generateKey(TRUE); //done
	public static final IKey<String> MONITOR = ATTRIBUTE_LIST_CREATOR.generateKey(); //done
	public static final IKey<String> TITLE = ATTRIBUTE_LIST_CREATOR.generateKey("Untitled Window"); //done
	public static final IKey<Boolean> RESIZEABLE = ATTRIBUTE_LIST_CREATOR.generateKey(FALSE); //done
	public static final IKey<Boolean> DOUBLEBUFFER = ATTRIBUTE_LIST_CREATOR.generateKey(TRUE); //done
	
	//gl window settings
	public static final IKey<GLApiType> GL_API_TYPE = ATTRIBUTE_LIST_CREATOR.generateKey(GL); //done
	public static final IKey<GLProfile> GL_PROFILE = ATTRIBUTE_LIST_CREATOR.generateKey(PROFILE_ANY); //done
	public static final IKey<Integer> GL_VERSION_MAJOR = ATTRIBUTE_LIST_CREATOR.generateKey(2); //done
	public static final IKey<Integer> GL_VERSION_MINOR = ATTRIBUTE_LIST_CREATOR.generateKey(1); //done
	public static final IKey<Boolean> GL_FORWARD_COMPATIBLE = ATTRIBUTE_LIST_CREATOR.generateKey(TRUE); //done
	public static final IKey<IWindow> GL_CONTEXT_SHARE = ATTRIBUTE_LIST_CREATOR.generateKey(); //done
	
	//fbo
	public static final IKey<Integer> FBO_R = ATTRIBUTE_LIST_CREATOR.generateKey(8);
	public static final IKey<Integer> FBO_G = ATTRIBUTE_LIST_CREATOR.generateKey(8);
	public static final IKey<Integer> FBO_B = ATTRIBUTE_LIST_CREATOR.generateKey(8);
	public static final IKey<Integer> FBO_A = ATTRIBUTE_LIST_CREATOR.generateKey(0);
	public static final IKey<Integer> FBO_DEPTH = ATTRIBUTE_LIST_CREATOR.generateKey(24);
	public static final IKey<Integer> FBO_STENCIL = ATTRIBUTE_LIST_CREATOR.generateKey(0);
	
	//callbacks
	public static final IKey<KeyboardCharCallback> CHAR_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<KeyboardKeyCallback> KEY_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<MouseClickCallback> MOUSE_CLICK_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<MousePositionCallback> MOUSE_POSITION_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<MouseScrollCallback> MOUSE_SCROLL_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<WindowCloseRequestedCallback> WINDOW_CLOSE_REQUESTED_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<WindowFBOResizeCallback> WINDOW_FBO_RESIZE_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<WindowFocusCallback> WINDOW_FOCUS_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<WindowPositionCallback> WINDOW_POSITION_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<WindowResizeCallback> WINDOW_RESIZE_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	
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
	
	public static void main(String[] args) {
		ToStringHelper.setDefault(MonofontGuiApi.TSH);
		
		IAttributeList windowFormat = ATTRIBUTE_LIST_CREATOR.create();
		windowFormat.put(WINDOW_WIDTH, 800);
		windowFormat.put(WINDOW_HEIGHT, 600);
		windowFormat.put(WINDOW_MODE, WINDOWED);
		
		windowFormat.put(FBO_R, 8);
		windowFormat.put(FBO_G, 8);
		windowFormat.put(FBO_B, 8);
		windowFormat.put(FBO_A, 0);
		windowFormat.put(FBO_DEPTH, 24);
		windowFormat.put(FBO_STENCIL, 0);
		
		System.out.println(windowFormat);
	}
}
