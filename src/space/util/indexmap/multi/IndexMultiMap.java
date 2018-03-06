package space.util.indexmap.multi;

import space.util.indexmap.IndexMap;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;

public interface IndexMultiMap<VALUE> {
	
	int[] EMPTYINT = IndexMap.EMPTYINT;
	
	//expandable
	default boolean isExpandable() {
		return isExpandable(EMPTYINT);
	}
	
	boolean isExpandable(int[] pos);
	
	//size
	default int size() {
		return size(EMPTYINT);
	}
	
	int size(int[] pos);
	
	//maxsize
	default int maxSize(int depth) {
		return maxSize(EMPTYINT, depth);
	}
	
	default int maxSize(int[] pos, int depth) {
		if (depth == 0)
			return size(pos);
		
		int[] ret = new int[] {Integer.MIN_VALUE};
		maxSize(pos, depth, ret);
		return ret[0];
	}
	
	default void maxSize(int[] pos, int depth, int[] ret) {
		if (depth == 0) {
			int curr = size(pos);
			if (curr > ret[0])
				ret[0] = curr;
			return;
		}
		
		int[] temp = new int[pos.length + 1];
		System.arraycopy(pos, 0, temp, 0, pos.length);
		
		for (int i = 0; i < size(pos); i++) {
			temp[pos.length] = i;
			maxSize(temp, depth - 1, ret);
		}
	}
	
	//access
	default boolean contains(int[] pos) {
		return get(pos) != null;
	}
	
	default void add(VALUE v) {
		put(new int[] {size()}, v);
	}
	
	default void add(int[] pos, VALUE v) {
		int[] p = new int[pos.length + 1];
		System.arraycopy(pos, 0, p, 0, pos.length);
		p[pos.length] = size(pos);
		put(p, v);
	}
	
	VALUE get(int[] pos);
	
	VALUE put(int[] pos, VALUE v);
	
	VALUE remove(int[] pos);
	
	//advanced access
	default VALUE getOrDefault(int[] pos, VALUE def) {
		VALUE v = get(pos);
		return v == null ? def : v;
	}
	
	default VALUE putIfAbsent(int[] pos, VALUE v) {
		VALUE c = get(pos);
		if (c != null)
			return c;
		
		put(pos, c = v);
		return c;
	}
	
	default VALUE putIfAbsent(int[] pos, Supplier<VALUE> v) {
		VALUE c = get(pos);
		if (c != null)
			return c;
		
		put(pos, c = v.get());
		return c;
	}
	
	default VALUE replace(int[] pos, VALUE newValue) {
		if (contains(pos))
			return put(pos, newValue);
		return null;
	}
	
	default boolean replace(int[] pos, VALUE oldValue, VALUE newValue) {
		if (Objects.equals(get(pos), oldValue)) {
			put(pos, newValue);
			return true;
		}
		return false;
	}
	
	default boolean replace(int[] pos, VALUE oldValue, Supplier<VALUE> newValue) {
		if (Objects.equals(get(pos), oldValue)) {
			put(pos, newValue.get());
			return true;
		}
		return false;
	}
	
	default boolean remove(int[] pos, VALUE v) {
		VALUE c = get(pos);
		if (c == v) {
			remove(pos);
			return true;
		}
		return false;
	}
	
	//rest
	default void clear() {
		clear(EMPTYINT);
	}
	
	void clear(int[] pos);
	
	Collection<VALUE> values();
	
	default Collection<IndexMultiMapEntry<VALUE>> table() {
		return table(EMPTYINT);
	}
	
	Collection<IndexMultiMapEntry<VALUE>> table(int[] pos);
	
	interface IndexMultiMapEntry<VALUE> {
		
		int[] getIndex();
		
		VALUE getValue();
		
		void setValue(VALUE v);
	}
	
	class IndexMultiMapTableIteratorToNormalIterator<VALUE> extends AbstractCollection<VALUE> {
		
		Collection<IndexMultiMapEntry<VALUE>> coll;
		
		public IndexMultiMapTableIteratorToNormalIterator(Collection<IndexMultiMapEntry<VALUE>> coll) {
			this.coll = coll;
		}
		
		@Override
		public Iterator<VALUE> iterator() {
			return new Iterator<>() {
				Iterator<IndexMultiMapEntry<VALUE>> iter = coll.iterator();
				
				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}
				
				@Override
				public VALUE next() {
					return iter.next().getValue();
				}
			};
		}
		
		@Override
		public int size() {
			return coll.size();
		}
	}
}
