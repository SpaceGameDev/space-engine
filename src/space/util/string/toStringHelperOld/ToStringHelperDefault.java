package space.util.string.toStringHelperOld;

import space.util.string.toStringHelperOld.array.TSHArray;
import space.util.string.toStringHelperOld.array.TSHArrayDefault;
import space.util.string.toStringHelperOld.modifier.TSHModifier;
import space.util.string.toStringHelperOld.modifier.TSHModifierDefault;
import space.util.string.toStringHelperOld.nativeType.TSHNativeType;
import space.util.string.toStringHelperOld.nativeType.TSHNativeTypeDefault;
import space.util.string.toStringHelperOld.objects.TSHObjects;
import space.util.string.toStringHelperOld.objects.TSHObjectsDefault;

import java.util.function.Supplier;

public class ToStringHelperDefault implements ToStringHelperCollection {
	
	public static ToStringHelperDefault INSTANCE = new ToStringHelperDefault();
	public static Supplier<ToStringHelperCollection> DEFAULT = () -> INSTANCE;
	
	@Override
	public TSHObjects getObjectPhaser() {
		return TSHObjectsDefault.INSTANCE;
	}
	
	@Override
	public TSHArray getArray() {
		return TSHArrayDefault.INSTANCE;
	}
	
	@Override
	public TSHModifier getModifier() {
		return TSHModifierDefault.INSTANCE;
	}
	
	@Override
	public TSHNativeType getNativeType() {
		return TSHNativeTypeDefault.INSTANCE;
	}
}
