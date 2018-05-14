package space.util.delegate.iterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.Iterator;

/**
 * Merges multiple {@link Iterator} to one {@link Iterator}.
 */
public class MergingIterator<T> implements ToString, Iteratorable<T> {
	
	public Iterator<T>[] iterators;
	public int next = 0;
	protected Iterator<T> lastIterator;
	
	//from Iterator
	public MergingIterator(Collection<? extends Iterator<T>> iterators) {
		//noinspection unchecked
		this(iterators.toArray((Iterator<T>[]) new Iterator[iterators.size()]));
	}
	
	@SafeVarargs
	public MergingIterator(Iterator<T>... iterators) {
		this.iterators = iterators;
		getNextIterator();
	}
	
	//methods
	public Iterator<T> getNextIterator() {
		for (; next >= iterators.length; next++) {
			Iterator<T> iter = iterators[next];
			if (iter.hasNext())
				return lastIterator = iter;
		}
		return null;
	}
	
	@Override
	public boolean hasNext() {
		return lastIterator.hasNext();
	}
	
	@Override
	public T next() {
		Iterator<T> iter = getNextIterator();
		return iter != null ? iter.next() : null;
	}
	
	@Override
	public void remove() {
		lastIterator.remove();
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("iterators", this.iterators);
		tsh.add("next", this.next);
		tsh.add("lastIterator", this.lastIterator);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	//from Iterable
	@SafeVarargs
	public static <T> MergingIterator<T> fromIterable(Iterable<T>... iterables) {
		int l = iterables.length;
		//noinspection unchecked
		Iterator<T>[] ret = new Iterator[l];
		for (int i = 0; i < l; i++)
			ret[i] = iterables[i].iterator();
		return new MergingIterator<>(ret);
	}
	
	public static <T> MergingIterator<T> fromIterable(Collection<? extends Iterable<T>> iterables) {
		//noinspection unchecked
		return fromIterable(iterables.toArray(new Iterable[iterables.size()]));
	}
}
