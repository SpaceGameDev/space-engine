package space.util.delegate.set;

import space.util.delegate.collection.DelegatingCollection;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.Set;

/**
 * Casts a {@link Collection} to a {@link Set}<br>
 * <b>Does not ensure uniqueness of entries</b>
 */
public class SetCast<E> extends DelegatingCollection<E> implements Set<E> {
	
	public SetCast(Collection<E> coll) {
		super(coll);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("cast(Set)", coll);
	}
}
