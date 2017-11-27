package space.util.gui.elementsOld.tsh;

import space.util.gui.GuiElement;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;

public interface GuiObjects<BASEELEMENT extends GuiElement<BASEELEMENT>> {
	
	void setTSHObjectsInstance(AbstractToStringHelperObjectsInstance instance);
}
