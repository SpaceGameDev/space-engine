package space.util.delegate.iterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * A {@link Iterator} delegating all calls to it's Field {@link DelegatingIterator#i}, provided by Constructor or set directly.
 */
public class DelegatingIterator<E> implements ToString, Iteratorable<E> {
	
	public Iterator<E> i;
	
	public DelegatingIterator(Iterator<E> i) {
		this.i = i;
	}
	
	//methods
	@Override
	public boolean hasNext() {
		return i.hasNext();
	}
	
	@Override
	public E next() {
		return i.next();
	}
	
	@Override
	public void remove() {
		i.remove();
	}
	
	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		i.forEachRemaining(action);
	}
	
	//toString
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("delegate", i);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
