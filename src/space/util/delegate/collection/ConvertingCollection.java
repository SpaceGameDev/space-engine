package space.util.delegate.collection;

import space.util.delegate.iterator.ConvertingIterator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConvertingCollection<F, T> implements Collection<T> {
	
	public Collection<F> coll;
	public Function<F, T> remap;
	public Function<T, F> reverse;
	public Predicate<T> add;
	
	public ConvertingCollection(Collection<F> coll, Function<F, T> remap) {
		this(coll, remap, null, null);
	}
	
	public ConvertingCollection(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse, boolean allowAdd) {
		this(coll, remap, reverse, allowAdd ? t -> coll.add(reverse.apply(t)) : null);
	}
	
	public ConvertingCollection(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse, Predicate<T> add) {
		this.coll = coll;
		this.remap = remap;
		this.reverse = reverse;
		this.add = add;
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
		if (reverse != null)
			//noinspection unchecked
			return coll.contains(reverse.apply((T) o));
		
		for (F f : coll)
			if (Objects.equals(remap.apply(f), o))
				return true;
		return false;
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
	public boolean add(T t) {
		if (add == null)
			throw new UnsupportedOperationException();
		return add.test(t);
	}
	
	@Override
	public boolean remove(Object o) {
		if (reverse != null)
			//noinspection unchecked
			return coll.remove(reverse.apply((T) o));
		
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
	public boolean containsAll(Collection<?> c) {
		if (reverse != null) {
			ArrayList<Object> list = new ArrayList<>();
			for (Object o : c)
				//noinspection unchecked
				list.add(reverse.apply((T) o));
			return coll.containsAll(list);
		}
		
		for (F f : coll) {
			T t = remap.apply(f);
			for (Object o : c)
				if (Objects.equals(t, o))
					return true;
		}
		return false;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		if (add == null)
			throw new UnsupportedOperationException();
		
		boolean mod = false;
		for (T t : c)
			if (add.test(t))
				mod = true;
		return mod;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		if (reverse != null) {
			ArrayList<Object> list = new ArrayList<>();
			for (Object o : c)
				//noinspection unchecked
				list.add(reverse.apply((T) o));
			return coll.removeAll(list);
		}
		
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
		if (reverse != null) {
			ArrayList<Object> list = new ArrayList<>();
			for (Object o : c)
				//noinspection unchecked
				list.add(reverse.apply((T) o));
			return coll.retainAll(list);
		}
		
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
		coll.clear();
	}
}
