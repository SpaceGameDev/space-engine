package space.engine.render.window;

import space.engine.render.window.callback.CharCallback;
import space.engine.render.window.callback.KeyCallback;
import space.engine.render.window.callback.MouseClickCallback;
import space.engine.render.window.callback.MousePositionCallback;
import space.engine.render.window.callback.ScrollCallback;
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

import java.util.Arrays;

public class WindowFormat {
	
	public static final AttributeListCreator ATTRIBUTE_LIST_CREATOR = new AttributeListCreator();
	
	//main window settings
	public static final IKey<Integer> WINDOW_WIDTH = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> WINDOW_HEIGHT = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<WindowMode> WINDOW_MODE = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	//additional window settings
	public static final IKey<Boolean> IS_VISIBLE = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> MONTIOR = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<String> TITLE = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Boolean> ALLOW_RESIZE = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Boolean> DOUBLE_BUFFER = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	//gl window settings
	public static final IKey<GLApiType> GL_API_TYPE = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> GL_VERSION_MAJOR = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> GL_VERSION_MINOR = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> GL_FORWARD_COMPATIBLE = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<IWindow> GL_CONTEXT_SHARE = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	//fbo
	public static final IKey<Integer> FBO_R = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> FBO_G = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> FBO_B = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> FBO_A = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> FBO_DEPTH = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<Integer> FBO_STENCIL = ATTRIBUTE_LIST_CREATOR.generateKey();
	
	//callbacks
	public static final IKey<CharCallback> CHAR_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<KeyCallback> KEY_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<MouseClickCallback> MOUSE_CLICK_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<MousePositionCallback> MOUSE_POSITION_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
	public static final IKey<ScrollCallback> MOUSE_SCROLL_CALLBACK = ATTRIBUTE_LIST_CREATOR.generateKey();
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
		GLES,
		NONE
		
	}
	
	public static void main(String[] args) {
		ToStringHelper.setDefault(MonofontGuiApi.TSH);
		
		IAttributeList windowFormat = ATTRIBUTE_LIST_CREATOR.create();
//		windowFormat.get(windowDimensions).set(1920, 1080, WindowMode.WINDOWED);
		windowFormat.put(WINDOW_WIDTH, 800);
		windowFormat.put(WINDOW_HEIGHT, 600);
		windowFormat.put(WINDOW_MODE, WindowMode.WINDOWED);
		System.out.println(windowFormat);
		System.out.println(windowFormat.getClass());
		System.out.println(Arrays.toString(windowFormat.getClass().getDeclaredConstructors()));
	}
}
