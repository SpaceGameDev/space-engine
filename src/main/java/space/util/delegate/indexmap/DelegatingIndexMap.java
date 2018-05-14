package space.util.delegate.indexmap;

import space.util.baseobject.ToString;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * An {@link IndexMap} delegating all calls to it's Field {@link DelegatingIndexMap#indexMap}, supplied with the Constructor.
 */
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
	public void add(VALUE value) {
		indexMap.add(value);
	}
	
	@Override
	public VALUE get(int index) {
		return indexMap.get(index);
	}
	
	@Override
	public Entry<VALUE> getEntry(int index) {
		return indexMap.getEntry(index);
	}
	
	@Override
	public VALUE put(int index, VALUE value) {
		return indexMap.put(index, value);
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
	public void addAll(Collection<? extends VALUE> coll) {
		indexMap.addAll(coll);
	}
	
	@Override
	public void putAll(IndexMap<? extends VALUE> indexMap) {
		this.indexMap.putAll(indexMap);
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<? extends VALUE> indexMap) {
		this.indexMap.putAllIfAbsent(indexMap);
	}
	
	@Override
	public VALUE getOrDefault(int index, VALUE def) {
		return indexMap.getOrDefault(index, def);
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE value) {
		return indexMap.putIfAbsent(index, value);
	}
	
	@Override
	public VALUE putIfPresent(int index, VALUE value) {
		return indexMap.putIfPresent(index, value);
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
	public boolean remove(int index, VALUE value) {
		return indexMap.remove(index, value);
	}
	
	@Override
	public VALUE compute(int index, ComputeFunction<? super VALUE, ? extends VALUE> function) {
		return indexMap.compute(index, function);
	}
	
	@Override
	public VALUE computeIfAbsent(int index, Supplier<? extends VALUE> supplier) {
		return indexMap.computeIfAbsent(index, supplier);
	}
	
	@Override
	public VALUE computeIfPresent(int index, Supplier<? extends VALUE> supplier) {
		return indexMap.computeIfPresent(index, supplier);
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
	public Collection<Entry<VALUE>> table() {
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
