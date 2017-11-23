package space.util.delegate.impl;

import space.util.baseobject.ToString;
import space.util.delegate.iterator.Iteratorable;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * turns a char[][]-array into a Iterable of type String
 */
public class CharArrayStringIterable implements ToString, Collection<String> {
	
	public char[][] array;
	
	public CharArrayStringIterable() {
		
	}
	
	public CharArrayStringIterable(CharArrayStringIterable iterable) {
		this(iterable.array);
	}
	
	public CharArrayStringIterable(char[][] array) {
		this.array = array;
	}
	
	@Override
	public Iterator<String> iterator() {
		return new Iteratorable<String>() {
			
			public int index = 0;
			
			@Override
			public boolean hasNext() {
				return array.length > index;
			}
			
			@Override
			public String next() {
				return new String(array[index++]);
			}
		};
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
		if (array.getClass().isAssignableFrom(a.getClass()))
			throw new IllegalArgumentException();
		System.arraycopy(array, 0, a, 0, array.length);
		return a;
	}
	
	@Override
	public boolean add(String e) {
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
	public boolean addAll(Collection<? extends String> c) {
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
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("array", this.array);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
