package space.util.delegate.indexmap;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DelegatingIndexMap<VALUE> implements ToString, IndexMap<VALUE> {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(DelegatingIndexMap.class, d -> new DelegatingIndexMap(Copyable.copy(d.indexMap)));
	}
	
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
	public boolean contains(int index) {
		return indexMap.contains(index);
	}
	
	@Override
	public void add(VALUE v) {
		indexMap.add(v);
	}
	
	@Override
	public int indexOf(VALUE v) {
		return indexMap.indexOf(v);
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
	public VALUE get(int index) {
		return indexMap.get(index);
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
	public void addAll(Collection<VALUE> coll) {
		indexMap.addAll(coll);
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		this.indexMap.putAll(indexMap);
	}
	
	@Override
	public void putAllReplace(IndexMap<VALUE> indexMap) {
		this.indexMap.putAllReplace(indexMap);
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
	public VALUE replace(int index, VALUE newValue) {
		return indexMap.replace(index, newValue);
	}
	
	@Override
	public VALUE replace(int index, Supplier<? extends VALUE> newValue) {
		return indexMap.replace(index, newValue);
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
	public Iteratorable<VALUE> iterator() {
		return indexMap.iterator();
	}
	
	@Override
	public Iteratorable<IndexMapEntry<VALUE>> tableIterator() {
		return indexMap.tableIterator();
	}
	
	@Override
	public void forEach(Consumer<? super VALUE> action) {
		indexMap.forEach(action);
	}
	
	@Override
	public Spliterator<VALUE> spliterator() {
		return indexMap.spliterator();
	}
	
	//object
	@Override
	public int hashCode() {
		return indexMap.hashCode();
	}
	
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object obj) {
		return indexMap.equals(obj);
	}
	
	//super
	protected boolean supercontains(int index) {
		return IndexMap.super.contains(index);
	}
	
	protected void superadd(VALUE v) {
		IndexMap.super.add(v);
	}
	
	protected void superaddAll(Collection<VALUE> coll) {
		IndexMap.super.addAll(coll);
	}
	
	protected void superputAll(IndexMap<VALUE> indexMap) {
		IndexMap.super.putAll(indexMap);
	}
	
	protected void superputAllReplace(IndexMap<VALUE> indexMap) {
		IndexMap.super.putAllReplace(indexMap);
	}
	
	protected void superputAllIfAbsent(IndexMap<VALUE> indexMap) {
		IndexMap.super.putAllIfAbsent(indexMap);
	}
	
	protected VALUE supergetOrDefault(int index, VALUE def) {
		return IndexMap.super.getOrDefault(index, def);
	}
	
	protected VALUE superputIfAbsent(int index, VALUE v) {
		return IndexMap.super.putIfAbsent(index, v);
	}
	
	protected VALUE superputIfAbsent(int index, Supplier<? extends VALUE> v) {
		return IndexMap.super.putIfAbsent(index, v);
	}
	
	protected VALUE superreplace(int index, VALUE newValue) {
		return IndexMap.super.replace(index, newValue);
	}
	
	protected VALUE superreplace(int index, Supplier<? extends VALUE> newValue) {
		return IndexMap.super.replace(index, newValue);
	}
	
	protected boolean superreplace(int index, VALUE oldValue, VALUE newValue) {
		return IndexMap.super.replace(index, oldValue, newValue);
	}
	
	protected boolean superreplace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		return IndexMap.super.replace(index, oldValue, newValue);
	}
	
	protected boolean superremove(int index, VALUE v) {
		return IndexMap.super.remove(index, v);
	}
	
	protected void superforEach(Consumer<? super VALUE> action) {
		IndexMap.super.forEach(action);
	}
	
	protected Spliterator<VALUE> superspliterator() {
		return IndexMap.super.spliterator();
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
