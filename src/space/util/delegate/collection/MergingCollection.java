package space.util.delegate.collection;

import space.util.baseobject.ToString;
import space.util.delegate.impl.ArrayCollection;
import space.util.delegate.iterator.MergingIterator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Merges multiple {@link Collection} to one {@link Collection}.
 * <p>
 * Use the {@link MergingCollection#createWithAddCollection(Collection, Collection[])} or the {@link MergingCollection#setAddColl(Collection)} to redirect all {@link Collection#add} and {@link Collection#addAll(Collection)} calls to the supplied {@link MergingCollection#addColl}.
 * If no {@link MergingCollection#addColl} is specified, the Methods {@link Collection#add} and {@link Collection#addAll(Collection)} throw an {@link UnsupportedOperationException}.
 */
public class MergingCollection<E> implements ToString, Collection<E> {
	
	public AddCollection<E> addColl;
	public Collection<Collection<E>> collections;
	
	@SafeVarargs
	public MergingCollection(Collection<E>... collections) {
		this(new ArrayList<>(new ArrayCollection<>(collections)));
	}
	
	public MergingCollection(Collection<Collection<E>> collections) {
		this.collections = collections;
	}
	
	//createWithAddCollection
	@SafeVarargs
	public static <E> MergingCollection<E> createWithAddCollection(Collection<E> addColl, Collection<E>... collections) {
		return createWithAddCollection(addCollectionFromCollection(addColl), collections);
	}
	
	public static <E> MergingCollection<E> createWithAddCollection(Collection<E> addColl, Collection<Collection<E>> collections) {
		return createWithAddCollection(addCollectionFromCollection(addColl), collections);
	}
	
	@SafeVarargs
	public static <E> MergingCollection<E> createWithAddCollection(AddCollection<E> addColl, Collection<E>... collections) {
		return createWithAddCollection(addColl, new ArrayList<>(new ArrayCollection<>(collections)));
	}
	
	public static <E> MergingCollection<E> createWithAddCollection(AddCollection<E> addColl, Collection<Collection<E>> collections) {
		MergingCollection<E> ret = new MergingCollection<>(collections);
		ret.setAddColl(addColl);
		return ret;
	}
	
	//setAddColl
	public void setAddColl(AddCollection<E> addColl) {
		this.addColl = addColl;
	}
	
	public void setAddColl(Collection<E> addColl) {
		setAddColl(addCollectionFromCollection(addColl));
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
		for (Collection<E> e : collections)
			if (!e.isEmpty())
				return false;
		return true;
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
		for (Collection<E> e : collections)
			if (!e.containsAll(c))
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
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("addColl", this.addColl);
		tsh.add("collections", this.collections);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	@FunctionalInterface
	public interface AddCollection<E> {
		
		boolean add(E e);
		
		default boolean addAll(Collection<? extends E> c) {
			boolean ret = false;
			for (E e : c)
				if (add(e))
					ret = true;
			return ret;
		}
	}
	
	public static <E> AddCollection<E> addCollectionFromCollection(Collection<E> coll) {
		return new AddCollection<E>() {
			@Override
			public boolean add(E e) {
				return coll.add(e);
			}
			
			@Override
			public boolean addAll(Collection<? extends E> c) {
				return coll.addAll(c);
			}
		};
	}
}
