package space.util.gui.elements.tsh;

import space.util.gui.GuiElement;

public interface GuiVariable<BASEELEMENT extends GuiElement<BASEELEMENT>> extends GuiElement<BASEELEMENT> {
	
	GuiVariable<BASEELEMENT> setVariableName(CharSequence name);
	
	GuiVariable<BASEELEMENT> setVariableValue(BASEELEMENT value);
}
