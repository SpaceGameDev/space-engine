package space.util.string.toStringHelper;

import space.util.string.toStringHelper.array.TSHArray;
import space.util.string.toStringHelper.modifier.TSHModifier;
import space.util.string.toStringHelper.nativeType.TSHNativeType;
import space.util.string.toStringHelper.objects.TSHObjects;

public interface ToStringHelperCollection {
	
	TSHObjects getObjectPhaser();
	
	TSHArray getArray();
	
	TSHModifier getModifier();
	
	TSHNativeType getNativeType();
}
