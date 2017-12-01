package space.util.gui.monofont.elements.direction;

import space.util.delegate.indexmap.ModificationAwareIndexMap;
import space.util.gui.GuiElement;
import space.util.gui.elements.direction.GuiDirectionalCreator.GuiDirectional;
import space.util.gui.exception.IllegalGuiElementException;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.MonofontGuiElementCaching;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.indexmap.IndexMapArray;
import space.util.string.CharSequence2D;

public abstract class MonofontElementList extends MonofontGuiElementCaching implements GuiDirectional {
	
	public IndexMap<MonofontGuiElement> list = new ModificationAwareIndexMap<>(new IndexMapArray<>(), this::modification);
	
	public MonofontElementList() {
	}
	
	@Override
	public void add(GuiElement gui) {
		if (!(gui instanceof MonofontGuiElement))
			throw new IllegalGuiElementException(gui);
		MonofontGuiElement v = (MonofontGuiElement) gui;
		v.setParent(this);
		list.add(v);
	}
	
	@Override
	public GuiElement put(int index, GuiElement gui) {
		if (!(gui instanceof MonofontGuiElement))
			throw new IllegalGuiElementException(gui);
		MonofontGuiElement v = (MonofontGuiElement) gui;
		v.setParent(this);
		MonofontGuiElement old = list.put(index, v);
		if (old != null)
			old.setParent(null);
		return old;
	}
	
	@Override
	public GuiElement remove(int index) {
		MonofontGuiElement old = list.remove(index);
		if (old != null)
			old.setParent(null);
		return old;
	}
	
	@Override
	public void remove(GuiElement gui) {
		if (!(gui instanceof MonofontGuiElement))
			throw new IllegalGuiElementException(gui);
		MonofontGuiElement v = (MonofontGuiElement) gui;
		list.remove(v);
		v.setParent(null);
	}
	
	public IndexMap<CharSequence2D> buildList() {
		IndexMap<CharSequence2D> charTable = new IndexMapArray<>();
		for (IndexMapEntry<MonofontGuiElement> entry : list.tableIterator()) {
			GuiElement value = entry.getValue();
			charTable.put(entry.getIndex(), value != null ? ((MonofontGuiElement) value).build() : null);
		}
		return charTable;
	}
}
