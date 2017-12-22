package space.util.delegate.collection;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.delegate.iterator.Iteratorable;
import space.util.delegate.iterator.ReferenceIterator;
import space.util.delegate.util.ReferenceUtil;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Remaps all Entries to a {@link Reference} of type E. These References are created by the {@link ReferenceCollection#refCreator Reference Creator} supplied with the Constructor or directly set.
 */
public class ReferenceCollection<E> implements ToString, Collection<E> {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(ReferenceCollection.class, d -> new ReferenceCollection(Copyable.copy(d.coll), d.refCreator));
	}
	
	public Collection<Reference<? extends E>> coll;
	public Function<E, ? extends Reference<? extends E>> refCreator;
	
	public ReferenceCollection(Collection<Reference<? extends E>> coll) {
		this(coll, ReferenceUtil.defRefCreator());
	}
	
	public ReferenceCollection(Collection<Reference<? extends E>> coll, Function<E, ? extends Reference<? extends E>> refCreator) {
		this.coll = coll;
		this.refCreator = refCreator;
	}
	
	@Override
	public int size() {
		return coll.size();
	}
	
	@Override
	public boolean isEmpty() {
		return coll.isEmpty();
	}
	
	@Override
	public boolean contains(Object o) {
		for (Reference<? extends E> e : coll)
			if (e != null && Objects.equals(e.get(), o))
				return true;
		return false;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ReferenceIterator<>(coll.iterator());
	}
	
	@Override
	public Object[] toArray() {
		return coll.toArray();
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		int l = a.length;
		Reference[] org = coll.toArray(new Reference[l]);
		for (int i = 0; i < l; i++)
			//noinspection unchecked
			a[i] = (T) ReferenceUtil.getSafe(org[i]);
		return a;
	}
	
	@Override
	public boolean add(E e) {
		return coll.add(refCreator.apply(e));
	}
	
	@Override
	public boolean remove(Object o) {
		Iterator<Reference<? extends E>> iter = coll.iterator();
		for (Reference<? extends E> e : Iteratorable.toIteratorable(iter)) {
			if (e != null && Objects.equals(e.get(), o)) {
				iter.remove();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		int ret = c.size();
		for (Reference<? extends E> e : coll) {
			for (Object o : c) {
				if (e != null && Objects.equals(e.get(), o)) {
					ret--;
					break;
				}
			}
		}
		return ret == 0;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean ret = false;
		for (E e : c)
			if (coll.add(refCreator.apply(e)))
				ret = true;
		return ret;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for (Object o : c)
			if (remove(o))
				ret = true;
		return ret;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean ret = false;
		Iterator<Reference<? extends E>> iter = coll.iterator();
		for (Reference<? extends E> e : Iteratorable.toIteratorable(iter)) {
			if (!c.contains(e)) {
				iter.remove();
				ret = true;
			}
		}
		return ret;
	}
	
	@Override
	public void clear() {
		coll.clear();
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return coll.removeIf(ref -> filter.test(ReferenceUtil.getSafe(ref)));
	}
	
	@Override
	public void forEach(Consumer<? super E> action) {
		coll.forEach(ref -> action.accept(ReferenceUtil.getSafe(ref)));
	}
	
	@Override
	public Spliterator<E> spliterator() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public Stream<E> stream() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public Stream<E> parallelStream() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	//Object
	@Override
	public int hashCode() {
		return coll.hashCode();
	}
	
	@Override
	@SuppressWarnings("SimplifiableIfStatement")
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Collection<?>))
			return false;
		
		return containsAll((Collection<?>) obj);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("coll", this.coll);
		tsh.add("refCreator", this.refCreator);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
