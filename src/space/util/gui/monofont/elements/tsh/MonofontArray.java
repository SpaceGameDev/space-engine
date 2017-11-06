package space.util.gui.monofont.elements.tsh;

import space.util.gui.elements.tsh.GuiArray;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.elements.direction.MonofontElementList;
import space.util.gui.monofont.tableCreator.MonofontTableCreatorArray;
import space.util.indexmap.multi.IndexMultiMapFrom1DIndexMap;
import space.util.string.CharSequence2D;

public class MonofontArray extends MonofontElementList implements GuiArray<MonofontGuiElement> {
	
	public MonofontArray() {
		super(MonofontTableCreatorArray.DEFAULT);
	}
	
	@Override
	public CharSequence2D rebuild0() {
		return style.makeTable(this, new IndexMultiMapFrom1DIndexMap<>(buildList(), false, 1));
	}
}
