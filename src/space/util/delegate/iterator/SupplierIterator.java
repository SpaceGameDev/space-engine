package space.util.delegate.iterator;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.string.toStringHelperOld.ToStringHelperCollection;
import space.util.string.toStringHelperOld.ToStringHelperInstance;
import space.util.string.toStringHelperOld.objects.TSHObjects.TSHObjectsInstance;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SupplierIterator<E> implements BaseObject, Iteratorable<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(SupplierIterator.class, d -> new SupplierIterator(Copyable.copy(d.i)));
	}
	
	public Supplier<Iterator<E>> i;
	
	public SupplierIterator(Supplier<Iterator<E>> i) {
		this.i = i;
	}
	
	@Override
	public boolean hasNext() {
		return i.get().hasNext();
	}
	
	@Override
	public E next() {
		return i.get().next();
	}
	
	@Override
	public void remove() {
		i.get().remove();
	}
	
	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		i.get().forEachRemaining(action);
	}
	
	@Override
	public int hashCode() {
		return i.get().hashCode();
	}
	
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object obj) {
		return i.get().equals(obj);
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("i", this.i);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
