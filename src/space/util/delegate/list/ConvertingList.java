package space.util.delegate.list;

import space.util.baseobject.ToString;
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
	
	@SuppressWarnings("TypeParameterHidesVisibleType")
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("list", this.list);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static <F, T> OneDirectionalUnmodifiable<F, T> createConvertingOneDirectionalUnmodifiable(List<F> list, Function<F, T> remap) {
		return new OneDirectionalUnmodifiable<>(list, remap);
	}
	
	public static class OneDirectionalUnmodifiable<F, T> extends ConvertingList<F, T> {
		
		public Function<F, T> remap;
		
		public OneDirectionalUnmodifiable(List<F> list, Function<F, T> remap) {
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
		public boolean containsAll(Collection<?> c) {
			outer:
			for (F f : list) {
				T t = remap.apply(f);
				for (Object o : c)
					if (Objects.equals(t, o))
						continue outer;
				return false;
			}
			return true;
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
	
	public static class BiDirectionalSparse<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<T, F> reverseSparse;
		
		public BiDirectionalSparse(List<F> list, Function<F, T> remap, Function<T, F> reverseSparse) {
			super(list, remap);
			this.reverseSparse = reverseSparse;
		}
		
		@Override
		public boolean add(T t) {
			return super.add(t);
		}
		
		@Override
		public boolean remove(Object o) {
			return super.remove(o);
		}
		
		@Override
		public boolean addAll(Collection<? extends T> c) {
			return super.addAll(c);
		}
		
		@Override
		public boolean addAll(int index, Collection<? extends T> c) {
			return super.addAll(index, c);
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			return super.removeAll(c);
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			return super.retainAll(c);
		}
		
		@Override
		public void replaceAll(UnaryOperator<T> operator) {
			super.replaceAll(operator);
		}
		
		@Override
		public void sort(Comparator<? super T> c) {
			super.sort(c);
		}
		
		@Override
		public void clear() {
			super.clear();
		}
		
		@Override
		public T set(int index, T element) {
			return super.set(index, element);
		}
		
		@Override
		public void add(int index, T element) {
			super.add(index, element);
		}
		
		@Override
		public T remove(int index) {
			return super.remove(index);
		}
		
		@Override
		public Iterator<T> iterator() {
			return super.iterator();
		}
		
		@Override
		public ListIterator<T> listIterator() {
			return super.listIterator();
		}
		
		@Override
		public ListIterator<T> listIterator(int index) {
			return super.listIterator(index);
		}
		
		@Override
		public List<T> subList(int fromIndex, int toIndex) {
			return super.subList(fromIndex, toIndex);
		}
		
		@Override
		public boolean removeIf(Predicate<? super T> filter) {
			return super.removeIf(filter);
		}
	}
	
}
