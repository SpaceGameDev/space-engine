package space.util.delegate.collection;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A {@link Collection} delegating all calls to it's Field {@link SupplierCollection#coll}, which is an {@link Supplier} of Type {@link Collection}, allowing for unique usages. The {@link Supplier} is provided by Constructor or set directly.
 */
public class SupplierCollection<E> implements ToString, Collection<E> {
	
	public Supplier<Collection<E>> coll;
	
	public SupplierCollection(Supplier<Collection<E>> coll) {
		this.coll = coll;
	}
	
	@Override
	public int size() {
		return coll.get().size();
	}
	
	@Override
	public boolean isEmpty() {
		return coll.get().isEmpty();
	}
	
	@Override
	public boolean contains(Object o) {
		return coll.get().contains(o);
	}
	
	@Override
	public Iterator<E> iterator() {
		return coll.get().iterator();
	}
	
	@Override
	public Object[] toArray() {
		return coll.get().toArray();
	}
	
	@Override
	@SuppressWarnings("SuspiciousToArrayCall")
	public <T> T[] toArray(T[] a) {
		return coll.get().toArray(a);
	}
	
	@Override
	public boolean add(E e) {
		return coll.get().add(e);
	}
	
	@Override
	public boolean remove(Object o) {
		return coll.get().remove(o);
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return coll.get().containsAll(c);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return coll.get().addAll(c);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return coll.get().removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return coll.get().retainAll(c);
	}
	
	@Override
	public void clear() {
		coll.get().clear();
	}
	
	@Override
	public Spliterator<E> spliterator() {
		return coll.get().spliterator();
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return coll.get().removeIf(filter);
	}
	
	@Override
	public Stream<E> stream() {
		return coll.get().stream();
	}
	
	@Override
	public Stream<E> parallelStream() {
		return coll.get().parallelStream();
	}
	
	@Override
	public void forEach(Consumer<? super E> action) {
		coll.get().forEach(action);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("supplier", coll);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
