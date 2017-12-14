package space.util.gui.monofont.tableCreator.multi;

import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.tableCreator.MonofontColumnCreator;
import space.util.indexmap.IndexMap;
import space.util.indexmap.multi.IndexMultiMap;
import space.util.indexmap.multi.IndexMultiMapFrom1DIndexMap;
import space.util.string.CharSequence2D;

@FunctionalInterface
public interface IMonofontTableCreator extends MonofontColumnCreator {
	
	/**
	 * creates a nice table String2D from a IndexMultiMap
	 *
	 * @param guiElement the {@link MonofontGuiElement} generating
	 * @param valueTable may contain null elements which should be treated like empty strings
	 * @return a nice table
	 */
	default CharSequence2D makeTable(String name, MonofontGuiElement guiElement, IndexMap<CharSequence2D> valueTable) {
		return makeTable(name, guiElement, new IndexMultiMapFrom1DIndexMap<>(valueTable));
	}
	
	/**
	 * creates a nice table String2D from a IndexMultiMap
	 *
	 * @param guiElement the {@link MonofontGuiElement} generating
	 * @param valueTable may contain null elements which should be treated like empty strings
	 * @return a nice table
	 */
	CharSequence2D makeTable(String name, MonofontGuiElement guiElement, IndexMultiMap<CharSequence2D> valueTable);
}
