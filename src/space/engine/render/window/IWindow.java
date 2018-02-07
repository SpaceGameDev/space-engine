package space.engine.render.window;

import space.util.baseobject.additional.Freeable;

public interface IWindow extends Freeable {
	
	void makeContextCurrent();
	
	void swapBuffers();
	
	void pollEvents();
	
	@Override
	void free();
}
