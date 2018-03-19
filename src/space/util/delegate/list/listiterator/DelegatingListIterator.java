package space.util.delegate.list.listiterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * A {@link ListIterator} delegating all calls to it's Field {@link DelegatingListIterator#iterator}, provided by Constructor or set directly.
 */
public class DelegatingListIterator<E> implements ToString, ListIterator<E> {
	
	public ListIterator<E> iterator;
	
	public DelegatingListIterator(ListIterator<E> iterator) {
		this.iterator = iterator;
	}
	
	//methods
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}
	
	@Override
	public E next() {
		return iterator.next();
	}
	
	@Override
	public boolean hasPrevious() {
		return iterator.hasPrevious();
	}
	
	@Override
	public E previous() {
		return iterator.previous();
	}
	
	@Override
	public int nextIndex() {
		return iterator.nextIndex();
	}
	
	@Override
	public int previousIndex() {
		return iterator.previousIndex();
	}
	
	@Override
	public void remove() {
		iterator.remove();
	}
	
	@Override
	public void set(E e) {
		iterator.set(e);
	}
	
	@Override
	public void add(E e) {
		iterator.add(e);
	}
	
	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		iterator.forEachRemaining(action);
	}
	
	//super
	protected void superforEachRemaining(Consumer<? super E> action) {
		ListIterator.super.forEachRemaining(action);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("delegate", iterator);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
