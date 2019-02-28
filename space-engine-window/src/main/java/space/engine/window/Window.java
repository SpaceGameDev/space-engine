package space.engine.window;

import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Freeable;
import space.engine.key.Key;
import space.engine.key.attribute.AttributeListCreator;
import space.engine.sync.TaskCreator;
import space.engine.window.VideoMode.VideoModeDesktop;

import java.util.concurrent.Executor;

/**
 * A {@link Window} is a {@link Window} on the Desktop.
 * Use {@link Window#free()} to free and close the {@link Window}.
 * <b>Opened {@link Window Windows} cannot be altered. {@link Window#free()} the opened one and create a new {@link Window} with your {@link WindowContext}</b>
 */
public interface Window extends Freeable, Executor {
	
	AttributeListCreator<Window> CREATOR = new AttributeListCreator<>();
	
	Key<@NotNull VideoMode> VIDEO_MODE = CREATOR.createKey(VideoMode.createVideoModeDesktop(800, 600));
	/**
	 * ignored when <code>VIDEO_MODE == {@link space.engine.window.VideoMode.VideoModeDesktop} && {@link VideoModeDesktop#hasTransparency()}</code>
	 */
	Key<@NotNull Integer> ALPHA_BITS = CREATOR.createKey(0);
	Key<@NotNull Integer> DEPTH_BITS = CREATOR.createKey(0);
	Key<@NotNull Integer> STENCIL_BITS = CREATOR.createKey(0);
	
	//attributes
	Key<@NotNull String> TITLE = CREATOR.createKey("Untitled Window");
	Key<@NotNull Boolean> VISIBLE = CREATOR.createKey(Boolean.TRUE);
	Key<@NotNull Boolean> RESIZEABLE = CREATOR.createKey(Boolean.FALSE);
	Key<@NotNull Boolean> DOUBLE_BUFFER = CREATOR.createKey(Boolean.TRUE);
	Key<@NotNull Boolean> BORDERLESS = CREATOR.createKey(Boolean.FALSE);
	
	//methods
	void swapBuffers();
	
	//context implementation specific methods
	
	/**
	 * Swap the Framebuffer of a FBO from OpenGL.
	 *
	 * @param opengl_texture_id the FBO id from OpenGL
	 * @return a Task it tell you when swapping is finished
	 */
	TaskCreator openGL_SwapBuffer(int opengl_texture_id);
	
	/**
	 * Swap the Framebuffer of a FBO from OpenGL ES.
	 *
	 * @param opengl_es_texture_id the FBO id from OpenGL ES
	 * @return a Task it tell you when swapping is finished
	 */
	TaskCreator openGL_ES_SwapBuffer(int opengl_es_texture_id);
}
