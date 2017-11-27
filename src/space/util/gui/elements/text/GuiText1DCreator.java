package space.util.gui.elements.text;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.elements.text.GuiText1DCreator.GuiText1D;

@FunctionalInterface
public interface GuiText1DCreator<ELEMENT extends GuiText1D> extends GuiCreator<ELEMENT> {
	
	ELEMENT create(String text);
	
	interface GuiText1D<ELEMENT extends GuiText1D<ELEMENT, CREATOR>, CREATOR extends GuiText1DCreator<ELEMENT>> extends GuiElement<ELEMENT, CREATOR> {
	
	}
}
