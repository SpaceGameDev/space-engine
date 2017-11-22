package space.util.delegate.iterator;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Iterator;
import java.util.function.Consumer;

public class DelegatingIterator<E> implements BaseObject, Iteratorable<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(DelegatingIterator.class, d -> new DelegatingIterator(Copyable.copy(d.i)));
	}
	
	public Iterator<E> i;
	
	public DelegatingIterator(Iterator<E> i) {
		this.i = i;
	}
	
	//methods
	@Override
	public boolean hasNext() {
		return i.hasNext();
	}
	
	@Override
	public E next() {
		return i.next();
	}
	
	@Override
	public void remove() {
		i.remove();
	}
	
	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		i.forEachRemaining(action);
	}
	
	@Override
	public int hashCode() {
		return i.hashCode();
	}
	
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object obj) {
		return i.equals(obj);
	}
	
	//super
	protected void superremove() {
		Iteratorable.super.remove();
	}
	
	protected void superforEachRemaining(Consumer<? super E> action) {
		Iteratorable.super.forEachRemaining(action);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("delegate", i);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
