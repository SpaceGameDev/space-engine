package space.util.delegate.iterator;

import space.util.string.toStringHelper.ToStringHelper;

import java.util.Iterator;

/**
 * The {@link UnmodifiableIterator} makes the {@link Iterator} unmodifiable.
 */
public class UnmodifiableIterator<E> extends DelegatingIterator<E> {
	
	public UnmodifiableIterator(Iterator<E> i) {
		super(i);
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("synchronized", iter);
	}
}
