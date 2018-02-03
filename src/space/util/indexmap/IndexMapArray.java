package space.util.indexmap;

import space.util.ArrayUtils;
import space.util.baseobject.ToString;
import space.util.delegate.impl.ArrayIterable.ArrayIterator;
import space.util.delegate.iterator.Iteratorable;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

public class IndexMapArray<VALUE> implements IndexMap<VALUE>, ToString {
	
	public static int DEFAULT_CAPACITY = 4;
	public static int EXPAND_SHIFT = 1;
	
	public int length;
	public VALUE[] array;
	
	public IndexMapArray() {
		this(DEFAULT_CAPACITY);
	}
	
	public IndexMapArray(int initCapacity) {
		//noinspection unchecked
		this.array = (VALUE[]) new Object[initCapacity];
	}
	
	public IndexMapArray(VALUE[] array) {
		this.array = array;
		this.length = array.length;
	}
	
	//capacity
	public boolean ensureCapacityAvailable(int index) {
		return ensureCapacity(index + 1);
	}
	
	public boolean ensureCapacity(int capa) {
		int oldl = array.length;
		if (oldl < capa) {
			array = Arrays.copyOf(array, ArrayUtils.getOptimalArraySizeExpansion(oldl, capa, EXPAND_SHIFT));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isExpandable() {
		return true;
	}
	
	@Override
	public int size() {
		return length;
	}
	
	//access
	@Override
	public VALUE get(int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException("no negative index!");
		if (index >= array.length)
			return null;
		
		return getInternal(index);
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		if (index < 0)
			throw new IndexOutOfBoundsException("no negative index!");
		return putInternal(index, v);
	}
	
	@Override
	public int indexOf(VALUE v) {
		return Arrays.binarySearch(array, 0, length, v);
	}
	
	@Override
	public VALUE remove(int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException("no negative index!");
		if (index >= array.length)
			return null;
		
		return removeInternal(index);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE[] toArray() {
		return toArray((VALUE[]) new Object[length]);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE[] toArray(VALUE[] array) {
		if (array.length < length)
			array = (VALUE[]) new Object[length];
		System.arraycopy(this.array, 0, array, 0, length);
		return array;
	}
	
	//access unchecked
	protected VALUE getInternal(int index) {
		return array[index];
	}
	
	protected VALUE removeInternal(int index) {
		VALUE ret = array[index];
		array[index] = null;
		return ret;
	}
	
	protected VALUE putInternal(int index, VALUE v) {
		ensureCapacityAvailable(index);
		if (index >= length)
			length = index + 1;
		
		VALUE ret = array[index];
		array[index] = v;
		return ret;
	}
	
	//addAll
	@Override
	public void addAll(Collection<VALUE> coll) {
		ensureCapacity(length + coll.size());
		IndexMap.super.addAll(coll);
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		ensureCapacity(indexMap.size());
		IndexMap.super.putAll(indexMap);
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
		ensureCapacity(indexMap.size());
		IndexMap.super.putAllIfAbsent(indexMap);
	}
	
	//advanced access
	@Override
	public VALUE getOrDefault(int index, VALUE def) {
		if (index < 0)
			throw new IndexOutOfBoundsException("no negative index!");
		if (index >= array.length)
			return def;
		
		VALUE ret = getInternal(index);
		return ret != null ? ret : def;
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		VALUE ret = get(index);
		if (ret != null)
			return ret;
		
		putInternal(index, v);
		return v;
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		VALUE ret = get(index);
		if (ret != null)
			return ret;
		
		putInternal(index, ret = v.get());
		return ret;
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		if (index < 0)
			throw new IndexOutOfBoundsException("no negative index!");
		if (index >= array.length || array[index] != oldValue)
			return false;
		
		putInternal(index, newValue);
		return true;
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		if (index < 0)
			throw new IndexOutOfBoundsException("no negative index!");
		if (index >= array.length || array[index] != oldValue)
			return false;
		
		putInternal(index, newValue.get());
		return true;
	}
	
	@Override
	public boolean remove(int index, VALUE v) {
		if (index < 0)
			throw new IndexOutOfBoundsException("no negative index!");
		if (index >= array.length || array[index] != v)
			return false;
		
		removeInternal(index);
		return true;
	}
	
	//other
	@Override
	public void clear() {
		Arrays.fill(array, 0, length, null);
	}
	
	@Override
	public Iteratorable<VALUE> iterator() {
		return new Iteratorable<VALUE>() {
			ArrayIterator<VALUE> iter = new ArrayIterator<>(array, 0, length);
			
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}
			
			@Override
			public VALUE next() {
				return iter.next();
			}
			
			@Override
			public void remove() {
				IndexMapArray.this.remove(iter.index);
			}
		};
	}
	
	@Override
	public Iteratorable<IndexMapEntry<VALUE>> tableIterator() {
		return new Iteratorable<IndexMapEntry<VALUE>>() {
			ArrayIterator<VALUE> iter = new ArrayIterator<>(array, 0, length);
			
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}
			
			@Override
			public IndexMapEntry<VALUE> next() {
				return new IndexMapEntry<VALUE>() {
					int index = iter.index;
					VALUE next = iter.next();
					
					@Override
					public int getIndex() {
						return index;
					}
					
					@Override
					public VALUE getValue() {
						return next;
					}
					
					@Override
					public void setValue(VALUE v) {
						next = v;
						put(iter.index, v);
					}
				};
			}
			
			@Override
			public void remove() {
				IndexMapArray.this.remove(iter.index);
			}
		};
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.toString(array, 0, length);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
