package space.util.delegate.collection;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.delegate.impl.ArrayIterable;
import space.util.delegate.iterator.MergingIterator;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;
import space.util.string.toStringHelper.objects.TSHObjects.TSHObjectsInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A Collection merging multiple Collections.
 * Use the createWithAddCollection()-Method or the setAddColl()-Method to redirect all add and addAll calls to the addColl.
 * If no addColl is specified, the Methods add() and addAll() throw {@link UnsupportedOperationException}.
 */
public class MergingCollection<E> implements BaseObject, Collection<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(MergingCollection.class, d -> MergingCollection.createWithAddCollection(d.addColl, Copyable.copy(d.collections)));
	}
	
	public Collection<E> addColl;
	public Collection<Collection<E>> collections;
	
	@SafeVarargs
	public MergingCollection(Collection<E>... collections) {
		this(new ArrayList<>(new ArrayIterable<>(collections)));
	}
	
	public MergingCollection(Collection<Collection<E>> collections) {
		this.collections = collections;
	}
	
	@SafeVarargs
	public static <E> MergingCollection<E> createWithAddCollection(Collection<E> addColl, Collection<E>... collections) {
		return createWithAddCollection(addColl, new ArrayList<>(new ArrayIterable<>(collections)));
	}
	
	public static <E> MergingCollection<E> createWithAddCollection(Collection<E> addColl, Collection<Collection<E>> collections) {
		MergingCollection<E> ret = new MergingCollection<E>(collections);
		ret.setAddColl(addColl);
		return ret;
	}
	
	public void setAddColl(Collection<E> addColl) {
		this.addColl = addColl;
	}
	
	public void removeAddColl() {
		addColl = null;
	}
	
	//size
	@Override
	public int size() {
		int cnt = 0;
		for (Collection<E> e : collections)
			cnt += e.size();
		return cnt;
	}
	
	@Override
	public boolean isEmpty() {
		return size() != 0;
	}
	
	//access
	@Override
	public boolean contains(Object o) {
		for (Collection<E> e : collections)
			if (e.contains(o))
				return true;
		return false;
	}
	
	@Override
	public boolean add(E e) {
		if (addColl == null)
			throw new UnsupportedOperationException();
		return addColl.add(e);
	}
	
	@Override
	public boolean remove(Object o) {
		return false;
	}
	
	//all
	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c)
			if (!contains(o))
				return false;
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (addColl == null)
			throw new UnsupportedOperationException();
		return addColl.addAll(c);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for (Collection<E> e : collections)
			if (e.removeAll(c))
				ret = true;
		return ret;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean ret = false;
		for (Collection<E> e : collections)
			if (e.retainAll(c))
				ret = true;
		return ret;
	}
	
	//other
	@Override
	public void clear() {
		for (Collection<E> e : collections)
			e.clear();
	}
	
	@Override
	public Object[] toArray() {
		return toArray(new Object[size()]);
	}
	
	@Override
	@SuppressWarnings("SuspiciousSystemArraycopy")
	public <T> T[] toArray(T[] a) {
		int pos = 0;
		for (Collection<E> e : collections) {
			Object[] o = e.toArray();
			int l = o.length;
			System.arraycopy(o, 0, a, pos, l);
			pos += l;
		}
		return a;
	}
	
	@Override
	public Iterator<E> iterator() {
		return MergingIterator.fromIterable(collections);
	}
	
	//Object
	
	@Override
	public boolean equals(Object o) {
		return this == o || collections.equals(o instanceof MergingCollection ? ((MergingCollection<?>) o).collections : o);
	}
	
	@Override
	public int hashCode() {
		return collections.hashCode();
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("addColl", this.addColl);
		tsh.add("collections", this.collections);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
