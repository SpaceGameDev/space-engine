package space.util.delegate.set;

import space.util.delegate.collection.ModificationAwareCollection;

import java.util.Set;

/**
 * The {@link ModificationAwareSet} will call the {@link ModificationAwareSet#onModification} {@link Runnable} when the {@link Set} is modified.
 */
public class ModificationAwareSet<E> extends ModificationAwareCollection<E> implements Set<E> {
	
	public ModificationAwareSet(Set<E> coll, Runnable onModification) {
		super(coll, onModification);
	}
}
