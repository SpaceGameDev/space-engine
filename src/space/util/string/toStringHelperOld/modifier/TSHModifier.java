package space.util.string.toStringHelperOld.modifier;

import space.util.string.toStringHelperOld.ToStringHelper;
import space.util.string.toStringHelperOld.ToStringHelperInstance;

public interface TSHModifier extends ToStringHelper {
	
	TSHModifierInstance getInstance(String modifier, Object value);
	
	interface TSHModifierInstance extends ToStringHelperInstance {
	
	}
}
