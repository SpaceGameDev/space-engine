package space.util.gui.tsh.elements;

import space.util.gui.GuiCreator;
import space.util.string.toStringHelper.ToStringHelper;

@FunctionalInterface
public interface GuiToStringHelperApiCreator<T> extends GuiCreator {
	
	ToStringHelper<T> create();
}
