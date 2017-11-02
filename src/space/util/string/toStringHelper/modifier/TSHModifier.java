package space.util.string.toStringHelper.modifier;

import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelperInstance;

public interface TSHModifier extends ToStringHelper {
	
	TSHModifierInstance getInstance(String modifier, Object value);
	
	interface TSHModifierInstance extends ToStringHelperInstance {
	
	}
}
