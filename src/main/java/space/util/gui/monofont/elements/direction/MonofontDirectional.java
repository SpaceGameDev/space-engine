package space.util.gui.monofont.elements.direction;

import space.util.gui.elements.direction.GuiDirectionalCreator;
import space.util.gui.elements.text.GuiText1DCreator;
import space.util.gui.monofont.MonofontIncluding;
import space.util.gui.monofont.elements.text.MonofontText1D;
import space.util.gui.monofont.tableCreator.MonofontColumnCreator;
import space.util.gui.monofont.tableCreator.MonofontColumnCreator.ColumnDirection;
import space.util.gui.monofont.tableCreator.MonofontWithColumnCreator;
import space.util.string.CharSequence2D;

public class MonofontDirectional extends MonofontElementList implements MonofontWithColumnCreator {
	
	static {
		MonofontIncluding.toIncludeList.add(MonofontDirectional.class);
	}
	
	public static final GuiText1DCreator CREATOR = MonofontText1D::new;
	
	public MonofontColumnCreator style;
	protected ColumnDirection directionCache;
	
	//direction
	public ColumnDirection direction() {
		rebuild();
		return directionCache;
	}
	
	protected ColumnDirection calcDirection() {
		if (parent == null)
			return ColumnDirection.VERTICAL;
		if (parent instanceof MonofontDirectional)
			return ((MonofontDirectional) parent).direction().opposite();
		if (parent instanceof MonofontColumn)
			return ColumnDirection.VERTICAL;
		if (parent instanceof MonofontRow)
			return ColumnDirection.HORIZONTAL;
		return ColumnDirection.VERTICAL;
	}
	
	@Override
	public GuiDirectionalCreator getCreator() {
		return MonofontDirectional::new;
	}
	
	@Override
	public void setMonofontColumnCreator(MonofontColumnCreator style) {
		this.style = style;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		directionCache = calcDirection();
		return style.makeTable("", this, directionCache, buildList());
	}
}
