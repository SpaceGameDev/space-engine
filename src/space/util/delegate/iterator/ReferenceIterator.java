package space.util.delegate.iterator;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.delegate.util.ReferenceUtil;
import space.util.string.toStringHelper.ToStringHelper;

import java.lang.ref.Reference;
import java.util.Iterator;
import java.util.function.Consumer;

public class ReferenceIterator<E> implements BaseObject, Iteratorable<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(ReferenceIterator.class, d -> new ReferenceIterator(Copyable.copy(d.i)));
	}
	
	public Iterator<Reference<? extends E>> i;
	
	public ReferenceIterator(Iterator<Reference<? extends E>> i) {
		this.i = i;
	}
	
	@Override
	public boolean hasNext() {
		return i.hasNext();
	}
	
	@Override
	public E next() {
		return ReferenceUtil.getSafe(i.next());
	}
	
	@Override
	public void remove() {
		i.remove();
	}
	
	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		i.forEachRemaining(ref -> action.accept(ref.get()));
	}
	
	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof ReferenceIterator && i.equals(((ReferenceIterator<?>) o).i);
	}
	
	@Override
	public int hashCode() {
		return i.hashCode();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("reference", i);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
