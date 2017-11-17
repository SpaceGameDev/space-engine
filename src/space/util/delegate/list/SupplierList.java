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
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class SupplierList<E> implements BaseObject, List<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(SupplierList.class, d -> new SupplierList(Copyable.copy(d.list)));
	}
	
	public Supplier<List<E>> list;
	
	public SupplierList(Supplier<List<E>> list) {
		this.list = list;
	}
	
	@Override
	public int size() {
		return list.get().size();
	}
	
	@Override
	public boolean isEmpty() {
		return list.get().isEmpty();
	}
	
	@Override
	public boolean contains(Object o) {
		return list.get().contains(o);
	}
	
	@Override
	public Iterator<E> iterator() {
		return list.get().iterator();
	}
	
	@Override
	public Object[] toArray() {
		return list.get().toArray();
	}
	
	@Override
	@SuppressWarnings("SuspiciousToArrayCall")
	public <T> T[] toArray(T[] a) {
		return list.get().toArray(a);
	}
	
	@Override
	public boolean add(E e) {
		return list.get().add(e);
	}
	
	@Override
	public boolean remove(Object o) {
		return list.get().remove(o);
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return list.get().containsAll(c);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return list.get().addAll(c);
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return list.get().addAll(index, c);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return list.get().removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return list.get().retainAll(c);
	}
	
	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		list.get().replaceAll(operator);
	}
	
	@Override
	public void sort(Comparator<? super E> c) {
		list.get().sort(c);
	}
	
	@Override
	public void clear() {
		list.get().clear();
	}
	
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object o) {
		return list.get().equals(o);
	}
	
	@Override
	public int hashCode() {
		return list.get().hashCode();
	}
	
	@Override
	public E get(int index) {
		return list.get().get(index);
	}
	
	@Override
	public E set(int index, E element) {
		return list.get().set(index, element);
	}
	
	@Override
	public void add(int index, E element) {
		list.get().add(index, element);
	}
	
	@Override
	public E remove(int index) {
		return list.get().remove(index);
	}
	
	@Override
	public int indexOf(Object o) {
		return list.get().indexOf(o);
	}
	
	@Override
	public int lastIndexOf(Object o) {
		return list.get().lastIndexOf(o);
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return list.get().listIterator();
	}
	
	@Override
	public ListIterator<E> listIterator(int index) {
		return list.get().listIterator(index);
	}
	
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return list.get().subList(fromIndex, toIndex);
	}
	
	@Override
	public Spliterator<E> spliterator() {
		return list.get().spliterator();
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return list.get().removeIf(filter);
	}
	
	@Override
	public Stream<E> stream() {
		return list.get().stream();
	}
	
	@Override
	public Stream<E> parallelStream() {
		return list.get().parallelStream();
	}
	
	@Override
	public void forEach(Consumer<? super E> action) {
		list.get().forEach(action);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("supplier", this.list);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
