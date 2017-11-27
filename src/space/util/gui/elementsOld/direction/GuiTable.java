package space.util.gui.elementsOld.direction;

import space.util.gui.GuiElement;
import space.util.indexmap.multi.IndexMultiMap;

public interface GuiTable<BASEELEMENT extends GuiElement<BASEELEMENT>> extends GuiElement<BASEELEMENT> {
	
	IndexMultiMap<GuiElement<?>> getTable();
}
