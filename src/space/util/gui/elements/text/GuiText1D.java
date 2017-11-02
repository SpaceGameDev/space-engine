package space.util.gui.elements.text;

import space.util.gui.GuiElement;

public interface GuiText1D<BASEELEMENT extends GuiElement<BASEELEMENT>> extends GuiElement<BASEELEMENT> {
	
	GuiText1D<BASEELEMENT> setCharSequence(CharSequence squ);
}
