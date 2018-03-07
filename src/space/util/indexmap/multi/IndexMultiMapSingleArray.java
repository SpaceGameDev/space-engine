package space.util.indexmap.multi;

import space.util.delegate.impl.ArrayIterable;
import space.util.delegate.iterator.Iteratorable;

import java.util.Arrays;
import java.util.Collection;

/**
 * works like a {@link IndexMultiMap}, but internally compresses down the information into a 1D-array similarly to what you see with Textures
 */
public class IndexMultiMapSingleArray<VALUE> implements IndexMultiMap<VALUE> {
	
	public VALUE[] array;
	public int[] size;
	public int[] multi;
	
	public IndexMultiMapSingleArray(int[] size) {
		this.size = size;
		//noinspection unchecked
		array = (VALUE[]) new Object[calculateMulti()];
	}
	
	public int calculateMulti() {
		int l = size.length;
		int last = l - 1;
		
		multi = new int[l];
		int curr = 1;
		
		for (int i = 0; i < l; i++) {
			multi[i] = curr;
			curr *= size[i];
		}
		return curr;
	}
	
	public int getIndex(int[] pos) {
		int ret = 0;
		for (int i = 0; i < pos.length; i++) {
			int currSize = size[i];
			int currIndex = pos[i];
			if (currIndex >= currSize)
				throw new ArrayIndexOutOfBoundsException("Dimension " + i + ": index " + currIndex + " >= size " + currSize);
			
			ret += currIndex * multi[i];
		}
		return ret;
	}
	
	public int[] fromIndex(int index) {
		if (index >= array.length)
			throw new ArrayIndexOutOfBoundsException("index " + index + "exceeds arraysize " + array.length + "!");
		int l = size.length;
		int[] ret = new int[l];
		for (int i = l - 1; i >= 0; i--) {
			int currSize = size[i];
			ret[i] = index % currSize;
			index /= currSize;
		}
		return ret;
	}
	
	@Override
	public boolean isExpandable() {
		return false;
	}
	
	@Override
	public boolean isExpandable(int[] pos) {
		return false;
	}
	
	@Override
	public int size(int[] pos) {
		return size[pos.length];
	}
	
	@Override
	public boolean contains(int[] pos) {
		return get(pos) != null;
	}
	
	@Override
	public VALUE get(int[] pos) {
		return array[getIndex(pos)];
	}
	
	@Override
	public VALUE remove(int[] pos) {
		return put(pos, null);
	}
	
	@Override
	public VALUE put(int[] pos, VALUE v) {
		int index = getIndex(pos);
		VALUE ret = array[index];
		array[index] = v;
		return ret;
	}
	
	@Override
	public void clear(int[] pos) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void clear() {
		Arrays.fill(array, null);
	}
	
	@Override
	public Collection<VALUE> values() {
		return new ArrayIterable<>(array);
	}
	
	@Override
	public Collection<IndexMultiMapEntry<VALUE>> table(int[] pos) {
		return new Iteratorable<IndexMultiMapEntry<VALUE>>() {
			
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < array.length;
			}
			
			@Override
			public IndexMultiMapEntry<VALUE> next() {
				int i = index++;
				return new IndexMultiMapEntry<VALUE>() {
					int[] pos = fromIndex(i);
					VALUE v = array[i];
					
					@Override
					public int[] getIndex() {
						return pos;
					}
					
					@Override
					public VALUE getValue() {
						return v;
					}
					
					@Override
					public void setValue(VALUE v) {
						this.v = v;
						array[i] = v;
					}
				};
			}
		};
	}
}
