package space.util.delegate.collection;

import space.util.delegate.util.ReferenceUtil;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.function.Function;

/**
 * Remaps all Entries to a {@link Reference} of type E. These References are created by refCreator in the Constructor.
 */
public class ReferenceCollection<E> extends ConvertingCollection.BiDirectionalSparse<Reference<? extends E>, E> {
	
	public ReferenceCollection(Collection<Reference<? extends E>> coll, Function<E, Reference<? extends E>> refCreator) {
		super(coll, ReferenceUtil::getSafe, refCreator);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("coll", this.coll);
		tsh.add("refCreator", this.reverseSparse);
		return tsh.build();
	}
}
