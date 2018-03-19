package space.util.delegate.list;

import space.util.delegate.iterator.UnmodifiableIterator;
import space.util.delegate.list.listiterator.UnmodifiableListIterator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * The {@link UnmodifiableList} makes the {@link List} unmodifiable.
 */
public class UnmodifiableList<E> extends DelegatingList<E> {
	
	public UnmodifiableList(List<E> list) {
		super(list);
	}
	
	@Override
	public Iterator<E> iterator() {
		return new UnmodifiableIterator<>(list.iterator());
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
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return new UnmodifiableListIterator<>(list.listIterator());
	}
	
	@Override
	public ListIterator<E> listIterator(int index) {
		return new UnmodifiableListIterator<>(list.listIterator());
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("list", this.list);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
