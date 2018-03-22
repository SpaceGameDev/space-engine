package space.util.delegate.list;

import space.util.baseobject.ToString;
import space.util.delegate.collection.ConvertingCollection;
import space.util.delegate.iterator.ConvertingIterator;
import space.util.delegate.list.listiterator.ConvertingListIterator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public abstract class ConvertingList<F, T> implements List<T>, ToString {
	
	public List<F> list;
	
	public ConvertingList(List<F> list) {
		this.list = list;
	}
	
	@Override
	@SuppressWarnings("TypeParameterHidesVisibleType")
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("list", this.list);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static <F, T> OneDirectionalUnmodifiable<F, T> createConvertingOneDirectionalUnmodifiable(List<F> list, Function<? super F, ? extends T> remap) {
		return new OneDirectionalUnmodifiable<>(list, remap);
	}
	
	public static <F, T> BiDirectionalUnmodifiable<F, T> createConvertingBiDirectionalUnmodifiable(List<F> list, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverse) {
		return new BiDirectionalUnmodifiable<>(list, remap, reverse);
	}
	
	public static <F, T> BiDirectionalSparse<F, T> createConvertingBiDirectionalSparse(List<F> list, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverseSparse) {
		return new BiDirectionalSparse<>(list, remap, reverseSparse);
	}
	
	public static <F, T> BiDirectional<F, T> createConvertingBiDirectional(List<F> list, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverse) {
		return new BiDirectional<>(list, remap, reverse);
	}
	
	public static <F, T> BiDirectional<F, T> createConvertingBiDirectional(List<F> list, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverseSparse, Function<? super T, ? extends F> reverse) {
		return new BiDirectional<>(list, remap, reverseSparse, reverse);
	}
	
	public static class OneDirectionalUnmodifiable<F, T> extends ConvertingList<F, T> {
		
		public Function<? super F, ? extends T> remap;
		
		public OneDirectionalUnmodifiable(List<F> list, Function<? super F, ? extends T> remap) {
			super(list);
			this.remap = remap;
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
			for (F f : list)
				if (Objects.equals(remap.apply(f), o))
					return true;
			return false;
		}
		
		@Override
		public Iterator<T> iterator() {
			return ConvertingIterator.createConverterOneDirectional(list.iterator(), remap);
		}
		
		@Override
		public Object[] toArray() {
			//noinspection unchecked
			F[] org = (F[]) list.toArray();
			Object[] ret = new Object[org.length];
			for (int i = 0; i < org.length; i++)
				ret[i] = remap.apply(org[i]);
			return ret;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <T1> T1[] toArray(T1[] a) {
			F[] org = (F[]) list.toArray();
			if (a.length < org.length) {
				//new instance
				T1[] ret = (T1[]) Array.newInstance(a.getClass(), org.length);
				for (int i = 0; i < org.length; i++)
					ret[i] = (T1) remap.apply(org[i]);
				return ret;
			}
			
			//use existing
			for (int i = 0; i < org.length; i++)
				a[i] = (T1) remap.apply(org[i]);
			a[org.length] = null;
			return a;
		}
		
		//modify methods
		@Override
		public boolean add(T t) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsAll(Collection<?> c) {
			return list.containsAll(ConvertingCollection.createConvertingOneDirectionalUnmodifiable((Collection<F>) c, remap));
		}
		
		@Override
		public boolean addAll(Collection<? extends T> c) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean addAll(int index, Collection<? extends T> c) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void replaceAll(UnaryOperator<T> operator) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void sort(Comparator<? super T> c) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void clear() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public T get(int index) {
			return remap.apply(list.get(index));
		}
		
		@Override
		public T set(int index, T element) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void add(int index, T element) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public T remove(int index) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public int indexOf(Object o) {
			Iterator<F> iter = list.iterator();
			for (int i = 0; iter.hasNext(); i++)
				if (Objects.equals(iter.next(), o))
					return i;
			return -1;
		}
		
		@Override
		public int lastIndexOf(Object o) {
			for (int i = list.size() - 1; i >= 0; i--)
				if (Objects.equals(list.get(i), o))
					return i;
			return -1;
		}
		
		@Override
		public ListIterator<T> listIterator() {
			return ConvertingListIterator.createConvertingOneDirectionalUnmodifiable(list.listIterator(), remap);
		}
		
		@Override
		public ListIterator<T> listIterator(int index) {
			return ConvertingListIterator.createConvertingOneDirectionalUnmodifiable(list.listIterator(index), remap);
		}
		
		@Override
		public List<T> subList(int fromIndex, int toIndex) {
			return ConvertingList.createConvertingOneDirectionalUnmodifiable(list.subList(fromIndex, toIndex), remap);
		}
		
		@Override
		public boolean removeIf(Predicate<? super T> filter) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void forEach(Consumer<? super T> action) {
			list.forEach(f -> action.accept(remap.apply(f)));
		}
	}
	
	public static class BiDirectionalUnmodifiable<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<? super T, ? extends F> reverse;
		
		public BiDirectionalUnmodifiable(List<F> list, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverse) {
			super(list, remap);
			this.reverse = reverse;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean contains(Object o) {
			return list.contains(reverse.apply((T) o));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsAll(Collection<?> c) {
			return list.containsAll(ConvertingCollection.createConvertingBiDirectionalUnmodifiable((Collection<F>) c, remap, reverse));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public int indexOf(Object o) {
			return list.indexOf(reverse.apply((T) o));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public int lastIndexOf(Object o) {
			return list.lastIndexOf(reverse.apply((T) o));
		}
	}
	
	public static class BiDirectionalSparse<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<? super T, ? extends F> reverseSparse;
		
		public BiDirectionalSparse(List<F> list, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverseSparse) {
			super(list, remap);
			this.reverseSparse = reverseSparse;
		}
		
		@Override
		public boolean add(T t) {
			return list.add(reverseSparse.apply(t));
		}
		
		@Override
		public boolean remove(Object o) {
			ListIterator<F> iter = list.listIterator();
			while (iter.hasNext()) {
				if (Objects.equals(remap.apply(iter.next()), o)) {
					iter.remove();
					return true;
				}
			}
			return false;
		}
		
		@Override
		public boolean addAll(Collection<? extends T> c) {
			return list.addAll(ConvertingCollection.createConvertingOneDirectionalUnmodifiable(c, reverseSparse));
		}
		
		@Override
		public boolean addAll(int index, Collection<? extends T> c) {
			return list.addAll(index, ConvertingCollection.createConvertingOneDirectionalUnmodifiable(c, reverseSparse));
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			boolean mod = false;
			Iterator<F> iter = list.iterator();
			while (iter.hasNext()) {
				T t = remap.apply(iter.next());
				if (c.contains(t)) {
					iter.remove();
					mod = true;
				}
			}
			return mod;
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			boolean mod = false;
			Iterator<F> iter = list.iterator();
			while (iter.hasNext()) {
				T t = remap.apply(iter.next());
				if (!c.contains(t)) {
					iter.remove();
					mod = true;
				}
			}
			return mod;
		}
		
		@Override
		public void replaceAll(UnaryOperator<T> operator) {
			list.replaceAll(f1 -> {
				T t1 = remap.apply(f1);
				T t2 = operator.apply(t1);
				return t1 == t2 ? f1 : reverseSparse.apply(t2);
			});
		}
		
		@Override
		public void sort(Comparator<? super T> c) {
			list.sort((o1, o2) -> c.compare(remap.apply(o1), remap.apply(o2)));
		}
		
		@Override
		public void clear() {
			list.clear();
		}
		
		@Override
		public T set(int index, T element) {
			return remap.apply(list.set(index, reverseSparse.apply(element)));
		}
		
		@Override
		public void add(int index, T element) {
			list.add(index, reverseSparse.apply(element));
		}
		
		@Override
		public T remove(int index) {
			return remap.apply(list.remove(index));
		}
		
		@Override
		public Iterator<T> iterator() {
			return ConvertingIterator.createConverterOneDirectional(list.iterator(), remap);
		}
		
		@Override
		public ListIterator<T> listIterator() {
			return ConvertingListIterator.createConvertingBiDirectional(list.listIterator(), remap, reverseSparse);
		}
		
		@Override
		public ListIterator<T> listIterator(int index) {
			return ConvertingListIterator.createConvertingBiDirectional(list.listIterator(index), remap, reverseSparse);
		}
		
		@Override
		public List<T> subList(int fromIndex, int toIndex) {
			return ConvertingList.createConvertingBiDirectionalSparse(list.subList(fromIndex, toIndex), remap, reverseSparse);
		}
		
		@Override
		public boolean removeIf(Predicate<? super T> filter) {
			return list.removeIf(f -> filter.test(remap.apply(f)));
		}
	}
	
	public static class BiDirectional<F, T> extends BiDirectionalSparse<F, T> {
		
		public Function<? super T, ? extends F> reverse;
		
		public BiDirectional(List<F> list, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverse) {
			this(list, remap, reverse, reverse);
		}
		
		public BiDirectional(List<F> list, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverseSparse, Function<? super T, ? extends F> reverse) {
			super(list, remap, reverseSparse);
			this.reverse = reverse;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean contains(Object o) {
			return list.contains(reverse.apply((T) o));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsAll(Collection<?> c) {
			return list.containsAll(ConvertingCollection.createConvertingBiDirectionalUnmodifiable((Collection<F>) c, remap, reverse));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public int indexOf(Object o) {
			return list.indexOf(reverse.apply((T) o));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public int lastIndexOf(Object o) {
			return list.lastIndexOf(reverse.apply((T) o));
		}
		
		//more methods!
	}
}
