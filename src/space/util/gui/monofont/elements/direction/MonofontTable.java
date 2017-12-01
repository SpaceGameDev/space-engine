package space.util.gui.monofont.elements.direction;

import space.util.gui.GuiElement;
import space.util.gui.elements.direction.GuiTableCreator;
import space.util.gui.elements.direction.GuiTableCreator.GuiTable;
import space.util.gui.exception.IllegalGuiElementException;
import space.util.gui.monofont.IMonofontWithTableCreator;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.MonofontGuiElementCaching;
import space.util.gui.monofont.MonofontIncluding;
import space.util.gui.monofont.tableCreator.IMonofontTableCreator;
import space.util.gui.monofont.tableCreator.MonofontTableCreatorDefault;
import space.util.indexmap.multi.IndexMultiMap;
import space.util.indexmap.multi.IndexMultiMap.IndexMultiMapEntry;
import space.util.indexmap.multi.IndexMultiMap2D;
import space.util.string.CharSequence2D;

public class MonofontTable extends MonofontGuiElementCaching implements GuiTable, IMonofontWithTableCreator {
	
	static {
		MonofontIncluding.toIncludeList.add(MonofontTable.class);
	}
	
	public static final GuiTableCreator CREATOR = MonofontTable::new;
	
	public IMonofontTableCreator style = MonofontTableCreatorDefault.STYLE_DEFAULT;
	public IndexMultiMap<MonofontGuiElement> table = new IndexMultiMap2D<>();
	
	@Override
	public void setMonofontTableCreator(IMonofontTableCreator style) {
		this.style = style;
	}
	
	@Override
	public GuiTableCreator getCreator() {
		return CREATOR;
	}
	
	//access
	@Override
	public void add(GuiElement gui) {
		if (!(gui instanceof MonofontGuiElement))
			throw new IllegalGuiElementException(gui);
		MonofontGuiElement v = (MonofontGuiElement) gui;
		v.setParent(this);
		table.add(v);
	}
	
	@Override
	public void add(int[] pos, GuiElement gui) {
		if (!(gui instanceof MonofontGuiElement))
			throw new IllegalGuiElementException(gui);
		MonofontGuiElement v = (MonofontGuiElement) gui;
		v.setParent(this);
		table.add(pos, v);
	}
	
	public GuiElement put(int[] pos, GuiElement gui) {
		if (!(gui instanceof MonofontGuiElement))
			throw new IllegalGuiElementException(gui);
		MonofontGuiElement v = (MonofontGuiElement) gui;
		v.setParent(this);
		MonofontGuiElement old = table.put(pos, v);
		if (old != null)
			old.setParent(null);
		return old;
	}
	
	public GuiElement remove(int[] pos) {
		MonofontGuiElement old = table.remove(pos);
		if (old != null)
			old.setParent(null);
		return old;
	}
	
	//rebuild
	@Override
	public CharSequence2D rebuild0() {
		IndexMultiMap<CharSequence2D> charTable = new IndexMultiMap2D<>();
		for (IndexMultiMapEntry<MonofontGuiElement> entry : table.tableIterator()) {
			MonofontGuiElement value = entry.getValue();
			charTable.put(entry.getIndex(), value != null ? value.build() : null);
		}
		return style.makeTable(this, charTable);
	}
}
