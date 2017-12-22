package space.util.delegate.iterator;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A {@link Iteratorable} delegating all calls to it's Field {@link SupplierIterator#i}, which is an {@link Supplier} of Type {@link Iteratorable}, allowing for unique usages. The {@link Supplier} is provided by Constructor or set directly.
 */
public class SupplierIterator<E> implements ToString, Iteratorable<E> {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(SupplierIterator.class, d -> new SupplierIterator(Copyable.copy(d.i)));
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
