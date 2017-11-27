package space.util.gui.elementsOld.text;

import space.util.gui.GuiElement;
import space.util.string.CharSequence2D;

public interface GuiText2D<BASEELEMENT extends GuiElement<BASEELEMENT>> extends GuiElement<BASEELEMENT> {
	
	GuiText2D<BASEELEMENT> setCharSequence(CharSequence2D squ);
}
