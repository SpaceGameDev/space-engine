package space.util.gui.monofont.tableCreator;

import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.tableCreator.multi.MonofontTableCreator;
import space.util.indexmap.IndexMap;
import space.util.indexmap.multi.IndexMultiMapFrom1DIndexMap;
import space.util.string.CharSequence2D;

public class MonofontColumnFromTable implements MonofontColumnCreator {
	
	public MonofontTableCreator tableCreator;
	public int depth;
	
	public MonofontColumnFromTable(MonofontTableCreator tableCreator) {
		this(tableCreator, 0);
	}
	
	public MonofontColumnFromTable(MonofontTableCreator tableCreator, int depth) {
		this.tableCreator = tableCreator;
		this.depth = depth;
	}
	
	public static MonofontColumnFromTable createHorizontal(MonofontTableCreator tableCreator) {
		return new MonofontColumnFromTable(tableCreator, 0);
	}
	
	public static MonofontColumnFromTable createVertical(MonofontTableCreator tableCreator) {
		return new MonofontColumnFromTable(tableCreator, 1);
	}
	
	@Override
	public CharSequence2D makeTable(String name, MonofontGuiElement guiElement, ColumnDirection direction, IndexMap<CharSequence2D> valueTable) {
		return tableCreator.makeTable(name, guiElement, new IndexMultiMapFrom1DIndexMap<>(valueTable, false, depth));
	}
}
