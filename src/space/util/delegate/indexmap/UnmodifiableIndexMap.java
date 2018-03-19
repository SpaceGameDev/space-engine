package space.util.delegate.indexmap;

import space.util.delegate.collection.ConvertingCollection;
import space.util.delegate.collection.UnmodifiableCollection;
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
	public void add(VALUE v) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public IndexMapEntry<VALUE> getEntry(int index) {
		return new Entry<>(indexMap.getEntry(index));
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
	public void addAll(Collection<VALUE> coll) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
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
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean remove(VALUE v) {
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
	public Collection<VALUE> values() {
		return new UnmodifiableCollection<>(super.values());
	}
	
	@Override
	public Collection<IndexMapEntry<VALUE>> table() {
		return ConvertingCollection.createConvertingBiDirectionalUnmodifiable(super.table(), Entry::new, entry -> indexMap.getEntry(entry.getIndex()));
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("unmodifiable", indexMap);
	}
	
	private static class Entry<VALUE> implements IndexMapEntry<VALUE> {
		
		public IndexMapEntry<VALUE> entry;
		
		public Entry(IndexMapEntry<VALUE> entry) {
			this.entry = entry;
		}
		
		@Override
		public int getIndex() {
			return entry.getIndex();
		}
		
		@Override
		public VALUE getValue() {
			return entry.getValue();
		}
		
		@Override
		public void setValue(VALUE v) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
	}
}
