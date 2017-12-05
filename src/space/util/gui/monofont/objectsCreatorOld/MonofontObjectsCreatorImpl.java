package space.util.gui.monofont.objectsCreator;

import space.util.delegate.iterator.Iteratorable;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.gui.monofont.elements.tshOld.MonofontObjects;
import space.util.indexmap.axis.IndexAxisMapInt;
import space.util.math.MathUtils;
import space.util.string.CharSequence2D;
import space.util.string.String2D;
import space.util.string.builder.CharBufferBuilder2D;
import space.util.string.toStringHelper.AbstractToStringHelperObjectsInstance.Entry;

import java.util.ListIterator;

/**
 * makes a table with full borders out of single characters
 */
public class MonofontObjectsCreatorImpl implements MonofontObjectsCreator {
	
	@SuppressWarnings("unused")
	public static MonofontObjectsCreator INSTANCE = new MonofontObjectsCreatorImpl('|', '-', '+', ": ");
	
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
	public CharSequence2D makeTable(MonofontObjects monofontObjects) {
		String className = monofontObjects.tsh.object.getClass().getName();
		
		//axis size
		IndexAxisMapInt axis = new IndexAxisMapInt();
		ListIterator<Entry<MonofontGuiElement>> iter = monofontObjects.tsh.list.listIterator();
		for (Entry<MonofontGuiElement> elem : Iteratorable.toIteratorable(iter)) {
			int index = iter.previousIndex();
			axis.put(new int[] {index, 0}, new int[] {1, elem.name.length()});
			axis.put(new int[] {index, 1}, new int[] {elem.value.sizeY(), elem.value.sizeX()});
		}
		
		//sizes
		int separatorLength = separator.length();
		int valueStart = axis.getIndex(1, 1) + separatorLength + 1;
		int valueEnd = MathUtils.max(axis.getIndex(1, 2) + separatorLength + 1, className.length() + 1);
		
		//buffer creation
		CharBufferBuilder2D<?> buffer = new CharBufferBuilder2D<>().setNoFillMissingSpaces();
		buffer.startEdit();
		
		//class name
		buffer.setY(0).setX(0).append(cross).append(className).fill(valueEnd - className.length(), lineLeftRight).append(cross);
		
		//entries
		int index = 0;
		iter = monofontObjects.tsh.list.listIterator();
		for (Entry<MonofontGuiElement> elem : Iteratorable.toIteratorable(iter)) {
			index = iter.previousIndex();
			int starty = axis.getIndex(0, index) + 1;
			int untily = axis.getIndex(0, index + 1) + 1;
			
			//lineLeft
			buffer.setY(starty).setX(0).append(lineUpDown);
			
			//name
			buffer.append(new String2D(elem.name + separator), untily, valueStart, fillChar);
			
			//value
			buffer.setY(starty).setX(valueStart).append(elem.value.build(), untily, valueEnd, fillChar);
			
			//lineRight
			buffer.setY(starty).setX(valueEnd).append(lineUpDown);
		}
		
		//sides
		int lasty = axis.getIndex(0, index + 1) + 1;
		fillDown(buffer, 0, 1, lasty, lineUpDown);
		fillDown(buffer, valueEnd, 1, lasty, lineUpDown);
		
		//lastLine
		buffer.setY(lasty).setX(0).append(cross).fill(valueEnd, lineLeftRight).append(cross);
		
		buffer.endEdit();
		return buffer.toString2D();
	}
	
	@SuppressWarnings("SameParameterValue")
	protected static void fillDown(CharBufferBuilder2D<?> buffer, int x, int fromy, int toy, char c) {
		for (int y = fromy; y < toy; y++) {
			buffer.setY(y);
			buffer.setX(x);
			buffer.append(c);
		}
	}
}
