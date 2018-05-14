package space.util.delegate.indexmap;

import space.util.delegate.collection.ConvertingCollection;
import space.util.delegate.collection.UnmodifiableCollection;
import space.util.delegate.indexmap.entry.UnmodifiableEntry;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * The {@link UnmodifiableIndexMap} makes the {@link IndexMap} unmodifiable.
 */
public class UnmodifiableIndexMap<VALUE> extends DelegatingIndexMap<VALUE> {
	
	public UnmodifiableIndexMap(IndexMap<VALUE> indexMap) {
		super(indexMap);
	}
	
	@Override
	public boolean isExpandable() {
		return false;
	}
	
	@Override
	public void add(VALUE value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public Entry<VALUE> getEntry(int index) {
		return new UnmodifiableEntry<>(indexMap.getEntry(index));
	}
	
	@Override
	public VALUE put(int index, VALUE value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE remove(int index) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void addAll(Collection<? extends VALUE> coll) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void putAll(IndexMap<? extends VALUE> indexMap) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<? extends VALUE> indexMap) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE putIfPresent(int index, VALUE value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean remove(int index, VALUE value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE compute(int index, ComputeFunction<? super VALUE, ? extends VALUE> function) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public VALUE computeIfAbsent(int index, Supplier<? extends VALUE> supplier) {
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
	public VALUE computeIfPresent(int index, Supplier<? extends VALUE> supplier) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public Collection<VALUE> values() {
		return new UnmodifiableCollection<>(super.values());
	}
	
	@Override
	public Collection<Entry<VALUE>> table() {
		return new ConvertingCollection.BiDirectionalUnmodifiable<>(super.table(), UnmodifiableEntry::new, entry -> indexMap.getEntry(entry.getIndex()));
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("unmodifiable", indexMap);
	}
}
