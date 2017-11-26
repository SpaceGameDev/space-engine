package space.util.gui.elements.tsh;

import space.util.gui.GuiElement;

public interface GuiModifier<BASEELEMENT extends GuiElement<BASEELEMENT>> extends GuiElement<BASEELEMENT> {
	
	GuiModifier<BASEELEMENT> setModifier(String name);
	
	GuiModifier<BASEELEMENT> setVariableValue(BASEELEMENT value);
}
