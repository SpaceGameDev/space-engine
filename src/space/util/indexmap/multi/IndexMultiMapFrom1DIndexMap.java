package space.util.indexmap.multi;

import space.util.delegate.iterator.Iteratorable;
import space.util.delegate.iterator.UnmodifiableIterator;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.indexmap.IndexMapFromList;

import java.util.List;

import static space.util.ArrayUtils.getSafeO;

public class IndexMultiMapFrom1DIndexMap<VALUE> implements IndexMultiMap<VALUE> {
	
	public IndexMap<VALUE> indexMap;
	public boolean modifiable;
	public int[] relativePos;
	
	public IndexMultiMapFrom1DIndexMap() {
		this((IndexMap<VALUE>) null);
	}
	
	public IndexMultiMapFrom1DIndexMap(IndexMap<VALUE> indexMap) {
		this(indexMap, false);
	}
	
	public IndexMultiMapFrom1DIndexMap(IndexMap<VALUE> indexMap, boolean modifiable) {
		this(indexMap, modifiable, EMPTYINT);
	}
	
	public IndexMultiMapFrom1DIndexMap(IndexMap<VALUE> indexMap, boolean modifiable, int dimensionalDepth) {
		this(indexMap, modifiable, new int[dimensionalDepth]);
	}
	
	public IndexMultiMapFrom1DIndexMap(IndexMap<VALUE> indexMap, boolean modifiable, int[] relativePos) {
		this.indexMap = indexMap;
		this.modifiable = modifiable;
		this.relativePos = relativePos;
	}
	
	public IndexMultiMapFrom1DIndexMap(List<VALUE> list) {
		this(list, true);
	}
	
	public IndexMultiMapFrom1DIndexMap(List<VALUE> list, boolean modifiable) {
		this(list, modifiable, EMPTYINT);
	}
	
	public IndexMultiMapFrom1DIndexMap(List<VALUE> list, boolean modifiable, int dimensionalDepth) {
		this(list, modifiable, new int[dimensionalDepth]);
	}
	
	public IndexMultiMapFrom1DIndexMap(List<VALUE> list, boolean modifiable, int[] relativePos) {
		this(new IndexMapFromList<>(list), modifiable, relativePos);
	}
	
	//index
	public int getListIndex(int[] pos) {
		return isListIndexValid(pos) ? getSafeO(pos, relativePos.length, 0) : -1;
	}
	
	/**
	 * checks if position in inside the indexMap.
	 * (kinda like Arrays.equals(pos, relativePos))
	 */
	public boolean isListIndexValid(int[] pos) {
		if (pos.length != relativePos.length)
			return false;
		for (int i = 0; i < relativePos.length; i++)
			if (getSafeO(pos, i, 0) != relativePos[i])
				return false;
		return true;
	}
	
	//expandable
	@Override
	public boolean isExpandable(int[] pos) {
		return modifiable && isListIndexValid(pos);
	}
	
	//size
	
	/**
	 * size at this coordinate
	 */
	@Override
	public int size(int[] pos) {
		return isListIndexValid(pos) ? indexMap.size() : 1;
	}
	
	//access
	@Override
	public VALUE get(int[] pos) {
		int i = getListIndex(pos);
		if (i == -1)
			return null;
		return indexMap.get(i);
	}
	
	@Override
	public VALUE put(int pos[], VALUE v) {
		int i = getListIndex(pos);
		if (i == -1)
			return null;
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		return indexMap.put(i, v);
	}
	
	@Override
	public VALUE remove(int[] pos) {
		int i = getListIndex(pos);
		if (i == -1)
			return null;
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		return indexMap.remove(i);
	}
	
	//clear
	@Override
	public void clear() {
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		indexMap.clear();
	}
	
	@Override
	public void clear(int[] pos) {
		int i = getListIndex(pos);
		if (i == -1)
			return;
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		indexMap.clear();
	}
	
	//iterator
	@Override
	public Iteratorable<VALUE> iterator() {
		return modifiable ? Iteratorable.toIteratorable(indexMap.iterator()) : new UnmodifiableIterator<>(indexMap.iterator());
	}
	
	@Override
	public Iteratorable<IndexMultiMapEntry<VALUE>> tableIterator() {
		return new Iteratorable<IndexMultiMapEntry<VALUE>>() {
			
			Iteratorable<IndexMapEntry<VALUE>> iter = indexMap.tableIterator();
			
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}
			
			@Override
			public IndexMultiMapEntry<VALUE> next() {
				return new IndexMultiMapFrom1DIndexMapEntry(iter.next().getIndex());
			}
		};
	}
	
	@Override
	public Iteratorable<IndexMultiMapEntry<VALUE>> tableIterator(int[] pos) {
		if (isListIndexValid(pos)) {
			if (pos.length == relativePos.length)
				return tableIterator();
			return Iteratorable.single(new IndexMultiMapFrom1DIndexMapEntry(pos[relativePos.length]));
		}
		return Iteratorable.empty();
	}
	
	protected class IndexMultiMapFrom1DIndexMapEntry implements IndexMultiMapEntry<VALUE> {
		
		int index;
		int[] pos;
		VALUE v;
		
		public IndexMultiMapFrom1DIndexMapEntry(int index) {
			this.index = index;
			this.pos = calcPos(index);
			this.v = indexMap.get(index);
		}
		
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
			indexMap.put(index, v);
		}
	}
	
	public int[] calcPos(int index) {
		int[] pos = new int[relativePos.length + 1];
		System.arraycopy(relativePos, 0, pos, 0, relativePos.length);
		pos[relativePos.length] = index;
		return pos;
	}
}
