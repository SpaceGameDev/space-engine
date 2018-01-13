package space.engine.render.window;

import space.util.baseobject.additional.Freeable;

public interface IWindow extends Freeable {
	
	void makeContextCurrent();
	
	void swapBuffers();
	
	void pollEvents();
	
	void destroy();
	
	@Override
	default void free() {
		destroy();
	}
	
	void update(WindowFormat format);
}
