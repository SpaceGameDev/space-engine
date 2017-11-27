package space.util.gui.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.gui.exception.IllegalGuiElementException;

public interface GuiVariableCreator<BASE extends GuiElement<BASE, ?>> extends GuiCreator<BASE> {
	
	/**
	 * creates a visual variable simmilar to this:
	 * <code>name + ": " + value</code>
	 *
	 * @param name  the name of the field
	 * @param value the value of the field, as a {@link GuiElement}
	 * @return a new {@link GuiElement} of set text
	 * @throws IllegalGuiElementException if a supplied {@link GuiElement} is illegal (eg. wrong type)
	 */
	GuiVariable create(String name, GuiElement value) throws IllegalGuiElementException;
	
	interface GuiVariable<ELEMENT extends GuiElement<ELEMENT, CREATOR>, CREATOR extends GuiVariableCreator<ELEMENT>> extends GuiElement<ELEMENT, CREATOR> {
	
	}
}
