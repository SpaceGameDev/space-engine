package space.util.delegate.indexmap;

import space.util.delegate.util.ReferenceUtil;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.Reference;
import java.util.function.Function;

/**
 * Remaps all Entries to a {@link Reference} of type VALUE. These References are created by refCreator in the Constructor.<br>
 * <b>Added References have to be removed manually.</b> Otherwise References may accumulate.
 */
public class ReferenceIndexMap<VALUE> extends ConvertingIndexMap.BiDirectionalSparse<Reference<? extends VALUE>, VALUE> {
	
	public ReferenceIndexMap(IndexMap<Reference<? extends VALUE>> indexMap, Function<VALUE, Reference<? extends VALUE>> refCreator) {
		super(indexMap, ReferenceUtil::getSafe, refCreator);
	}
	
	public void setRefCreator(Function<? super VALUE, ? extends Reference<? extends VALUE>> refCreator) {
		this.reverseSparse = refCreator;
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("refCreator", this.reverseSparse);
		return tsh.build();
	}
}
