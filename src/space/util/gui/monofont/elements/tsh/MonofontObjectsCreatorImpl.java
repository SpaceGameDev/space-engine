package space.util.gui.monofont.elements.tsh;

import space.util.delegate.iterator.Iteratorable;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.indexmap.axis.IndexAxisMapInt;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.builder.CharBufferBuilder2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance.Entry;

import java.util.ListIterator;

/**
 * makes a table with full borders out of single characters
 */
public class MonofontObjectsCreatorImpl implements IMonofontObjectsCreator {
	
	@SuppressWarnings("unused")
	public static IMonofontObjectsCreator DEFAULT = new MonofontObjectsCreatorImpl('|', '-', '+', ": ");
	
	public char fillChar;
	public char lineUpDown;
	public char lineLeftRight;
	public char cross;
	public String separator;
	
	public MonofontObjectsCreatorImpl(char lineUpDown, char lineLeftRight, char cross, String separator) {
		this(' ', lineUpDown, lineLeftRight, cross, separator);
	}
	
	public MonofontObjectsCreatorImpl(char fillChar, char lineUpDown, char lineLeftRight, char cross, String separator) {
		this.fillChar = fillChar;
		this.lineUpDown = lineUpDown;
		this.lineLeftRight = lineLeftRight;
		this.cross = cross;
		this.separator = separator;
	}
	
	@Override
	public CharSequence2D makeTable(AbstractToStringHelperObjectsInstance<MonofontGuiElement> tsh) {
		//axis size
		IndexAxisMapInt axis = new IndexAxisMapInt(); //content index
		ListIterator<Entry<MonofontGuiElement>> iter = tsh.list.listIterator();
		for (Entry<MonofontGuiElement> elem : Iteratorable.toIteratorable(iter)) {
			int index = iter.previousIndex();
			axis.put(new int[] {index, 0}, new int[] {1, elem.name.length()});
			axis.put(new int[] {index, 1}, new int[] {elem.value.sizeY(), elem.value.sizeX()});
		}
		
		//middleLine
		int separatorLength = separator.length();
		int middleLineStart = axis.getIndex(1, 1);
		int valueStart = middleLineStart + separatorLength;
		int valueEnd = axis.getIndex(1, 2) + separatorLength;
		
		//buffer building content
		CharBufferBuilder2D<?> buffer = new CharBufferBuilder2D<>().setNoFillMissingSpaces();
		buffer.startEdit();
		iter = tsh.list.listIterator();
		for (Entry<MonofontGuiElement> elem : Iteratorable.toIteratorable(iter)) {
			int index = iter.previousIndex();
			int untily = axis.getIndex(0, index + 1) + index;
			
			//name
			buffer.setY(axis.getIndex(0, index) + index + 1);
			buffer.setX(0);
			buffer.append(new String2D(elem.name + separator), untily, middleLineStart, fillChar);
			
			//value
			buffer.setY(axis.getIndex(0, index) + index + 1);
			buffer.setX(valueStart);
			buffer.append(elem.value.build(), untily, valueEnd, fillChar);
		}
		
		buffer.endEdit();
		return new String2D(buffer);
	}
}
