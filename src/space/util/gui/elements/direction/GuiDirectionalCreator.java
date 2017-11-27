package space.util.gui.elements.direction;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.indexmap.IndexMap;

/**
 * when using this, the resulting element should go in the inverse direction of the parent (row - column - row - ...)
 */
@FunctionalInterface
public interface GuiDirectionalCreator<BASE extends GuiElement<BASE, ?>> extends GuiCreator<BASE> {
	
	/**
	 * creates a {@link GuiDirectional} with a Method {@link GuiDirectional#getIndexMap()} getting the {@link IndexMap} to fill
	 *
	 * @return a new {@link GuiDirectional} to fill a table within
	 */
	//FIXME: get this running...
	GuiDirectional<BASE, GuiDirectionalCreator<BASE>> create();
	
	interface GuiDirectional<BASE extends GuiElement<BASE, CREATOR>, CREATOR extends GuiDirectionalCreator<BASE>> extends GuiElement<BASE, CREATOR> {
		
		IndexMap<BASE> getIndexMap();
		
		default void add(BASE v) {
			getIndexMap().add(v);
		}
		
		default void put(int index, BASE v) {
			getIndexMap().put(index, v);
		}
		
		default void remove(int index) {
			getIndexMap().remove(index);
		}
	}
}
