package space.util.delegate.iterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * An {@link Iterator} delegating all calls to it's Field {@link DelegatingIterator#iter}, supplied with the Constructor.
 */
public class DelegatingIterator<E> implements ToString, Iteratorable<E> {
	
	public Iterator<E> iter;
	
	public DelegatingIterator(Iterator<E> iter) {
		this.iter = iter;
	}
	
	//methods
	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}
	
	@Override
	public E next() {
		return iter.next();
	}
	
	@Override
	public void remove() {
		iter.remove();
	}
	
	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		iter.forEachRemaining(action);
	}
	
	//toString
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("delegate", iter);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
