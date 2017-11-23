package space.util.delegate.list.listiterator;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SupplierListIterator<E> implements ToString, ListIterator<E> {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(SupplierListIterator.class, d -> new SupplierListIterator(Copyable.copy(d.iterator)));
	}
	
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
	public int hashCode() {
		return iterator.get().hashCode();
	}
	
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object obj) {
		return iterator.get().equals(obj);
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
