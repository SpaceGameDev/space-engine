package space.engine.render.window;

import space.util.baseobject.additional.Freeable;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeList;

public interface IWindowFramework<W extends IWindow> extends Freeable {
	
	W create(IAttributeList format);
	
	@Override
	void free();
}
