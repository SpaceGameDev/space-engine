package space.util.delegate.iterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.lang.ref.Reference;

/**
 * Remaps all Entries to a {@link Reference} of type E. (No Reference Creator is needed here)
 */
public class ReferenceIterator<E> implements ToString, Iteratorable<E> {
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("reference", i);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
