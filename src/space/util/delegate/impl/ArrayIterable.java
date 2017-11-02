package space.util.delegate.impl;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.delegate.iterator.Iteratorable;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;
import space.util.string.toStringHelper.objects.TSHObjects.TSHObjectsInstance;

import java.util.Arrays;
import java.util.Collection;

public class ArrayIterable<E> implements BaseObject, Collection<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(ArrayIterable.class, ArrayIterable::new, d -> new ArrayIterable(Copyable.copy(d.array)));
	}
	
	public E[] array;
	
	public ArrayIterable() {
		
	}
	
	public ArrayIterable(E[] array) {
		this.array = array;
	}
	
	@Override
	public ArrayIterator<E> iterator() {
		return new ArrayIterator<>(array);
	}
	
	@Override
	public int size() {
		return array.length;
	}
	
	@Override
	public boolean isEmpty() {
		return array.length == 0;
	}
	
	@Override
	public boolean contains(Object o) {
		return Arrays.binarySearch(array, o) != 0;
	}
	
	@Override
	public Object[] toArray() {
		return array.clone();
	}
	
	@Override
	@SuppressWarnings("SuspiciousSystemArraycopy")
	public <T> T[] toArray(T[] a) {
		if (a.length < array.length)
			//noinspection unchecked
			a = (T[]) new Object[array.length];
		System.arraycopy(array, 0, a, 0, array.length);
		return a;
	}
	
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c)
			if (!contains(o))
				return false;
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("array", this.array);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static class ArrayIterator<E> implements BaseObject, Iteratorable<E> {
		
		static {
			//noinspection unchecked
			BaseObject.initClass(ArrayIterable.ArrayIterator.class, d -> new ArrayIterator<>(d.array));
		}
		
		public E[] array;
		public int index;
		public int end;
		
		public ArrayIterator(E[] array) {
			this(array, 0, array.length);
		}
		
		public ArrayIterator(E[] array, int start, int end) {
			this.array = array;
			this.index = start;
			this.end = end;
		}
		
		@Override
		public boolean hasNext() {
			return index < end;
		}
		
		@Override
		public E next() {
			return array[index++];
		}
		
		@Override
		public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
			TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
			tsh.add("array", this.array);
			tsh.add("index", this.index);
			tsh.add("end", this.end);
			return tsh;
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
