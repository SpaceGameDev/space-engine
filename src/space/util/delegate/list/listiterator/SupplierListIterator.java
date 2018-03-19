package space.util.delegate.list.listiterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A {@link ListIterator} delegating all calls to it's Field {@link SupplierListIterator#iterator}, which is an {@link Supplier} of Type {@link ListIterator}, allowing for unique usages. The {@link Supplier} is provided by Constructor or set directly.
 */
public class SupplierListIterator<E> implements ToString, ListIterator<E> {
	
	public Supplier<ListIterator<E>> iterator;
	
	public SupplierListIterator(Supplier<ListIterator<E>> iterator) {
		this.iterator = iterator;
	}
	
	@Override
	public boolean hasNext() {
		return iterator.get().hasNext();
	}
	
	@Override
	public E next() {
		return iterator.get().next();
	}
	
	@Override
	public boolean hasPrevious() {
		return iterator.get().hasPrevious();
	}
	
	@Override
	public E previous() {
		return iterator.get().previous();
	}
	
	@Override
	public int nextIndex() {
		return iterator.get().nextIndex();
	}
	
	@Override
	public int previousIndex() {
		return iterator.get().previousIndex();
	}
	
	@Override
	public void remove() {
		iterator.get().remove();
	}
	
	@Override
	public void set(E e) {
		iterator.get().set(e);
	}
	
	@Override
	public void add(E e) {
		iterator.get().add(e);
	}
	
	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		iterator.get().forEachRemaining(action);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("supplier", iterator);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
