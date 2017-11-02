package space.util.gui.monofont.elements.direction;

import space.util.gui.elements.direction.GuiDirectional;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.tableCreator.MonofontTableCreatorIncludingTable;
import space.util.indexmap.multi.IndexMultiMapFrom1DIndexMap;
import space.util.string.CharSequence2D;

public class MonofontDirectional extends MonofontElementList implements GuiDirectional<MonofontGuiElement> {
	
	static {
		MonofontTableCreatorIncludingTable.toIncludeList.add(MonofontDirectional.class);
	}
	
	//I'd like to cache this value, but either it could break some stuff or the overhead isn't really worth it
	@SuppressWarnings({"SimplifiableIfStatement", "RedundantIfStatement"})
	public boolean isRowLike() {
		if (parent == null)
			return true;
		if (parent instanceof MonofontDirectional)
			return !((MonofontDirectional) parent).isRowLike();
		if (parent instanceof MonofontColumn)
			return true;
		if (parent instanceof MonofontRow)
			return false;
		return true;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable(this, new IndexMultiMapFrom1DIndexMap<>(buildList(), false, isRowLike() ? 0 : 1));
	}
}
