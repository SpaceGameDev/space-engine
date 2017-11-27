package space.util.gui.elementsOld.tsh;

import space.util.gui.GuiElement;

public interface GuiModifier<BASEELEMENT extends GuiElement<BASEELEMENT>> extends GuiElement<BASEELEMENT> {
	
	GuiModifier<BASEELEMENT> setModifier(String name);
	
	GuiModifier<BASEELEMENT> setVariableValue(BASEELEMENT value);
}
