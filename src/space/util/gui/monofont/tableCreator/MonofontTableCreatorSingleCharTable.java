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
 * makes a table with full borders out of single characters
 */
public class MonofontTableCreatorSingleCharTable implements IMonofontTableCreator {
	
	@SuppressWarnings("unused")
	public static IMonofontTableCreator DEFAULT = new MonofontTableCreatorSingleCharTable('|', '-', '+');
	
	public char fillChar;
	public char lineUpDown;
	public char lineLeftRight;
	public char cross;
	
	public MonofontTableCreatorSingleCharTable(char lineUpDown, char lineLeftRight, char cross) {
		this(' ', lineUpDown, lineLeftRight, cross);
	}
	
	public MonofontTableCreatorSingleCharTable(char fillChar, char lineUpDown, char lineLeftRight, char cross) {
		this.fillChar = fillChar;
		this.lineUpDown = lineUpDown;
		this.lineLeftRight = lineLeftRight;
		this.cross = cross;
	}
	
	@Override
	public CharSequence2D makeTable(MonofontGuiElement guiElement, IndexMultiMap<CharSequence2D> valueTable) {
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
			buffer.setY(axis.getIndex(0, indexy) + indexy + 1);
			buffer.setX(axis.getIndex(1, indexx) + indexx + 1);
			int untily = axis.getIndex(0, indexy + 1) + indexy + 1;
			int untilx = axis.getIndex(1, indexx + 1) + indexx + 1;
			buffer.append(elem.getValue() == null ? String2D.EMPTY : elem.getValue(), untily, untilx, fillChar);
		}
		
		//filling left right
		for (int indexy = 0; indexy < size[0] + 1; indexy++) {
			buffer.setY(axis.getIndex(0, indexy) + indexy);
			buffer.setX(0);
			buffer.fill(axis.getIndex(1, size[1]) + size[1] + 1, lineLeftRight);
		}
		
		//filling up down
		for (int indexy = 0; indexy < axis.getIndex(0, size[0]) + size[0] + 1; indexy++) {
			buffer.setY(indexy);
			for (int indexx = 0; indexx < size[1] + 1; indexx++) {
				buffer.setX(axis.getIndex(1, indexx) + indexx);
				buffer.append(lineUpDown);
			}
		}
		
		//filling cross
		for (int indexy = 0; indexy < size[0] + 1; indexy++) {
			buffer.setY(axis.getIndex(0, indexy) + indexy);
			for (int indexx = 0; indexx < size[1] + 1; indexx++) {
				buffer.setX(axis.getIndex(1, indexx) + indexx);
				buffer.append(cross);
			}
		}
		
		buffer.beingEdited--;
		return new String2D(buffer);
	}
}
