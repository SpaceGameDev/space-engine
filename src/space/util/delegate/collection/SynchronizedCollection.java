package space.util.delegate.collection;

import space.util.baseobjectOld.BaseObject;
import space.util.baseobjectOld.Copyable;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SynchronizedCollection<E> extends DelegatingCollection<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(SynchronizedCollection.class, d -> new SynchronizedCollection(Copyable.copy(d.coll)));
	}
	
	public SynchronizedCollection(Collection<E> coll) {
		super(coll);
	}
	
	@Override
	public synchronized int size() {
		return super.size();
	}
	
	@Override
	public synchronized boolean isEmpty() {
		return super.isEmpty();
	}
	
	@Override
	public synchronized boolean contains(Object o) {
		return super.contains(o);
	}
	
	@Override
	public synchronized Iterator<E> iterator() {
		return super.iterator();
	}
	
	@Override
	public synchronized Object[] toArray() {
		return super.toArray();
	}
	
	@Override
	@SuppressWarnings("SuspiciousToArrayCall")
	public synchronized <T> T[] toArray(T[] a) {
		return super.toArray(a);
	}
	
	@Override
	public synchronized boolean add(E e) {
		return super.add(e);
	}
	
	@Override
	public synchronized boolean remove(Object o) {
		return super.remove(o);
	}
	
	@Override
	public synchronized boolean containsAll(Collection<?> c) {
		return super.containsAll(c);
	}
	
	@Override
	public synchronized boolean addAll(Collection<? extends E> c) {
		return super.addAll(c);
	}
	
	@Override
	public synchronized boolean removeAll(Collection<?> c) {
		return super.removeAll(c);
	}
	
	@Override
	public synchronized boolean retainAll(Collection<?> c) {
		return super.retainAll(c);
	}
	
	@Override
	public synchronized void clear() {
		super.clear();
	}
	
	@Override
	public synchronized boolean removeIf(Predicate<? super E> filter) {
		return super.removeIf(filter);
	}
	
	@Override
	public synchronized void forEach(Consumer<? super E> action) {
		super.forEach(action);
	}
	
	@Override
	public Spliterator<E> spliterator() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public Stream<E> stream() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public Stream<E> parallelStream() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	//Object
	@Override
	public synchronized boolean equals(Object o) {
		return super.equals(o);
	}
	
	@Override
	public synchronized int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("synchronized", coll);
	}
}
