package space.util.gui.elements.text;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.string.CharSequence2D;

public interface GuiText2DCreator<BASE extends GuiElement<BASE, ?>> extends GuiCreator<BASE> {
	
	/**
	 * creates a simple 2D Text
	 *
	 * @param text the text, in {@link CharSequence2D}
	 * @return a new {@link GuiElement} of set 2D text
	 */
	GuiText2D create(CharSequence2D text);
	
	interface GuiText2D<ELEMENT extends GuiElement<ELEMENT, CREATOR>, CREATOR extends GuiText2DCreator<ELEMENT>> extends GuiElement<ELEMENT, CREATOR> {
	
	}
}
