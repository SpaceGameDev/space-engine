package space.util.gui.elementsOld.direction;

import space.util.gui.GuiElement;
import space.util.indexmap.IndexMap;

/**
 * when using this, the resulting element should go in the inverse direction of the parent (row - column)
 */
public interface GuiDirectional<BASEELEMENT extends GuiElement<BASEELEMENT>> extends GuiElement<BASEELEMENT> {
	
	IndexMap<BASEELEMENT> getList();
	
	default void add(BASEELEMENT v) {
		getList().add(v);
	}
	
	default void remove(BASEELEMENT v) {
		getList().add(v);
	}
}
