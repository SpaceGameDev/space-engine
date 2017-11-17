package space.util.delegate.iterator;

import space.util.baseobjectOld.BaseObject;
import space.util.baseobjectOld.Copyable;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

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
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("i", this.i);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
