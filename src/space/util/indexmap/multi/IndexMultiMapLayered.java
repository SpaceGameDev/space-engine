package space.util.indexmap.multi;

import space.util.delegate.iterator.Iteratorable;

public interface IndexMultiMapLayered<VALUE> extends IndexMultiMap<VALUE> {
	
	boolean isExpandable(int[] pos, int offset);
	
	int size(int[] pos, int offset);
	
	boolean contains(int[] pos, int offset);
	
	VALUE get(int[] pos, int offset);
	
	VALUE remove(int[] pos, int offset);
	
	VALUE put(int[] pos, int offset, VALUE v);
	
	void clear(int[] pos, int offset);
	
	Iteratorable<IndexMultiMapEntry<VALUE>> tableIterator(int[] pos, int offset);
	
	@Override
	default boolean isExpandable(int[] pos) {
		return isExpandable(pos, 0);
	}
	
	@Override
	default int size(int[] pos) {
		return size(pos, 0);
	}
	
	@Override
	default boolean contains(int[] pos) {
		return contains(pos, 0);
	}
	
	@Override
	default VALUE get(int[] pos) {
		return get(pos, 0);
	}
	
	@Override
	default VALUE remove(int[] pos) {
		return remove(pos, 0);
	}
	
	@Override
	default VALUE put(int[] pos, VALUE v) {
		return put(pos, 0, v);
	}
	
	@Override
	default void clear(int[] pos) {
		clear(pos, 0);
	}
	
	@Override
	default Iteratorable<IndexMultiMapEntry<VALUE>> tableIterator(int[] pos) {
		return tableIterator(pos, 0);
	}
}
