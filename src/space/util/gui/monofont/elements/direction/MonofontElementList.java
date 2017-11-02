package space.util.gui.monofont.elements.direction;

import space.util.delegate.indexmap.ModificationAwareIndexMap;
import space.util.gui.GuiElement;
import space.util.gui.elements.direction.GuiDirectional;
import space.util.gui.monofont.IMonofontWithStyle;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.MonofontGuiElementCaching;
import space.util.gui.monofont.tableCreator.IMonofontTableCreator;
import space.util.gui.monofont.tableCreator.MonofontTableCreatorDefault;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.indexmap.IndexMapArray;
import space.util.string.CharSequence2D;

public abstract class MonofontElementList extends MonofontGuiElementCaching implements GuiDirectional<MonofontGuiElement>, IMonofontWithStyle {
	
	public IMonofontTableCreator style;
	public IndexMap<GuiElement<?>> list = new ModificationAwareIndexMap<>(new IndexMapArray<>(), this::modification);
	
	public MonofontElementList() {
		this(MonofontTableCreatorDefault.STYLE_DEFAULT);
	}
	
	public MonofontElementList(IMonofontTableCreator style) {
		this.style = style;
	}
	
	public void setMonofontStyle(IMonofontTableCreator style) {
		this.style = style;
	}
	
	public void add(GuiElement<?> v) {
		if (!(v instanceof MonofontGuiElement))
			throw new IllegalArgumentException("GuiElement not of Monofont!");
		list.add(v);
		((MonofontGuiElement) v).parent = this;
	}
	
	public boolean remove(GuiElement<?> v) {
		boolean ret = list.remove(v);
		if (v instanceof MonofontGuiElement)
			((MonofontGuiElement) v).parent = this;
		return ret;
	}
	
	@Override
	public IndexMap<GuiElement<?>> getList() {
		return list;
	}
	
	public IndexMap<CharSequence2D> buildList() {
		IndexMap<CharSequence2D> charTable = new IndexMapArray<>();
		for (IndexMapEntry<GuiElement<?>> entry : list.tableIterator()) {
			int pos = entry.getIndex();
			GuiElement<?> value = entry.getValue();
			
			if (value == null) {
				charTable.put(pos, null);
				continue;
			}
			
			if (!(value instanceof MonofontGuiElement))
				throw new IllegalStateException("GuiElement not of Monofont found!");
			charTable.put(pos, ((MonofontGuiElement) value).build());
		}
		
		return charTable;
	}
}
