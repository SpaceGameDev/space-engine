package space.util.delegate.iterator;

import java.util.Iterator;
import java.util.function.Function;

public class ConvertingIterator<F, T> implements Iterator<T> {
	
	public Iterator<F> iter;
	public Function<F, T> remap;
	
	public ConvertingIterator(Iterator<F> iter, Function<F, T> remap) {
		this.iter = iter;
		this.remap = remap;
	}
	
	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}
	
	@Override
	public T next() {
		return remap.apply(iter.next());
	}
	
	@Override
	public void remove() {
		iter.remove();
	}
}
