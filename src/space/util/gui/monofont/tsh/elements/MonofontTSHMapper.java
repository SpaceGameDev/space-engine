package space.util.gui.monofont.tsh.elements;

import space.util.gui.GuiElement;
import space.util.gui.exception.IllegalGuiElementException;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.MonofontGuiElementCaching;
import space.util.gui.monofont.tableCreator.multi.MonofontTableCreator;
import space.util.gui.monofont.tableCreator.multi.MonofontTableCreatorNoBorder;
import space.util.gui.tsh.elements.GuiToStringHelperMapperCreator;
import space.util.gui.tsh.elements.GuiToStringHelperMapperCreator.GuiToStringHelperMapper;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.indexmap.IndexMapArray;
import space.util.indexmap.multi.IndexMultiMap;
import space.util.indexmap.multi.IndexMultiMap2D;
import space.util.string.CharSequence2D;
import space.util.string.builder.CharBufferBuilder2D;

public class MonofontTSHMapper extends MonofontGuiElementCaching implements GuiToStringHelperMapper {
	
	public static final GuiToStringHelperMapperCreator CREATOR = MonofontTSHMapper::new;
	
	public String name;
	public CharSequence2D separator;
	public boolean align;
	public MonofontTableCreator style = MonofontTableCreatorNoBorder.DEFAULT;
	public IndexMap<Entry> table = new IndexMapArray<>();
	
	public MonofontTSHMapper(String name, CharSequence2D separator, boolean align) {
		this.name = name;
		this.separator = separator;
		this.align = align;
	}
	
	@Override
	public GuiToStringHelperMapperCreator getCreator() {
		return CREATOR;
	}
	
	@Override
	public GuiElement put(int[] pos, GuiElement object) {
		if (!(object instanceof MonofontGuiElement))
			throw new IllegalGuiElementException();
		MonofontGuiElement gui = (MonofontGuiElement) object;
		
		Entry ent = table.putIfAbsent(pos[0], Entry::new);
		MonofontGuiElement old;
		if (pos[1] == 0) {
			old = ent.key;
			ent.key = gui;
		} else if (pos[1] == 1) {
			old = ent.value;
			ent.value = gui;
		} else
			throw new IllegalArgumentException("pos[1] " + pos[1] + " == 0 OR 1");
		return old;
	}
	
	//rebuild
	@Override
	public CharSequence2D rebuild0() {
		IndexMultiMap<CharSequence2D> charTable = new IndexMultiMap2D<>();
		for (IndexMapEntry<Entry> entry : table.tableIterator()) {
			int index = entry.getIndex();
			Entry value = entry.getValue();
			
			if (align) {
				charTable.put(new int[] {index, 0}, value.key.buildSequence2D());
				charTable.put(new int[] {index, 1}, separator);
				charTable.put(new int[] {index, 2}, value.value.buildSequence2D());
			} else {
				charTable.put(new int[] {index, 0}, new CharBufferBuilder2D<>().append(value.key.buildSequence2D()).append(separator));
				charTable.put(new int[] {index, 1}, value.value.buildSequence2D());
			}
		}
		return style.makeTable(name, this, charTable);
	}
	
	public static class Entry {
		
		public MonofontGuiElement key;
		public MonofontGuiElement value;
		
		public Entry() {
		}
		
		public Entry(MonofontGuiElement key, MonofontGuiElement value) {
			this.key = key;
			this.value = value;
		}
		
		public boolean isEmpty() {
			return key == null && value == null;
		}
	}
}
