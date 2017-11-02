package space.util.string.toStringHelper.modifier;

import space.util.string.toStringHelper.ToStringHelperCollection;

public class TSHModifierDefault extends AbstractTSHModifier {
	
	public static final TSHModifierDefault INSTANCE = new TSHModifierDefault();
	
	public ToStringHelperCollection helperCollection;
	
	@Override
	public void setToStringHelperCollection(ToStringHelperCollection coll) {
		helperCollection = coll;
	}
	
	@Override
	public TSHModifierInstance getInstance(String modifier, Object value) {
		return new AbstractTSHModifierInstance(modifier, value) {
			@Override
			public String toString() {
				return modifier + " " + value.toString();
			}
		};
	}
}
