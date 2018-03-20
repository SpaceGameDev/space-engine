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
	
	public Iterator<T> getNextIterator() {
		if (next >= iterators.length)
			return null;
		
		Iterator<T> iter = iterators[next];
		while (iter != null && !iter.hasNext())
			iter = iterators[next++];
		return iter;
	}
	
	@Override
	public boolean hasNext() {
		Iterator<T> iter = getLastIterator();
		return iter != null && iter.hasNext();
	}
	
	@Override
	public T next() {
		Iterator<T> iter = getNextIterator();
		return iter != null ? iter.next() : null;
	}
	
	@Override
	public void remove() {
		Iterator<T> iter = getLastIterator();
		if (iter != null)
			iter.remove();
	}
	
	@Override
	@SuppressWarnings("TypeParameterHidesVisibleType")
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("iterators", this.iterators);
		tsh.add("next", this.next);
		
		if (next >= this.iterators.length)
			tsh.add("iterators[next]", "Overflow");
		else
			tsh.add("iterators[next]", this.iterators[next]);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
