package space.util.gui.monofont.elements.direction;

import space.util.gui.elements.direction.GuiDirectionalCreator;
import space.util.gui.elements.direction.GuiDirectionalCreator.GuiDirectional;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.monofont.IMonofontWithTableCreator;
import space.util.gui.monofont.MonofontIncluding;
import space.util.gui.monofont.elements.text.MonofontText1D;
import space.util.gui.monofont.tableCreator.multi.IMonofontTableCreator;
import space.util.indexmap.multi.IndexMultiMapFrom1DIndexMap;
import space.util.string.CharSequence2D;

public class MonofontDirectional extends MonofontElementList implements GuiDirectional, IMonofontWithTableCreator {
	
	static {
		MonofontIncluding.toIncludeList.add(MonofontDirectional.class);
	}
	
	public static final GuiText1DCreator CREATOR = MonofontText1D::new;
	
	public IMonofontTableCreator style;
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
	public GuiDirectionalCreator getCreator() {
		return MonofontDirectional::new;
	}
	
	@Override
	public void setMonofontTableCreator(IMonofontTableCreator style) {
		this.style = style;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		calcIsRowLike();
		return style.makeTable("", this, new IndexMultiMapFrom1DIndexMap<>(buildList(), false, isRowLike() ? 0 : 1));
	}
}
