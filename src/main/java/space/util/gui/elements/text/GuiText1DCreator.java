package space.util.gui.elements.text;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;

@FunctionalInterface
public interface GuiText1DCreator extends GuiCreator {
	
	/**
	 * creates a simple 1D Text
	 *
	 * @param text the text
	 * @return a new {@link GuiElement} of set text
	 */
	GuiText1D create(String text);
	
	interface GuiText1D extends GuiElement {
	
	}
}
