package space.util.delegate.set;

import space.util.delegate.collection.UnmodifiableCollection;

import java.util.Set;

/**
 * The {@link UnmodifiableSet} makes the {@link Set} unmodifiable.
 */
public class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E> {
	
	public UnmodifiableSet(Set<E> coll) {
		super(coll);
	}
}
