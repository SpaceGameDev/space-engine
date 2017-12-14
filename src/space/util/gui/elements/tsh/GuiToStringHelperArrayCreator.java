package space.util.gui.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;

@FunctionalInterface
public interface GuiToStringHelperArrayCreator extends GuiCreator {
	
	/**
	 * creates a new {@link ToStringHelperArray}
	 *
	 * @param type the type of array
	 * @return a new {@link ToStringHelperArray}
	 */
	ToStringHelperArray create(Class<?> type);
	
	interface ToStringHelperArray extends GuiElement {
		
		GuiElement put(int index, GuiElement element);
	}
}
