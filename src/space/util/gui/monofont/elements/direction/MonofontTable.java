package space.util.gui.monofont.elements.direction;

import space.util.gui.GuiElement;
import space.util.gui.elements.direction.GuiTable;
import space.util.gui.monofont.IMonofontWithStyle;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.MonofontGuiElementCaching;
import space.util.gui.monofont.tableCreator.IMonofontTableCreator;
import space.util.gui.monofont.tableCreator.MonofontTableCreatorDefault;
import space.util.gui.monofont.tableCreator.MonofontTableCreatorIncludingTable;
import space.util.indexmap.multi.IndexMultiMap;
import space.util.indexmap.multi.IndexMultiMap.IndexMultiMapEntry;
import space.util.indexmap.multi.IndexMultiMap2D;
import space.util.string.CharSequence2D;

public class MonofontTable extends MonofontGuiElementCaching implements GuiTable<MonofontGuiElement>, IMonofontWithStyle {
	
	static {
		MonofontTableCreatorIncludingTable.toIncludeList.add(MonofontTable.class);
	}
	
	public IMonofontTableCreator style = MonofontTableCreatorDefault.STYLE_DEFAULT;
	IndexMultiMap<GuiElement<?>> table = new IndexMultiMap2D<>();
	
	@Override
	public void setMonofontStyle(IMonofontTableCreator style) {
		this.style = style;
	}
	
	public void put(int[] pos, GuiElement<?> v) {
		if (!(v instanceof MonofontGuiElement))
			throw new IllegalArgumentException("GuiElement not of Monofont!");
		table.put(pos, v);
		((MonofontGuiElement) v).parent = this;
	}
	
	public boolean remove(int[] pos) {
		GuiElement<?> guiElement = table.remove(pos);
		if (guiElement instanceof MonofontGuiElement)
			((MonofontGuiElement) guiElement).parent = this;
		return guiElement != null;
	}
	
	@Override
	public IndexMultiMap<GuiElement<?>> getTable() {
		return table;
	}
	
	@Override
	public CharSequence2D rebuild0() {
		IndexMultiMap<CharSequence2D> charTable = new IndexMultiMap2D<>();
		for (IndexMultiMapEntry<GuiElement<?>> entry : table.tableIterator()) {
			int[] pos = entry.getIndex();
			GuiElement<?> value = entry.getValue();
			
			if (value == null) {
				charTable.put(pos, null);
				continue;
			}
			
			if (!(value instanceof MonofontGuiElement))
				throw new IllegalStateException("GuiElement not of Monofont found!");
			charTable.put(pos, ((MonofontGuiElement) value).build());
		}
		
		return style.makeTable(this, charTable);
	}
}
