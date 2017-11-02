package space.util.delegate.iterator;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.string.toStringHelperOld.ToStringHelperCollection;
import space.util.string.toStringHelperOld.ToStringHelperInstance;

import java.util.Iterator;

public class UnmodifiableIterator<E> extends DelegatingIterator<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(UnmodifiableIterator.class, d -> new UnmodifiableIterator(Copyable.copy(d.i)));
	}
	
	public UnmodifiableIterator(Iterator<E> i) {
		super(i);
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		return api.getModifier().getInstance("synchronized", i);
	}
}
