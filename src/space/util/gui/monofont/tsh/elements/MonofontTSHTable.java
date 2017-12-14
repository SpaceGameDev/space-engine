package space.util.gui.monofont.tsh.elements;

import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.elements.direction.MonofontTable;
import space.util.gui.tsh.elements.GuiToStringHelperTableCreator;
import space.util.gui.tsh.elements.GuiToStringHelperTableCreator.GuiToStringHelperTable;
import space.util.indexmap.multi.IndexMultiMap;
import space.util.indexmap.multi.IndexMultiMap.IndexMultiMapEntry;
import space.util.indexmap.multi.IndexMultiMap2D;
import space.util.string.CharSequence2D;

public class MonofontTSHTable extends MonofontTable implements GuiToStringHelperTable {
	
	public static final GuiToStringHelperTableCreator CREATOR = MonofontTSHTable::new;
	
	public String name;
	
	public MonofontTSHTable(String name, int dimensions) {
		if (dimensions > 2)
			throw new IllegalArgumentException("dimensions " + dimensions + " > 2 are not allowed");
		this.name = name;
	}
	
	@Override
	public GuiToStringHelperTableCreator getCreator() {
		return CREATOR;
	}
	
	//rebuild
	@Override
	public CharSequence2D rebuild0() {
		IndexMultiMap<CharSequence2D> charTable = new IndexMultiMap2D<>();
		for (IndexMultiMapEntry<MonofontGuiElement> entry : table.tableIterator()) {
			MonofontGuiElement value = entry.getValue();
			charTable.put(entry.getIndex(), value != null ? value.buildSequence2D() : null);
		}
		return style.makeTable(name, this, charTable);
	}
}
