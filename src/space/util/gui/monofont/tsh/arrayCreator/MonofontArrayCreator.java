package space.util.gui.monofont.tsh.arrayCreator;

import space.util.gui.monofont.MonofontGuiElement;
import space.util.indexmap.IndexMap;
import space.util.string.CharSequence2D;

@FunctionalInterface
public interface MonofontArrayCreator {
	
	CharSequence2D makeTable(Class<?> type, IndexMap<MonofontGuiElement> elements);
}
