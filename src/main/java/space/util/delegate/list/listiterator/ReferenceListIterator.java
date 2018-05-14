package space.util.delegate.list.listiterator;

import space.util.delegate.util.ReferenceUtil;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.Reference;
import java.util.ListIterator;
import java.util.function.Function;

/**
 * Remaps all Entries to a {@link Reference} of type E. These References are created by refCreator in the Constructor.<br>
 * <b>Added References have to be removed manually.</b> Otherwise References may accumulate.
 */
public class ReferenceListIterator<E> extends ConvertingListIterator.BiDirectional<Reference<? extends E>, E> {
	
	public ReferenceListIterator(ListIterator<Reference<? extends E>> listIterator, Function<? super E, ? extends Reference<? extends E>> refCreator) {
		super(listIterator, ReferenceUtil::getSafe, refCreator);
	}
	
	public void setRefCreator(Function<? super E, ? extends Reference<? extends E>> refCreator) {
		this.reverse = refCreator;
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("listIterator", this.listIterator);
		tsh.add("refCreator", this.reverse);
		return tsh.build();
	}
}
