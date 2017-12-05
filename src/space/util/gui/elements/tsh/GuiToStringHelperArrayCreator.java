package space.util.gui.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.indexmap.IndexMap;

@FunctionalInterface
public interface GuiToStringHelperArrayCreator extends GuiCreator {
	
	/**
	 * creates a new {@link ToStringHelperArray}
	 *
	 * @param type    the type of array
	 * @param element an {@link IndexMap} containing all elements
	 * @return a new {@link ToStringHelperArray}
	 */
	ToStringHelperArray create(Class<?> type, IndexMap<? extends GuiElement> element);
	
	interface ToStringHelperArray extends GuiElement {
		
	}
}
