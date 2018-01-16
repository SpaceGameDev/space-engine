package space.engine.render.window;

import space.util.baseobject.additional.Freeable;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;
import space.util.logger.Logger;

public interface IWindowFramework<W extends IWindow> extends Freeable {
	
	void init(Logger logger);
	
	void newWindowInteractingThread();
	
	W create(IAttributeList format);
	
	@Override
	void free();
}
