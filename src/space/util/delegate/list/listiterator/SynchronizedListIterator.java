package space.util.delegate.list.listiterator;

import space.util.string.toStringHelper.ToStringHelper;

import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * Synchronizes over the entire {@link ListIterator}. Frequent access on the {@link ListIterator} can be problematic.
 */
public class SynchronizedListIterator<E> extends DelegatingListIterator<E> {
	
	public SynchronizedListIterator(ListIterator<E> iterator) {
		super(iterator);
	}
	
	@Override
	public synchronized boolean hasNext() {
		return super.hasNext();
	}
	
	@Override
	public synchronized E next() {
		return super.next();
	}
	
	@Override
	public synchronized boolean hasPrevious() {
		return super.hasPrevious();
	}
	
	@Override
	public synchronized E previous() {
		return super.previous();
	}
	
	@Override
	public synchronized int nextIndex() {
		return super.nextIndex();
	}
	
	@Override
	public synchronized int previousIndex() {
		return super.previousIndex();
	}
	
	@Override
	public synchronized void remove() {
		super.remove();
	}
	
	@Override
	public synchronized void set(E e) {
		super.set(e);
	}
	
	@Override
	public synchronized void add(E e) {
		super.add(e);
	}
	
	@Override
	public synchronized void forEachRemaining(Consumer<? super E> action) {
		super.forEachRemaining(action);
	}
	
	@Override
	public synchronized int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public synchronized boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("synchronized", iterator);
	}
}
