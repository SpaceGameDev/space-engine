package space.util.gui.monofont.elements.direction;

import space.util.gui.elements.direction.GuiColumnCreator;
import space.util.gui.elements.direction.GuiColumnCreator.GuiColumn;
import space.util.gui.monofont.IMonofontWithTableCreator;
import space.util.gui.monofont.MonofontIncluding;
import space.util.gui.monofont.tableCreator.multi.IMonofontTableCreator;
import space.util.string.CharSequence2D;

public class MonofontColumn extends MonofontElementList implements GuiColumn, IMonofontWithTableCreator {
	
	static {
		MonofontIncluding.toIncludeList.add(MonofontColumn.class);
	}
	
	public static final GuiColumnCreator CREATOR = MonofontColumn::new;
	
	public IMonofontTableCreator style;
	
	@Override
	public GuiColumnCreator getCreator() {
		return CREATOR;
	}
	
	@Override
	public void setMonofontTableCreator(IMonofontTableCreator style) {
		this.style = style;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable("", this, buildList());
	}
}
