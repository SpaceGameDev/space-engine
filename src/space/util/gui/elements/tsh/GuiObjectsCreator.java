package space.util.gui.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public interface GuiObjectsCreator extends GuiCreator {
	
	/**
	 * creates a new {@link GuiObjects} Instance
	 *
	 * @param obj the {@link Object} to create an Instance for
	 * @return the new {@link GuiModifierCreator.GuiModifier}
	 */
	GuiObjects create(Object obj, ToStringHelper helper);
	
	interface GuiObjects extends GuiElement {
		
		ToStringHelperObjectsInstance<GuiObjects> getTSH();
	}
}
