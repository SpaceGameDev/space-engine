package space.util.gui.monofont.tableCreator;

import space.util.gui.monofont.MonofontGuiElement;
import space.util.indexmap.IndexMap;
import space.util.string.CharSequence2D;

@FunctionalInterface
public interface MonofontColumnCreator {
	
	/**
	 * creates a nice table String2D from a IndexMultiMap
	 *
	 * @param guiElement the {@link MonofontGuiElement} generating
	 * @param valueTable may contain null elements which should be treated like empty strings
	 * @return a nice table
	 */
	CharSequence2D makeTable(String name, MonofontGuiElement guiElement, IndexMap<CharSequence2D> valueTable);
}
