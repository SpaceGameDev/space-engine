package space.util.gui.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public interface GuiObjectsCreator<BASE extends GuiElement<BASE, ?>> extends GuiCreator<BASE> {
	
	/**
	 * creates a new {@link GuiObjects} Instance
	 *
	 * @param obj the {@link Object} to create an Instance for
	 * @return the new {@link GuiModifierCreator.GuiModifier}
	 */
	GuiObjects create(Object obj);
	
	interface GuiObjects<ELEMENT extends GuiElement<ELEMENT, CREATOR>, CREATOR extends GuiObjectsCreator<ELEMENT>> extends GuiElement<ELEMENT, CREATOR>, ToStringHelperObjectsInstance<ELEMENT> {
	
	}
}
