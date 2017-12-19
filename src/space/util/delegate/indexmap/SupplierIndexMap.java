package space.util.delegate.indexmap;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.function.Supplier;

public class SupplierIndexMap<VALUE> implements ToString, IndexMap<VALUE> {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(SupplierIndexMap.class, d -> new SupplierIndexMap(Copyable.copy(d.indexMap)));
	}
	
	public Supplier<IndexMap<VALUE>> indexMap;
	
	public SupplierIndexMap(Supplier<IndexMap<VALUE>> indexMap) {
		this.indexMap = indexMap;
	}
	
	@Override
	public boolean isExpandable() {
		return indexMap.get().isExpandable();
	}
	
	@Override
	public int size() {
		return indexMap.get().size();
	}
	
	@Override
	public boolean contains(int index) {
		return indexMap.get().contains(index);
	}
	
	@Override
	public int indexOf(VALUE v) {
		return indexMap.get().indexOf(v);
	}
	
	@Override
	public VALUE[] toArray() {
		return indexMap.get().toArray();
	}
	
	@Override
	public VALUE[] toArray(VALUE[] array) {
		return indexMap.get().toArray(array);
	}
	
	@Override
	public void add(VALUE v) {
		indexMap.get().add(v);
	}
	
	@Override
	public VALUE get(int index) {
		return indexMap.get().get(index);
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		return indexMap.get().put(index, v);
	}
	
	@Override
	public VALUE remove(int index) {
		return indexMap.get().remove(index);
	}
	
	@Override
	public void addAll(Collection<VALUE> coll) {
		indexMap.get().addAll(coll);
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		this.indexMap.get().putAll(indexMap);
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
		this.indexMap.get().putAllIfAbsent(indexMap);
	}
	
	@Override
	public VALUE getOrDefault(int index, VALUE def) {
		return indexMap.get().getOrDefault(index, def);
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		return indexMap.get().putIfAbsent(index, v);
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		return indexMap.get().putIfAbsent(index, v);
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		return indexMap.get().replace(index, oldValue, newValue);
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		return indexMap.get().replace(index, oldValue, newValue);
	}
	
	@Override
	public boolean remove(int index, VALUE v) {
		return indexMap.get().remove(index, v);
	}
	
	@Override
	public void clear() {
		indexMap.get().clear();
	}
	
	@Override
	public Iteratorable<VALUE> iterator() {
		return indexMap.get().iterator();
	}
	
	@Override
	public Iteratorable<IndexMapEntry<VALUE>> tableIterator() {
		return indexMap.get().tableIterator();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("supplier", indexMap);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
