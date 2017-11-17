package space.util.delegate.iterator;

import space.util.baseobjectOld.BaseObject;
import space.util.baseobjectOld.Copyable;
import space.util.string.toStringHelper.ToStringHelper;

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
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("synchronized", i);
	}
}
