package space.util.string.toStringHelper;

import java.util.function.Supplier;

public class ToStringHelperGetter {
	
	public static Supplier<ToStringHelper<?>> DEFAULT;
	
	static {
		ToStringHelper.setDefault(ToStringHelperDefault.INSTANCE);
	}
}
