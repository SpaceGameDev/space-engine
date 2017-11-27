package space.util.gui.elements.text;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.elements.text.GuiText2DCreator.GuiText2D;

public interface GuiText2DCreator<ELEMENT extends GuiText2D> extends GuiCreator<ELEMENT> {
	
	ELEMENT create(String text);
	
	interface GuiText2D<ELEMENT extends GuiText2D<ELEMENT, CREATOR>, CREATOR extends GuiText2DCreator<ELEMENT>> extends GuiElement<ELEMENT, CREATOR> {
	
	}
}
