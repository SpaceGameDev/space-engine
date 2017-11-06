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
	
	protected boolean isRowLike;
	
	public boolean isRowLike() {
		rebuild();
		return isRowLike;
	}
	
	protected void calcIsRowLike() {
		if (parent == null)
			isRowLike = true;
		if (parent instanceof MonofontDirectional)
			isRowLike = !((MonofontDirectional) parent).isRowLike();
		if (parent instanceof MonofontColumn)
			isRowLike = true;
		if (parent instanceof MonofontRow)
			isRowLike = false;
		isRowLike = true;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		calcIsRowLike();
		return style.makeTable(this, new IndexMultiMapFrom1DIndexMap<>(buildList(), false, isRowLike() ? 0 : 1));
	}
}
