package space.util.string.toStringHelper.array;

import space.util.string.toStringHelper.ToStringHelperCollection;

public abstract class AbstractTSHArray implements TSHArray {
	
	public ToStringHelperCollection helperCollection;
	
	@Override
	public void setToStringHelperCollection(ToStringHelperCollection coll) {
		this.helperCollection = coll;
	}
}
