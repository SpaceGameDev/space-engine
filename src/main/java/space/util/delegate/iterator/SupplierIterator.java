package space.util.delegate.iterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A {@link Iterator} delegating all calls to it's Field {@link SupplierIterator#iter}, which is an {@link Supplier} of Type {@link Iterator}.
 * The {@link Supplier} is provided by Constructor or set directly.
 */
public class SupplierIterator<E> implements ToString, Iteratorable<E> {
	
	public Supplier<Iterator<E>> iter;
	
	public SupplierIterator(Supplier<Iterator<E>> iter) {
		this.iter = iter;
	}
	
	@Override
	public boolean hasNext() {
		return iter.get().hasNext();
	}
	
	@Override
	public E next() {
		return iter.get().next();
	}
	
	@Override
	public void remove() {
		iter.get().remove();
	}
	
	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		iter.get().forEachRemaining(action);
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("iter", this.iter);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
