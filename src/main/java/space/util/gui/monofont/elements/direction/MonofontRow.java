package space.util.gui.monofont.elements.direction;

import space.util.gui.elements.direction.GuiRowCreator;
import space.util.gui.elements.direction.GuiRowCreator.GuiRow;
import space.util.gui.monofont.MonofontIncluding;
import space.util.gui.monofont.tableCreator.MonofontColumnCreator;
import space.util.gui.monofont.tableCreator.MonofontColumnCreator.ColumnDirection;
import space.util.gui.monofont.tableCreator.MonofontWithColumnCreator;
import space.util.string.CharSequence2D;

public class MonofontRow extends MonofontElementList implements GuiRow, MonofontWithColumnCreator {
	
	static {
		MonofontIncluding.toIncludeList.add(MonofontRow.class);
	}
	
	public static final GuiRowCreator CREATOR = MonofontRow::new;
	
	public MonofontColumnCreator style;
	
	@Override
	public GuiRowCreator getCreator() {
		return CREATOR;
	}
	
	@Override
	public void setMonofontColumnCreator(MonofontColumnCreator style) {
		this.style = style;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable("", this, ColumnDirection.VERTICAL, buildList());
	}
}
