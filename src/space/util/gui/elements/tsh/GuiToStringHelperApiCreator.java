package space.util.gui.elements.tsh;

import space.util.gui.GuiCreator;
import space.util.string.toStringHelper.ToStringHelper;

@FunctionalInterface
public interface GuiToStringHelperApiCreator<T> extends GuiCreator {
	
	ToStringHelper<T> create();
}
