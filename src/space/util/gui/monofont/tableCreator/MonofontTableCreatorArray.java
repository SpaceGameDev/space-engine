package space.util.gui.monofont.tableCreator;

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
public class MonofontTableCreatorArray implements IMonofontTableCreator {
	
	@SuppressWarnings("unused")
	public static MonofontTableCreatorArray DEFAULT = new MonofontTableCreatorArray('[', ',', ']');
	
	public char fillChar;
	public char start;
	public char lineUpDown;
	public char end;
	
	public MonofontTableCreatorArray(char start, char lineUpDown, char end) {
		this(' ', start, lineUpDown, end);
	}
	
	public MonofontTableCreatorArray(char fillChar, char start, char lineUpDown, char end) {
		this.fillChar = fillChar;
		this.start = start;
		this.lineUpDown = lineUpDown;
		this.end = end;
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
		buffer.beingEdited++;
		for (IndexMultiMapEntry<CharSequence2D> elem : valueTable.tableIterator()) {
			int indexy = getSafeO(elem.getIndex(), 0, 0);
			int indexx = getSafeO(elem.getIndex(), 1, 0);
			buffer.setY(axis.getIndex(0, indexy));
			buffer.setX(axis.getIndex(1, indexx) + indexx + 1);
			int untily = axis.getIndex(0, indexy + 1);
			int untilx = axis.getIndex(1, indexx + 1) + indexx + 1;
			buffer.append(elem.getValue() == null ? String2D.EMPTY : elem.getValue(), untily, untilx, fillChar);
		}
		
		//filling start
		for (int indexy = 0; indexy < axis.getIndex(0, size[0]); indexy++) {
			buffer.setY(indexy);
			buffer.setX(0);
			buffer.append(start);
		}
		
		//filling up down
		for (int indexy = 0; indexy < axis.getIndex(0, size[0]); indexy++) {
			buffer.setY(indexy);
			for (int indexx = 1; indexx < size[1]; indexx++) {
				buffer.setX(axis.getIndex(1, indexx) + indexx);
				buffer.append(lineUpDown);
			}
		}
		
		//filling end
		for (int indexy = 0; indexy < axis.getIndex(0, size[0]); indexy++) {
			buffer.setY(indexy);
			buffer.setX(axis.getIndex(1, size[1]) + size[1]);
			buffer.append(end);
		}
		
		buffer.beingEdited--;
		return new String2D(buffer);
	}
}
