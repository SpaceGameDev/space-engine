package space.util.gui.monofont.elements.direction;

import space.util.gui.elements.direction.GuiRowCreator;
import space.util.gui.elements.direction.GuiRowCreator.GuiRow;
import space.util.gui.monofont.IMonofontWithTableCreator;
import space.util.gui.monofont.MonofontIncluding;
import space.util.gui.monofont.tableCreator.multi.IMonofontTableCreator;
import space.util.indexmap.multi.IndexMultiMapFrom1DIndexMap;
import space.util.string.CharSequence2D;

public class MonofontRow extends MonofontElementList implements GuiRow, IMonofontWithTableCreator {
	
	static {
		MonofontIncluding.toIncludeList.add(MonofontRow.class);
	}
	
	public static final GuiRowCreator CREATOR = MonofontRow::new;
	
	public IMonofontTableCreator style;
	
	@Override
	public GuiRowCreator getCreator() {
		return CREATOR;
	}
	
	@Override
	public void setMonofontTableCreator(IMonofontTableCreator style) {
		this.style = style;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable("", this, new IndexMultiMapFrom1DIndexMap<>(buildList(), false, 1));
	}
}
