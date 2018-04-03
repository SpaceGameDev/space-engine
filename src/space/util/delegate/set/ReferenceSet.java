package space.util.delegate.set;

import space.util.delegate.collection.ReferenceCollection;

import java.lang.ref.Reference;
import java.util.Set;
import java.util.function.Function;

public class ReferenceSet<E> extends ReferenceCollection<E> implements Set<E> {
	
	public ReferenceSet(Set<Reference<? extends E>> coll, Function<E, Reference<? extends E>> refCreator) {
		super(coll, refCreator);
	}
}
