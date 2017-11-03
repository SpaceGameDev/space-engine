package space.util.delegate.collection;

import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.Set;

/**
 * casts a collection to a Set<br>
 * <b>does not ensure uniqueness of entries</b>
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
