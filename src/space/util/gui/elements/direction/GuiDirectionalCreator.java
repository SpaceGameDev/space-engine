package space.util.gui.elements.direction;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;

/**
 * when using this, the resulting element should go in the inverse direction of the parent (row - column - row - ...)
 */
@FunctionalInterface
public interface GuiDirectionalCreator extends GuiCreator {
	
	/**
	 * creates a {@link GuiDirectional}.
	 * Use the Methods {@link GuiDirectional#add(GuiElement)}, {@link GuiDirectional#put(int, GuiElement)}
	 * and {@link GuiDirectional#remove(int)} to config it like a {@link space.util.indexmap.IndexMap}.
	 *
	 * @return a new {@link GuiDirectional} to fill a table within
	 */
	GuiDirectional create();
	
	interface GuiDirectional extends GuiElement {
		
		void add(GuiElement v);
		
		GuiElement put(int index, GuiElement v);
		
		GuiElement remove(int index);
		
		void remove(GuiElement v);
	}
}
