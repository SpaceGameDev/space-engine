package space.util.indexmap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.Empties;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public interface IndexMap<VALUE> {
	
	int[] EMPTYINT = Empties.EMPTY_INT_ARRAY;
	
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
	
	default void add(@Nullable VALUE value) {
		put(size(), value);
	}
	
	@Nullable VALUE get(int index);
	
	@NotNull Entry<VALUE> getEntry(int index);
	
	@Nullable VALUE put(int index, @Nullable VALUE value);
	
	@Nullable VALUE remove(int index);
	
	@Nullable VALUE[] toArray();
	
	@Nullable VALUE[] toArray(@NotNull VALUE[] array);
	
	//addAll
	default void addAll(@NotNull Collection<? extends VALUE> coll) {
		coll.forEach(this::add);
	}
	
	default void putAll(@NotNull IndexMap<? extends VALUE> indexMap) {
		for (Entry<? extends VALUE> entry : indexMap.table()) {
			VALUE value = entry.getValue();
			if (value != null)
				put(entry.getIndex(), value);
		}
	}
	
	default void putAllIfAbsent(@NotNull IndexMap<? extends VALUE> indexMap) {
		for (Entry<? extends VALUE> entry : indexMap.table())
			computeIfAbsent(entry.getIndex(), entry::getValue);
	}
	
	//advanced access
	@Nullable
	default VALUE getOrDefault(int index, @Nullable VALUE def) {
		VALUE v = get(index);
		return v == null ? def : v;
	}
	
	@Nullable
	default VALUE putIfAbsent(int index, @Nullable VALUE value) {
		VALUE oldValue = get(index);
		if (oldValue != null)
			return oldValue;
		
		put(index, value);
		return value;
	}
	
	@Nullable
	default VALUE putIfPresent(int index, @Nullable VALUE value) {
		VALUE oldValue = get(index);
		if (oldValue == null)
			return null;
		
		put(index, value);
		return value;
	}
	
	default boolean replace(int index, @Nullable VALUE oldValue, @Nullable VALUE newValue) {
		if (Objects.equals(get(index), oldValue)) {
			put(index, newValue);
			return true;
		}
		return false;
	}
	
	default boolean replace(int index, @Nullable VALUE oldValue, @NotNull Supplier<? extends VALUE> newValue) {
		if (Objects.equals(get(index), oldValue)) {
			put(index, newValue.get());
			return true;
		}
		return false;
	}
	
	default boolean remove(int index, @Nullable VALUE value) {
		if (get(index) == value) {
			remove(index);
			return true;
		}
		return false;
	}
	
	//compute
	@Nullable
	default VALUE compute(int index, @NotNull ComputeFunction<? super VALUE, ? extends VALUE> function) {
		VALUE oldValue = get(index);
		VALUE newValue = function.apply(index, oldValue);
		if (Objects.equals(oldValue, newValue))
			put(index, newValue);
		return newValue;
	}
	
	@Nullable
	default VALUE computeIfAbsent(int index, @NotNull Supplier<? extends VALUE> supplier) {
		VALUE oldValue = get(index);
		if (oldValue == null)
			put(index, oldValue = supplier.get());
		return oldValue;
	}
	
	@Nullable
	default VALUE computeIfPresent(int index, @NotNull Supplier<? extends VALUE> supplier) {
		VALUE oldValue = get(index);
		if (oldValue != null)
			put(index, oldValue = supplier.get());
		return oldValue;
	}
	
	//other
	void clear();
	
	@NotNull Collection<VALUE> values();
	
	@NotNull Collection<Entry<VALUE>> table();
	
	//entry
	interface Entry<VALUE> {
		
		int getIndex();
		
		@Nullable VALUE getValue();
		
		void setValue(@Nullable VALUE v);
		
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
	
	@FunctionalInterface
	interface ComputeFunction<F, R> {
		
		R apply(int index, F value);
	}
}
