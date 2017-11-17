package space.util.delegate.impl;

import space.util.baseobjectOld.BaseObject;
import space.util.baseobjectOld.Copyable;
import space.util.delegate.iterator.Iteratorable;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * iterates over the names of an enum
 */
public class EnumNameIterable<E extends Enum<E>> implements BaseObject, Collection<String> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(EnumNameIterable.class, EnumNameIterable::new, d -> new EnumNameIterable(Copyable.copy(d.array)));
	}
	
	public E[] array;
	
	public EnumNameIterable() {
		
	}
	
	public EnumNameIterable(E[] array) {
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
				return array[index++].name();
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
