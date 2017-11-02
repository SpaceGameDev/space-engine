package space.util.delegate.iterator;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.string.toStringHelperOld.ToStringHelperCollection;
import space.util.string.toStringHelperOld.ToStringHelperInstance;

import java.util.Iterator;
import java.util.function.Consumer;

public class SynchronizedIterator<E> extends DelegatingIterator<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(SynchronizedIterator.class, d -> new SynchronizedIterator(Copyable.copy(d.i)));
	}
	
	public SynchronizedIterator(Iterator<E> i) {
		super(i);
	}
	
	@Override
	public synchronized boolean hasNext() {
		return super.hasNext();
	}
	
	@Override
	public synchronized E next() {
		return super.next();
	}
	
	@Override
	public synchronized void remove() {
		super.remove();
	}
	
	@Override
	public synchronized void forEachRemaining(Consumer<? super E> action) {
		super.forEachRemaining(action);
	}
	
	@Override
	public synchronized int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public synchronized boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		return api.getModifier().getInstance("synchronized", i);
	}
}
