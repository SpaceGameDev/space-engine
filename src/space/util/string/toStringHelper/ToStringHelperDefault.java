package space.util.string.toStringHelper;

import space.util.string.toStringHelper.array.TSHArray;
import space.util.string.toStringHelper.array.TSHArrayDefault;
import space.util.string.toStringHelper.modifier.TSHModifier;
import space.util.string.toStringHelper.modifier.TSHModifierDefault;
import space.util.string.toStringHelper.nativeType.TSHNativeType;
import space.util.string.toStringHelper.nativeType.TSHNativeTypeDefault;
import space.util.string.toStringHelper.objects.TSHObjects;
import space.util.string.toStringHelper.objects.TSHObjectsDefault;

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
