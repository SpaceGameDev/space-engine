package space.util.indexmap;

import space.util.Empties;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public interface IndexMap<VALUE> {
	
	int[] EMPTYINT = Empties.EMPTYINTARRAY;
	
	//capacity
	boolean isExpandable();
	
	int size();
	
	default boolean isEmpty() {
		return size() == 0;
	}
	
	//access
	default boolean contains(int index) {
		return get(index) != null;
	}
	
	default void add(VALUE v) {
		put(size(), v);
	}
	
	VALUE get(int index);
	
	Entry<VALUE> getEntry(int index);
	
	VALUE put(int index, VALUE v);
	
	VALUE remove(int index);
	
	VALUE[] toArray();
	
	VALUE[] toArray(VALUE[] array);
	
	//addAll
	default void addAll(Collection<? extends VALUE> coll) {
		coll.forEach(this::add);
	}
	
	default void putAll(IndexMap<? extends VALUE> indexMap) {
		for (Entry<? extends VALUE> entry : indexMap.table()) {
			VALUE value = entry.getValue();
			if (value != null)
				put(entry.getIndex(), value);
		}
	}
	
	default void putAllIfAbsent(IndexMap<? extends VALUE> indexMap) {
		for (Entry<? extends VALUE> entry : indexMap.table())
			putIfAbsent(entry.getIndex(), entry::getValue);
	}
	
	//advanced access
	default VALUE getOrDefault(int index, VALUE def) {
		VALUE v = get(index);
		return v == null ? def : v;
	}
	
	default VALUE putIfAbsent(int index, VALUE v) {
		VALUE c = get(index);
		if (c != null)
			return c;
		
		put(index, c = v);
		return c;
	}
	
	default VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		VALUE c = get(index);
		if (c != null)
			return c;
		
		put(index, c = v.get());
		return c;
	}
	
	default boolean replace(int index, VALUE oldValue, VALUE newValue) {
		if (Objects.equals(get(index), oldValue)) {
			put(index, newValue);
			return true;
		}
		return false;
	}
	
	default boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		if (Objects.equals(get(index), oldValue)) {
			put(index, newValue.get());
			return true;
		}
		return false;
	}
	
	default boolean remove(int index, VALUE v) {
		VALUE c = get(index);
		if (c == v) {
			remove(index);
			return true;
		}
		return false;
	}
	
	//other
	void clear();
	
	Collection<VALUE> values();
	
	Collection<Entry<VALUE>> table();
	
	//entry
	interface Entry<VALUE> {
		
		int getIndex();
		
		VALUE getValue();
		
		void setValue(VALUE v);
		
		VALUE setIfAbsent(Supplier<VALUE> v);
		
		default void remove() {
			setValue(null);
		}
		
		/**
		 * Returns the hash code for this {@link Entry Entry}.
		 * The hash code is generated like this:
		 * <pre>
		 *     return Integer.hashCode(this.getIndex()) ^ Objects.hashCode(this.getValue());
		 * </pre>
		 *
		 * @return the hash code of this object
		 */
		@Override
		int hashCode();
		
		/**
		 * Compares two {@link Entry Entries} for equality.
		 * For two entries to be considered equal, their key and value have to be equal.
		 * All Implementations should therefor implement it in this way:
		 * <pre>
		 * 		if (this == obj)
		 * 			return true;
		 * 		if (!(obj instanceof IndexMap.Entry))
		 *     		return false;
		 * 		IndexMap.Entry other = (IndexMap.Entry) obj;
		 * 		return (this == obj) || (this.getIndex() == other.getIndex() && Objects.equals(this.getValue(), other.getValue()));
		 * </pre>
		 *
		 * @param obj the other obj to check against
		 * @return if the index and value are equal
		 */
		@Override
		boolean equals(Object obj);
	}
}
