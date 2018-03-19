package space.util.delegate.iterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Merges multiple {@link Iterator} to one {@link Iterator}.
 */
public class MergingIterator<T> implements ToString, Iteratorable<T> {
	
	public Iterator<T>[] iterators;
	public Iterator<T> currIter;
	public int next;
	
	//Iterable
	public MergingIterator(Collection<? extends Iterator<T>> iterators) {
		//noinspection unchecked
		this(iterators.toArray((Iterator<T>[]) new Iterator[iterators.size()]));
	}
	
	@SafeVarargs
	public MergingIterator(Iterator<T>... iterators) {
		this.iterators = iterators;
	}
	
	//Iterable
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
	
	@Override
	public boolean hasNext() {
		return findNext() && currIter.hasNext();
	}
	
	@Override
	public T next() {
		if (!findNext())
			throw new NoSuchElementException();
		return currIter.next();
	}
	
	public boolean findNext() {
		Iterator<T> curr = currIter;
		while (curr == null || !curr.hasNext()) {
			if (next >= iterators.length)
				return false;
			curr = iterators[next++];
		}
		currIter = curr;
		return true;
	}
	
	@Override
	public void remove() {
		if (currIter == null)
			throw new IllegalStateException();
		currIter.remove();
	}
	
	@Override
	@SuppressWarnings("TypeParameterHidesVisibleType")
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("iterators", this.iterators);
		tsh.add("currIter", this.currIter);
		tsh.add("next", this.next);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
