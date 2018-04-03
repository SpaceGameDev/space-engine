package space.util.delegate.set;

import space.util.delegate.collection.DelegatingCollection;

import java.util.Set;

public class DelegatingSet<E> extends DelegatingCollection<E> implements Set<E> {
	
	public DelegatingSet(Set<E> coll) {
		super(coll);
	}
}
