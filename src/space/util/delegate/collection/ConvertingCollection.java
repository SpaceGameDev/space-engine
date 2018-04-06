package space.util.delegate.collection;

import space.util.baseobject.ToString;
import space.util.delegate.iterator.ConvertingIterator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ConvertingCollection<F, T> implements Collection<T>, ToString {
	
	public Collection<F> coll;
	
	protected ConvertingCollection(Collection<F> coll) {
		this.coll = coll;
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("coll", this.coll);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static class OneDirectionalUnmodifiable<F, T> extends ConvertingCollection<F, T> {
		
		public Function<? super F, ? extends T> remap;
		
		public OneDirectionalUnmodifiable(Collection<F> coll, Function<? super F, ? extends T> remap) {
			super(coll);
			this.remap = remap;
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
			for (F f : coll)
				if (Objects.equals(remap.apply(f), o))
					return true;
			return false;
		}
		
		@Override
		public Iterator<T> iterator() {
			return ConvertingIterator.createConverterOneDirectionalUnmodifiable(coll.iterator(), remap);
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
		public boolean add(T t) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			for (F f : coll)
				if (!c.contains(remap.apply(f)))
					return false;
			return true;
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
		public boolean removeIf(Predicate<? super T> filter) {
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
		public void forEach(Consumer<? super T> action) {
			coll.forEach(f -> action.accept(remap.apply(f)));
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("coll", this.coll);
			tsh.add("remap", this.remap);
			return tsh.build();
		}
	}
	
	public static class BiDirectionalUnmodifiable<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<? super T, ? extends F> reverse;
		
		public BiDirectionalUnmodifiable(Collection<F> coll, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverse) {
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
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("coll", this.coll);
			tsh.add("remap", this.remap);
			tsh.add("reverse", this.reverse);
			return tsh.build();
		}
	}
	
	public static class BiDirectionalSparse<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<? super T, ? extends F> reverseSparse;
		
		public BiDirectionalSparse(Collection<F> coll, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverseSparse) {
			super(coll, remap);
			this.reverseSparse = reverseSparse;
		}
		
		@Override
		public boolean add(T t) {
			return coll.add(reverseSparse.apply(t));
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
			return coll.addAll(new OneDirectionalUnmodifiable<>(c, reverseSparse));
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
		public Iterator<T> iterator() {
			return ConvertingIterator.createConverterOneDirectional(coll.iterator(), remap);
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
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("coll", this.coll);
			tsh.add("remap", this.remap);
			tsh.add("reverseSparse", this.reverseSparse);
			return tsh.build();
		}
	}
	
	public static class BiDirectional<F, T> extends BiDirectionalSparse<F, T> {
		
		public Function<? super T, ? extends F> reverse;
		
		public BiDirectional(Collection<F> coll, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverse) {
			this(coll, remap, reverse, reverse);
		}
		
		public BiDirectional(Collection<F> coll, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverse, Function<? super T, ? extends F> reverseSparse) {
			super(coll, remap, reverseSparse);
			this.reverse = reverse;
		}
		
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
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean contains(Object o) {
			return coll.contains(reverse.apply((T) o));
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("coll", this.coll);
			tsh.add("remap", this.remap);
			tsh.add("reverseSparse", this.reverseSparse);
			tsh.add("reverse", this.reverse);
			return tsh.build();
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsAll(Collection<?> c) {
			return coll.containsAll(new OneDirectionalUnmodifiable<>((Collection<T>) c, reverse));
		}
	}
}
