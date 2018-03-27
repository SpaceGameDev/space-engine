package space.util.delegate.indexmap;

import space.util.baseobject.ToString;
import space.util.delegate.util.ReferenceUtil;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.Reference;
import java.util.function.Function;

/**
 * Remaps all Entries to a {@link Reference} of type VALUE. These References are created by the {@link ReferenceIndexMap#refCreator Reference Creator} supplied with the Constructor or directly set.
 * Note that it will accumulate References if not removed.
 */
public class ReferenceIndexMap<VALUE> extends ConvertingIndexMap.BiDirectionalSparse<Reference<? extends VALUE>, VALUE> implements ToString {
	
	public ReferenceIndexMap(IndexMap<Reference<? extends VALUE>> indexMap, Function<VALUE, Reference<? extends VALUE>> refCreator) {
		super(indexMap, ReferenceUtil::getSafe, refCreator);
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("refCreator", this.reverseSparse);
		return tsh.build();
	}
}
