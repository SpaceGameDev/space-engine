package space.util.gui.elements.direction;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;

public interface GuiTableCreator extends GuiCreator {
	
	/**
	 * creates a {@link GuiTable}.
	 * Use the Methods {@link GuiTable#add(GuiElement)},  {@link GuiTable#add(int[], GuiElement)},
	 * {@link GuiTable#put(int[], GuiElement)} and {@link GuiTable#remove(int[])}
	 * to config it like a {@link space.util.indexmap.multi.IndexMultiMap}.
	 *
	 * @return a new {@link GuiTable} to fill a table within
	 */
	GuiTable create();
	
	interface GuiTable extends GuiElement {
		
		void add(GuiElement v);
		
		void add(int[] pos, GuiElement v);
		
		GuiElement put(int[] pos, GuiElement v);
		
		GuiElement remove(int[] pos);
	}
}
