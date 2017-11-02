package space.util.delegate.list.listiterator;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.string.toStringHelperOld.ToStringHelperCollection;
import space.util.string.toStringHelperOld.ToStringHelperInstance;

import java.util.ListIterator;

public class UnmodifiableListIterator<E> extends DelegatingListIterator<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(UnmodifiableListIterator.class, d -> new UnmodifiableListIterator(Copyable.copy(d.iterator)));
	}
	
	public UnmodifiableListIterator(ListIterator<E> iterator) {
		super(iterator);
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void set(E e) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void add(E e) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		return api.getModifier().getInstance("unmodifiable", iterator);
	}
}
