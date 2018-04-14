package space.util.delegate.iterator;

import space.util.delegate.util.ReferenceUtil;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.Reference;
import java.util.Iterator;

/**
 * Remaps all Entries to a {@link Reference} of type E.<br>
 */
public class ReferenceIterator<E> extends ConvertingIterator.OneDirectional<Reference<? extends E>, E> {
	
	public ReferenceIterator(Iterator<Reference<? extends E>> iter) {
		super(iter, ReferenceUtil::getSafe);
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("iter", this.iter);
		return tsh.build();
	}
}
