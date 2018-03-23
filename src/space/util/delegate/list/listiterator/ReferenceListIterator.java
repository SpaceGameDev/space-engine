package space.util.delegate.list.listiterator;

import space.util.baseobject.ToString;
import space.util.delegate.util.ReferenceUtil;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.Reference;
import java.util.ListIterator;
import java.util.function.Function;

/**
 * Remaps all Entries to a {@link Reference} of type E. These References are created by the {@link ReferenceListIterator#refCreator Reference Creator} supplied with the Constructor or directly set.
 */
public class ReferenceListIterator<E> extends ConvertingListIterator.BiDirectional<Reference<? extends E>, E> implements ToString {
	
	public ReferenceListIterator(ListIterator<Reference<? extends E>> listIterator, Function<? super E, ? extends Reference<? extends E>> refCreator) {
		super(listIterator, ReferenceUtil::getSafe, refCreator);
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("listIterator", this.listIterator);
		tsh.add("refCreator", this.reverse);
		return tsh.build();
	}
}
