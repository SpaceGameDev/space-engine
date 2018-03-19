package space.util.delegate.list.listiterator;

import space.util.string.toStringHelper.ToStringHelper;

import java.util.ListIterator;

/**
 * The {@link UnmodifiableListIterator} makes the {@link ListIterator} unmodifiable.
 */
public class UnmodifiableListIterator<E> extends DelegatingListIterator<E> {
	
	public UnmodifiableListIterator(ListIterator<E> iterator) {
		super(iterator);
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void set(E e) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void add(E e) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("unmodifiable", iterator);
	}
}
