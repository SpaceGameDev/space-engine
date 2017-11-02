package space.util.string.toStringHelperOld.array;

import space.util.string.toStringHelperOld.ToStringHelperCollection;

public abstract class AbstractTSHArray implements TSHArray {
	
	public ToStringHelperCollection helperCollection;
	
	@Override
	public void setToStringHelperCollection(ToStringHelperCollection coll) {
		this.helperCollection = coll;
	}
}
