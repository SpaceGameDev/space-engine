package space.util.gui.elements.direction;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.indexmap.IndexMap;
import space.util.indexmap.multi.IndexMultiMap;

public interface GuiTableCreator<BASE extends GuiElement<BASE, ?>> extends GuiCreator<BASE> {
	
	/**
	 * creates a {@link GuiTable} with a Method {@link GuiTable#getIndexMultiMap()} getting the {@link IndexMap} to fill
	 *
	 * @return a new {@link GuiTable} to fill a table within
	 */
	BASE create();
	
	interface GuiTable<BASE extends GuiElement<BASE, CREATOR>, CREATOR extends GuiTableCreator<BASE>> extends GuiElement<BASE, CREATOR> {
		
		IndexMultiMap<BASE> getIndexMultiMap();
		
		default void add(BASE v) {
			getIndexMultiMap().add(v);
		}
		
		default void add(int[] pos, BASE v) {
			getIndexMultiMap().add(pos, v);
		}
		
		default void put(int[] pos, BASE v) {
			getIndexMultiMap().put(pos, v);
		}
		
		default void remove(int[] pos) {
			getIndexMultiMap().remove(pos);
		}
	}
}
