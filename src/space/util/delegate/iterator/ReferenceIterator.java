package space.util.delegate.iterator;

import space.util.baseobject.ToString;
import space.util.delegate.util.ReferenceUtil;
import space.util.string.toStringHelper.ToStringHelper;

import java.lang.ref.Reference;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Remaps all Entries to a {@link Reference} of type E. (No Reference Creator is needed here)
 */
public class ReferenceIterator<E> implements ToString, Iteratorable<E> {
	
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
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("reference", i);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
