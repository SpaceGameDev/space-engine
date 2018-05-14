package space.util.gui.tsh.elements;

import space.util.gui.GuiCreator;
import space.util.gui.GuiElement;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public interface GuiToStringHelperObjectsCreator extends GuiCreator {
	
	/**
	 * creates a new {@link ToStringHelperObjects} Instance
	 *
	 * @param obj the {@link Object} to create an Instance for
	 * @return the new {@link ToStringHelperObjects}
	 */
	ToStringHelperObjects create(Object obj, ToStringHelper<? extends GuiElement> helper);
	
	interface ToStringHelperObjects extends GuiElement, ToStringHelperObjectsInstance<GuiElement> {
	
	}
}
