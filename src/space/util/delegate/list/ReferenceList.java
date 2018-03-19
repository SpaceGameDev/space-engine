package space.util.delegate.list;

import space.util.baseobject.ToString;
import space.util.delegate.iterator.Iteratorable;
import space.util.delegate.iterator.ReferenceIterator;
import space.util.delegate.list.listiterator.ReferenceListIterator;
import space.util.delegate.util.ReferenceUtil;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Remaps all Entries to a {@link Reference} of type E. These References are created by the {@link ReferenceList#refCreator Reference Creator} supplied with the Constructor or directly set.
 */
public class ReferenceList<E> implements ToString, List<E> {
	
	public List<Reference<? extends E>> list;
	public Function<E, ? extends Reference<? extends E>> refCreator;
	
	public ReferenceList(List<Reference<? extends E>> list) {
		this(list, ReferenceUtil.defRefCreator());
	}
	
	public ReferenceList(List<Reference<? extends E>> list, Function<E, ? extends Reference<? extends E>> refCreator) {
		this.list = list;
		this.refCreator = refCreator;
	}
	
	@Override
	public int size() {
		return list.size();
	}
	
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	@Override
	public boolean contains(Object o) {
		for (Reference<? extends E> e : list)
			if (e != null && e.get() == o)
				return true;
		return false;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ReferenceIterator<>(list.iterator());
	}
	
	@Override
	public Object[] toArray() {
		return list.toArray();
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		int l = a.length;
		Reference[] org = list.toArray(new Reference[l]);
		for (int i = 0; i < l; i++)
			//noinspection unchecked
			a[i] = (T) ReferenceUtil.getSafe(org[i]);
		return a;
	}
	
	@Override
	public boolean add(E e) {
		return list.add(refCreator.apply(e));
	}
	
	@Override
	public boolean remove(Object o) {
		Iterator<Reference<? extends E>> iter = list.iterator();
		for (Reference<? extends E> e : Iteratorable.toIteratorable(iter)) {
			if (e != null && e.get() == o) {
				iter.remove();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		int ret = c.size();
		for (Reference<? extends E> e : list) {
			for (Object o : c) {
				if (e != null && e.get() == o) {
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
			if (list.add(refCreator.apply(e)))
				ret = true;
		return ret;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean ret = false;
		int i = index;
		for (E e : c) {
			if (i > 0) {
				i--;
				continue;
			}
			if (list.add(refCreator.apply(e)))
				ret = true;
		}
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
		Iterator<Reference<? extends E>> iter = list.iterator();
		for (Reference<? extends E> e : Iteratorable.toIteratorable(iter)) {
			if (!c.contains(e)) {
				iter.remove();
				ret = true;
			}
		}
		return ret;
	}
	
	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		for (int i = 0; i < list.size(); i++)
			set(i, operator.apply(get(i)));
	}
	
	@Override
	public void sort(Comparator<? super E> c) {
		list.sort((o1, o2) -> c.compare(ReferenceUtil.getSafe(o1), ReferenceUtil.getSafe(o2)));
	}
	
	@Override
	public void clear() {
		list.clear();
	}
	
	@Override
	public E get(int index) {
		return ReferenceUtil.getSafe(list.get(index));
	}
	
	@Override
	public E set(int index, E element) {
		return ReferenceUtil.getSafe(list.set(index, refCreator.apply(element)));
	}
	
	@Override
	public void add(int index, E element) {
		list.add(index, refCreator.apply(element));
	}
	
	@Override
	public E remove(int index) {
		return ReferenceUtil.getSafe(list.remove(index));
	}
	
	@Override
	public int indexOf(Object o) {
		int i = 0;
		for (Reference<? extends E> e : list) {
			if (e != null && e == o)
				return i;
			i++;
		}
		return -1;
	}
	
	@Override
	public int lastIndexOf(Object o) {
		int i = 0;
		int ret = -1;
		for (Reference<? extends E> e : list) {
			if (e != null && e == o)
				ret = i;
			i++;
		}
		return ret;
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return new ReferenceListIterator<>(list.listIterator(), refCreator);
	}
	
	@Override
	public ListIterator<E> listIterator(int index) {
		return new ReferenceListIterator<>(list.listIterator(index), refCreator);
	}
	
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return new ReferenceList<>(list.subList(fromIndex, toIndex), refCreator);
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return list.removeIf(ref -> filter.test(ReferenceUtil.getSafe(ref)));
	}
	
	@Override
	public void forEach(Consumer<? super E> action) {
		list.forEach(ref -> action.accept(ReferenceUtil.getSafe(ref)));
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
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("list", this.list);
		tsh.add("refCreator", this.refCreator);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
