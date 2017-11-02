package space.util.string.toStringHelperOld;

import space.util.string.toStringHelperOld.array.TSHArray;
import space.util.string.toStringHelperOld.modifier.TSHModifier;
import space.util.string.toStringHelperOld.nativeType.TSHNativeType;
import space.util.string.toStringHelperOld.objects.TSHObjects;

public interface ToStringHelperCollection {
	
	TSHObjects getObjectPhaser();
	
	TSHArray getArray();
	
	TSHModifier getModifier();
	
	TSHNativeType getNativeType();
}
