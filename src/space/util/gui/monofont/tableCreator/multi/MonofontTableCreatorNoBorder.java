package space.util.gui.monofont.tableCreator.multi;

import space.util.gui.monofont.MonofontGuiElement;
import space.util.indexmap.axis.IndexAxisMapInt;
import space.util.indexmap.multi.IndexMultiMap;
import space.util.indexmap.multi.IndexMultiMap.IndexMultiMapEntry;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.builder.CharBufferBuilder2D;

import static space.util.ArrayUtils.getSafeO;

/**
 * makes a table with only a left-right separator
 */
public class MonofontTableCreatorNoBorder implements IMonofontTableCreator {
	
	@SuppressWarnings("unused")
	public static MonofontTableCreatorNoBorder DEFAULT = new MonofontTableCreatorNoBorder();
	
	public char fillChar;
	
	public MonofontTableCreatorNoBorder() {
		this(' ');
	}
	
	public MonofontTableCreatorNoBorder(char fillChar) {
		this.fillChar = fillChar;
	}
	
	@Override
	public CharSequence2D makeTable(String name, MonofontGuiElement guiElement, IndexMultiMap<CharSequence2D> valueTable) {
		//axis size
		int[] size = new int[] {valueTable.size(), valueTable.maxSize(1)};
		IndexAxisMapInt axis = new IndexAxisMapInt(); //content index
		for (IndexMultiMapEntry<CharSequence2D> elem : valueTable.tableIterator())
			if (elem.getValue() != null)
				axis.put(new int[] {getSafeO(elem.getIndex(), 0, 0), getSafeO(elem.getIndex(), 1, 0)}, new int[] {elem.getValue().height(), elem.getValue().maxLength()});
		
		//buffer building content
		CharBufferBuilder2D<?> buffer = new CharBufferBuilder2D<>().setNoFillMissingSpaces();
		buffer.startEdit();
		for (IndexMultiMapEntry<CharSequence2D> elem : valueTable.tableIterator()) {
			int indexy = getSafeO(elem.getIndex(), 0, 0);
			int indexx = getSafeO(elem.getIndex(), 1, 0);
			buffer.setY(axis.getIndex(0, indexy));
			buffer.setX(axis.getIndex(1, indexx));
			int untily = axis.getIndex(0, indexy + 1);
			int untilx = axis.getIndex(1, indexx + 1);
			buffer.append(elem.getValue() == null ? String2D.EMPTY : elem.getValue(), untily, untilx, fillChar);
		}
		
		buffer.endEdit();
		return new String2D(buffer);
	}
}
