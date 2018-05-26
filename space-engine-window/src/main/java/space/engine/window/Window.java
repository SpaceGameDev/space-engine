package space.engine.window;

import space.util.baseobject.Freeable;
import space.util.concurrent.task.Task;
import space.util.key.Key;
import space.util.key.attribute.AttributeListCreatorImpl;

/**
 * A {@link Window} is a {@link Window} on the Desktop ({@link WindowMode#WINDOWED} or {@link WindowMode#BORDERLESS}) or Monitor ({@link WindowMode#FULLSCREEN}).
 * Use {@link Window#free()} to free and close the {@link Window}.
 * <b>Opened {@link Window Windows} cannot be altered. {@link Window#free()} the opened one and create a new {@link Window} with your {@link WindowContext}</b>
 */
public interface Window extends Freeable {
	
	/**
	 * Swap the Framebuffer of a FBO from OpenGL.
	 *
	 * @param opengl_fbo_id the FBO id from OpenGL
	 * @return a Task it tell you when swapping is finished
	 */
	Task openGL_SwapFramebuffer(int opengl_fbo_id);
	
	/**
	 * Swap the Framebuffer of a FBO from OpenGL ES.
	 *
	 * @param opengl_es_fbo_id the FBO id from OpenGL ES
	 * @return a Task it tell you when swapping is finished
	 */
	Task openGL_ES_SwapFramebuffer(int opengl_es_fbo_id);
	
	//attributes
	AttributeListCreatorImpl<Window> CREATOR = new AttributeListCreatorImpl<>();
	
	//main window settings
	Key<Integer> POSX = CREATOR.generateKey();
	Key<Integer> POSY = CREATOR.generateKey();
	Key<VideoMode> VIDEO_MODE = CREATOR.generateKey(VideoMode.createVideoModeDesktop(800, 600));
	
	//additional window settings
	Key<String> TITLE = CREATOR.generateKey("Untitled Window");
	Key<Boolean> VISIBLE = CREATOR.generateKey(Boolean.TRUE);
	Key<Boolean> RESIZEABLE = CREATOR.generateKey(Boolean.FALSE);
	Key<Boolean> DOUBLEBUFFER = CREATOR.generateKey(Boolean.TRUE);
	
	enum WindowMode {
		
		WINDOWED,
		FULLSCREEN,
		BORDERLESS
		
	}
}
