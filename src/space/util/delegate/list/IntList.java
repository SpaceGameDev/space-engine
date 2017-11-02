package space.util.delegate.list;

import space.util.ArrayUtils;
import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.gui.monofont.MonofontGuiElement;
import space.util.string.toStringHelper.ToStringHelperCollection;

import java.util.Arrays;

public class IntList implements BaseObject {
	
	static {
		BaseObject.initClass(IntList.class, IntList::new, d -> new IntList(Copyable.copy(d.array)));
	}
	
	public static final IntList EMPTY = new IntList(0);
	public static int defaultCapacity = 4;
	public static int expandShift = 1;
	
	public int[] array;
	public int size;
	
	public IntList() {
		this(defaultCapacity);
	}
	
	public IntList(int initCapacity) {
		this(new int[initCapacity], 0);
	}
	
	public IntList(int[] array) {
		this(array, array.length);
	}
	
	public IntList(int[] array, int size) {
		this.array = array;
		this.size = size;
	}
	
	public boolean ensureCapacityIndex(int index) {
		return ensureCapacity(index + 1);
	}
	
	public boolean ensureCapacity(int capa) {
		if (capa >= array.length) {
			int[] old = array;
			array = new int[ArrayUtils.getOptimalArraySizeExpansion(old.length, capa, expandShift)];
			System.arraycopy(old, 0, array, 0, old.length);
			return true;
		}
		return false;
	}
	
	public void checkIndex(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException();
	}
	
	public void add(int i) {
		ensureCapacityIndex(size);
		array[size++] = i;
	}
	
	public int set(int index, int i) {
		checkIndex(index);
		int old = array[index];
		array[index] = i;
		return old;
	}
	
	public int get(int index) {
		checkIndex(index);
		return array[index];
	}
	
	public int poll() {
		if (size == 0)
			throw new IndexOutOfBoundsException();
		int ret = array[--size];
		array[size] = 0;
		return ret;
	}
	
	public void remove(int index) {
		System.arraycopy(array, index + 1, array, index, (--size) - index);
	}
	
	public int[] toArray() {
		int[] ret = new int[size];
		System.arraycopy(array, 0, ret, 0, size);
		return ret;
	}
	
	public int[] toArrayBackwards() {
		int[] ret = new int[size];
		int g = size - 1;
		for (int i = 0; i < size; ) {
			ret[i] = array[g];
			i++;
			g--;
		}
		return ret;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof IntList))
			return false;
		
		IntList intList = (IntList) o;
		
		if (size != intList.size)
			return false;
		return Arrays.equals(array, intList.array);
	}
	
	@Override
	public int hashCode() {
		int result = Arrays.hashCode(array);
		result = 31 * result + size;
		return result;
	}
	
	@Override
	public MonofontGuiElement toTSH(ToStringHelperCollection api) {
		return api.to(array, 0, size);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
