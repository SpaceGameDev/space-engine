package space.util.delegate.list;

import space.util.baseobject.ToString;
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

/**
 * A {@link List} delegating all calls to it's Field {@link DelegatingList#list}, provided by Constructor or set directly.
 */
public class DelegatingList<E> implements List<E>, ToString {
	
	public List<E> list;
	
	public DelegatingList(List<E> list) {
		this.list = list;
	}
	
	//methods
	@Override
	public int size() {
		return list.size();
	}
	
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}
	
	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}
	
	@Override
	public Object[] toArray() {
		return list.toArray();
	}
	
	@Override
	@SuppressWarnings("SuspiciousToArrayCall")
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}
	
	@Override
	public boolean add(E e) {
		return list.add(e);
	}
	
	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return list.addAll(c);
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return list.addAll(index, c);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}
	
	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		list.replaceAll(operator);
	}
	
	@Override
	public void sort(Comparator<? super E> c) {
		list.sort(c);
	}
	
	@Override
	public void clear() {
		list.clear();
	}
	
	@Override
	public E get(int index) {
		return list.get(index);
	}
	
	@Override
	public E set(int index, E element) {
		return list.set(index, element);
	}
	
	@Override
	public void add(int index, E element) {
		list.add(index, element);
	}
	
	@Override
	public E remove(int index) {
		return list.remove(index);
	}
	
	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}
	
	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return list.listIterator();
	}
	
	@Override
	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}
	
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
	
	@Override
	public Spliterator<E> spliterator() {
		return list.spliterator();
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return list.removeIf(filter);
	}
	
	@Override
	public Stream<E> stream() {
		return list.stream();
	}
	
	@Override
	public Stream<E> parallelStream() {
		return list.parallelStream();
	}
	
	@Override
	public void forEach(Consumer<? super E> action) {
		list.forEach(action);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("delegate", this.list);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
