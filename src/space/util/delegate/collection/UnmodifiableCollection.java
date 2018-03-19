package space.util.delegate.collection;

import space.util.baseobject.ToString;
import space.util.delegate.iterator.UnmodifiableIterator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The {@link UnmodifiableCollection} will throw {@link UnsupportedOperationException} when trying to change the {@link Collection}
 */
@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
public class UnmodifiableCollection<E> implements Collection<E>, ToString {
	
	public Collection<E> coll;
	
	public UnmodifiableCollection(Collection<E> coll) {
		this.coll = coll;
	}
	
	@Override
	public int size() {
		return coll.size();
	}
	
	@Override
	public boolean isEmpty() {
		return coll.isEmpty();
	}
	
	@Override
	public boolean contains(Object o) {
		return coll.contains(o);
	}
	
	@Override
	public Iterator<E> iterator() {
		return new UnmodifiableIterator<>(coll.iterator());
	}
	
	@Override
	public Object[] toArray() {
		return coll.toArray();
	}
	
	@Override
	@SuppressWarnings("SuspiciousToArrayCall")
	public <T> T[] toArray(T[] a) {
		return coll.toArray(a);
	}
	
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return coll.containsAll(c);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public Spliterator<E> spliterator() {
		return coll.spliterator();
	}
	
	@Override
	public Stream<E> stream() {
		return coll.stream();
	}
	
	@Override
	public Stream<E> parallelStream() {
		return coll.parallelStream();
	}
	
	@Override
	public void forEach(Consumer<? super E> action) {
		coll.forEach(action);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("coll", this.coll);
		return tsh.build();
	}
}
