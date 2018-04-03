package space.util.delegate.set;

import space.util.delegate.collection.UnmodifiableCollection;

import java.util.Set;

public class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E> {
	
	public UnmodifiableSet(Set<E> coll) {
		super(coll);
	}
}
