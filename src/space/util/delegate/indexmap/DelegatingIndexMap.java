package space.util.delegate.indexmap;

import space.util.baseobject.ToString;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * A {@link IndexMap} delegating all calls to it's Field {@link DelegatingIndexMap#indexMap}, provided by Constructor or set directly.
 */
@SuppressWarnings("unused")
public class DelegatingIndexMap<VALUE> implements ToString, IndexMap<VALUE> {
	
	public IndexMap<VALUE> indexMap;
	
	public DelegatingIndexMap(IndexMap<VALUE> indexMap) {
		this.indexMap = indexMap;
	}
	
	//delegate
	@Override
	public boolean isExpandable() {
		return indexMap.isExpandable();
	}
	
	@Override
	public int size() {
		return indexMap.size();
	}
	
	@Override
	public boolean isEmpty() {
		return indexMap.isEmpty();
	}
	
	@Override
	public boolean contains(int index) {
		return indexMap.contains(index);
	}
	
	@Override
	public void add(VALUE v) {
		indexMap.add(v);
	}
	
	@Override
	public VALUE get(int index) {
		return indexMap.get(index);
	}
	
	@Override
	public IndexMapEntry<VALUE> getEntry(int index) {
		return indexMap.getEntry(index);
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		return indexMap.put(index, v);
	}
	
	@Override
	public VALUE remove(int index) {
		return indexMap.remove(index);
	}
	
	@Override
	public VALUE[] toArray() {
		return indexMap.toArray();
	}
	
	@Override
	public VALUE[] toArray(VALUE[] array) {
		return indexMap.toArray(array);
	}
	
	@Override
	public void addAll(Collection<VALUE> coll) {
		indexMap.addAll(coll);
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		this.indexMap.putAll(indexMap);
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
		this.indexMap.putAllIfAbsent(indexMap);
	}
	
	@Override
	public VALUE getOrDefault(int index, VALUE def) {
		return indexMap.getOrDefault(index, def);
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		return indexMap.putIfAbsent(index, v);
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		return indexMap.putIfAbsent(index, v);
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		return indexMap.replace(index, oldValue, newValue);
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		return indexMap.replace(index, oldValue, newValue);
	}
	
	@Override
	public boolean remove(int index, VALUE v) {
		return indexMap.remove(index, v);
	}
	
	@Override
	public void clear() {
		indexMap.clear();
	}
	
	@Override
	public Collection<VALUE> values() {
		return indexMap.values();
	}
	
	@Override
	public Collection<IndexMapEntry<VALUE>> table() {
		return indexMap.table();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("delegate", indexMap);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
