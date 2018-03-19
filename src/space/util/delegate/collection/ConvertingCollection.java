package space.util.delegate.collection;

import space.util.delegate.iterator.ConvertingIterator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public abstract class ConvertingCollection<F, T> implements Collection<T> {
	
	public Collection<F> coll;
	
	public ConvertingCollection(Collection<F> coll) {
		this.coll = coll;
	}
	
	public static class OneDirectionalUnmodifiable<F, T> extends ConvertingCollection<F, T> {
		
		public Function<F, T> remap;
		
		public OneDirectionalUnmodifiable(Collection<F> coll, Function<F, T> remap) {
			super(coll);
			this.remap = remap;
		}
		
		//access methods
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
			for (F f : coll)
				if (Objects.equals(remap.apply(f), o))
					return true;
			return false;
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			outer:
			for (F f : coll) {
				T t = remap.apply(f);
				for (Object o : c)
					if (Objects.equals(t, o))
						continue outer;
				return false;
			}
			return true;
		}
		
		@Override
		public Iterator<T> iterator() {
			return new ConvertingIterator<>(coll.iterator(), remap);
		}
		
		@Override
		public Object[] toArray() {
			//noinspection unchecked
			F[] org = (F[]) coll.toArray();
			Object[] ret = new Object[org.length];
			for (int i = 0; i < org.length; i++)
				ret[i] = remap.apply(org[i]);
			return ret;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <T1> T1[] toArray(T1[] a) {
			F[] org = (F[]) coll.toArray();
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
		
		@Override
		public void forEach(Consumer<? super T> action) {
			coll.forEach(f -> action.accept(remap.apply(f)));
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
		public boolean addAll(Collection<? extends T> c) {
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
		public void clear() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean removeIf(Predicate<? super T> filter) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
	}
	
	public static class BiDirectionalUnmodifiable<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<T, F> reverse;
		
		public BiDirectionalUnmodifiable(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse) {
			super(coll, remap);
			this.reverse = reverse;
		}
		
		//access methods
		@Override
		@SuppressWarnings("unchecked")
		public boolean contains(Object o) {
			return coll.contains(reverse.apply((T) o));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsAll(Collection<?> c) {
			return coll.containsAll(new OneDirectionalUnmodifiable<>((Collection<T>) c, reverse));
		}
	}
	
	public static class BiDirectionalSparse<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<T, F> reverseAdd;
		
		public BiDirectionalSparse(Collection<F> coll, Function<F, T> remap, Function<T, F> reverseAdd) {
			super(coll, remap);
			this.reverseAdd = reverseAdd;
		}
		
		//modify methods
		@Override
		public boolean add(T t) {
			return coll.add(reverseAdd.apply(t));
		}
		
		@Override
		public boolean remove(Object o) {
			Iterator<F> iter = coll.iterator();
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
			ArrayList<F> list = new ArrayList<>(c.size());
			c.forEach(o -> list.add(reverseAdd.apply(o)));
			return coll.addAll(list);
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			boolean mod = false;
			Iterator<F> iter = coll.iterator();
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
			Iterator<F> iter = coll.iterator();
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
		public void clear() {
			super.clear();
		}
		
		@Override
		public boolean removeIf(Predicate<? super T> filter) {
			return coll.removeIf(f -> filter.test(remap.apply(f)));
		}
	}
	
	public static class BiDirectional<F, T> extends BiDirectionalSparse<F, T> {
		
		public Function<T, F> reverse;
		
		public BiDirectional(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse) {
			this(coll, remap, reverse, reverse);
		}
		
		public BiDirectional(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse, Function<T, F> reverseAdd) {
			super(coll, remap, reverseAdd);
			this.reverse = reverse;
		}
		
		//access methods
		@Override
		@SuppressWarnings("unchecked")
		public boolean contains(Object o) {
			return coll.contains(reverse.apply((T) o));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsAll(Collection<?> c) {
			return coll.containsAll(new OneDirectionalUnmodifiable<>((Collection<T>) c, reverse));
		}
		
		//modify methods
		@Override
		@SuppressWarnings("unchecked")
		public boolean remove(Object o) {
			return coll.remove(reverse.apply((T) o));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean removeAll(Collection<?> c) {
			return coll.removeAll(new OneDirectionalUnmodifiable<>((Collection<T>) c, reverse));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean retainAll(Collection<?> c) {
			return coll.retainAll(new OneDirectionalUnmodifiable<>((Collection<T>) c, reverse));
		}
	}
}
