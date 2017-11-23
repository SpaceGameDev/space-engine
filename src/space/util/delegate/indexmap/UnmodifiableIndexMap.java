package space.util.delegate.indexmap;

import space.util.baseobject.Copyable;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.function.Supplier;

public class UnmodifiableIndexMap<VALUE> extends DelegatingIndexMap<VALUE> {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(UnmodifiableIndexMap.class, d -> new UnmodifiableIndexMap(Copyable.copy(d.indexMap)));
	}
	
	public UnmodifiableIndexMap(IndexMap<VALUE> indexMap) {
		super(indexMap);
	}
	
	@Override
	public boolean isExpandable() {
		return false;
	}
	
	@Override
	public void add(VALUE v) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void addAll(Collection<VALUE> coll) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void putAllReplace(IndexMap<VALUE> indexMap) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE remove(int index) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE replace(int index, VALUE newValue) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE replace(int index, Supplier<? extends VALUE> newValue) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean remove(int index, VALUE v) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("unmodifiable", indexMap);
	}
}
