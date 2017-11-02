package space.util.gui.elements.direction;

import space.util.gui.GuiElement;
import space.util.indexmap.IndexMap;

/**
 * when using this, the resulting element should go in the inverse direction of the parent (row - column)
 */
public interface GuiDirectional<BASEELEMENT extends GuiElement<BASEELEMENT>> extends GuiElement<BASEELEMENT> {
	
	IndexMap<GuiElement<?>> getList();
}
