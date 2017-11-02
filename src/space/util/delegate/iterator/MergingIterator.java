package space.util.delegate.iterator;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;
import space.util.string.toStringHelper.objects.TSHObjects.TSHObjectsInstance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MergingIterator<T> implements BaseObject, Iteratorable<T> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(MergingIterator.class, d -> new MergingIterator(Copyable.copy(d.iterators)));
	}
	
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
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof MergingIterator))
			return false;
		
		MergingIterator<?> that = (MergingIterator<?>) o;
		
		if (next != that.next)
			return false;
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		if (!Arrays.equals(iterators, that.iterators))
			return false;
		return currIter != null ? currIter.equals(that.currIter) : that.currIter == null;
	}
	
	@Override
	public int hashCode() {
		int result = Arrays.hashCode(iterators);
		result = 31 * result + (currIter != null ? currIter.hashCode() : 0);
		result = 31 * result + next;
		return result;
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("iterators", this.iterators);
		tsh.add("currIter", this.currIter);
		tsh.add("next", this.next);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
