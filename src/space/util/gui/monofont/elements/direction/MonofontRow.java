package space.util.gui.monofont.elements.direction;

import space.util.gui.elements.direction.GuiRow;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.tableCreator.MonofontTableCreatorIncludingTable;
import space.util.indexmap.multi.IndexMultiMapFrom1DIndexMap;
import space.util.string.CharSequence2D;

public class MonofontRow extends MonofontElementList implements GuiRow<MonofontGuiElement> {
	
	static {
		MonofontTableCreatorIncludingTable.toIncludeList.add(MonofontRow.class);
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable(this, new IndexMultiMapFrom1DIndexMap<>(buildList(), false, 1));
	}
}
