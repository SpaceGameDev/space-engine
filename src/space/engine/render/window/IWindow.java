package space.engine.render.window;

import space.util.baseobject.additional.Freeable;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;

public interface IWindow extends Freeable {
	
	void update(IAttributeList format);
	
	void makeContextCurrent();
	
	void swapBuffers();
	
	void pollEvents();
	
	void destroy();
	
	@Override
	default void free() {
		destroy();
	}
}
