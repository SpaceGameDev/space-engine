package space.engine.render.window;

import space.util.baseobject.additional.Freeable;
import space.util.logger.Logger;

public interface IWindowFramework<W extends IWindow> extends Freeable {
	
	void init(Logger logger);
	
	void newThread();
	
	W create(WindowFormat format);
	
	void destroy();
	
	@Override
	default void free() {
		destroy();
	}
}
