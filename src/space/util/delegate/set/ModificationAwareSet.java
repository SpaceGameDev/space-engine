package space.util.delegate.set;

import space.util.delegate.collection.ModificationAwareCollection;

import java.util.Set;

public class ModificationAwareSet<E> extends ModificationAwareCollection<E> implements Set<E> {
	
	public ModificationAwareSet(Set<E> coll, Runnable onModification) {
		super(coll, onModification);
	}
}
