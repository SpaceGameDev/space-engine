package space.util.gui.monofont.elements.direction;

import space.util.gui.elements.direction.GuiColumnCreator;
import space.util.gui.elements.direction.GuiColumnCreator.GuiColumn;
import space.util.gui.monofont.MonofontIncluding;
import space.util.gui.monofont.tableCreator.MonofontColumnCreator;
import space.util.gui.monofont.tableCreator.MonofontColumnCreator.ColumnDirection;
import space.util.gui.monofont.tableCreator.MonofontWithColumnCreator;
import space.util.string.CharSequence2D;

public class MonofontColumn extends MonofontElementList implements GuiColumn, MonofontWithColumnCreator {
	
	static {
		MonofontIncluding.toIncludeList.add(MonofontColumn.class);
	}
	
	public static final GuiColumnCreator CREATOR = MonofontColumn::new;
	
	public MonofontColumnCreator style;
	
	@Override
	public GuiColumnCreator getCreator() {
		return CREATOR;
	}
	
	@Override
	public void setMonofontColumnCreator(MonofontColumnCreator style) {
		this.style = style;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable("", this, ColumnDirection.HORIZONTAL, buildList());
	}
}
