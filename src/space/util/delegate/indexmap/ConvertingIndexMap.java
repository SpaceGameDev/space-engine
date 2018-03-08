package space.util.delegate.indexmap;

import space.util.indexmap.IndexMap;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConvertingIndexMap<F, T> implements IndexMap<T> {
	
	public IndexMap<F> indexMap;
	public Function<F, T> remap;
	public Function<T, F> reverse;
	public PutFunction<T> put;
	public Predicate<T> add;
	
	public ConvertingIndexMap(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverse, PutFunction<T> put, Predicate<T> add) {
		this.indexMap = indexMap;
		this.remap = remap;
		this.reverse = reverse;
		this.put = put;
		this.add = add;
	}
	
	@Override
	public boolean isExpandable() {
		return indexMap.isExpandable();
	}
	
	@Override
	public int size() {
		return indexMap.size();
	}
	
	@Override
	public boolean contains(int index) {
		return indexMap.contains(index);
	}
	
	@Override
	public boolean contains(T index) {
	
	}
	
	@Override
	public void add(T v) {
	
	}
	
	@Override
	public T get(int index) {
		return remap.apply(indexMap.get(index));
	}
	
	@Override
	public IndexMapEntry<T> getEntry(int index) {
		return new IndexMapEntry<>() {
			IndexMapEntry<F> entry = indexMap.getEntry(index);
			
			@Override
			public int getIndex() {
				return index;
			}
			
			@Override
			public T getValue() {
				return remap.apply(entry.getValue());
			}
			
			@Override
			public void setValue(T v) {
				put.put(index, v);
			}
		};
	}
	
	@Override
	public T put(int index, T v) {
		return put.put(index, v);
	}
	
	@Override
	public int indexOf(T v) {
	
	}
	
	@Override
	public T remove(int index) {
		return remap.apply(indexMap.remove(index));
	}
	
	@Override
	public T[] toArray() {
	
	}
	
	@Override
	public T[] toArray(T[] array) {
	
	}
	
	@Override
	public void addAll(Collection<T> coll) {
		coll.forEach(c -> add.test(c));
	}
	
	@Override
	public void putAll(IndexMap<T> indexMap) {
	
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<T> indexMap) {
	
	}
	
	@Override
	public T getOrDefault(int index, T def) {
		return null;
	}
	
	@Override
	public T putIfAbsent(int index, T v) {
		return null;
	}
	
	@Override
	public T putIfAbsent(int index, Supplier<? extends T> v) {
		return null;
	}
	
	@Override
	public boolean replace(int index, T oldValue, T newValue) {
		return false;
	}
	
	@Override
	public boolean replace(int index, T oldValue, Supplier<? extends T> newValue) {
		return false;
	}
	
	@Override
	public boolean remove(T v) {
		return false;
	}
	
	@Override
	public boolean remove(int index, T v) {
		return false;
	}
	
	@Override
	public void clear() {
	
	}
	
	@Override
	public Collection<T> values() {
		return null;
	}
	
	@Override
	public Collection<IndexMapEntry<T>> table() {
		return null;
	}
	
	@FunctionalInterface
	public interface PutFunction<T> {
		
		T put(int index, T t);
	}
}
