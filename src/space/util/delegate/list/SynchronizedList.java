package space.util.delegate.list;

import space.util.baseobjectOld.BaseObject;
import space.util.baseobjectOld.Copyable;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class SynchronizedList<E> extends DelegatingList<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(SynchronizedList.class, d -> new SynchronizedList(Copyable.copy(d.list)));
	}
	
	public SynchronizedList(List<E> list) {
		super(list);
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
	public synchronized boolean addAll(int index, Collection<? extends E> c) {
		return super.addAll(index, c);
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
	public synchronized void replaceAll(UnaryOperator<E> operator) {
		super.replaceAll(operator);
	}
	
	@Override
	public synchronized void sort(Comparator<? super E> c) {
		super.sort(c);
	}
	
	@Override
	public synchronized void clear() {
		super.clear();
	}
	
	@Override
	public synchronized boolean equals(Object o) {
		return super.equals(o);
	}
	
	@Override
	public synchronized int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public synchronized E get(int index) {
		return super.get(index);
	}
	
	@Override
	public synchronized E set(int index, E element) {
		return super.set(index, element);
	}
	
	@Override
	public synchronized void add(int index, E element) {
		super.add(index, element);
	}
	
	@Override
	public synchronized E remove(int index) {
		return super.remove(index);
	}
	
	@Override
	public synchronized int indexOf(Object o) {
		return super.indexOf(o);
	}
	
	@Override
	public synchronized int lastIndexOf(Object o) {
		return super.lastIndexOf(o);
	}
	
	@Override
	public synchronized ListIterator<E> listIterator() {
		return super.listIterator();
	}
	
	@Override
	public synchronized ListIterator<E> listIterator(int index) {
		return super.listIterator(index);
	}
	
	@Override
	public synchronized List<E> subList(int fromIndex, int toIndex) {
		return super.subList(fromIndex, toIndex);
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
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("synchronized", this.list);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
