package space.util.string.toStringHelper;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ToStringHelperGetter {
	
	@NotNull
	public static Supplier<ToStringHelper<?>> DEFAULT = () -> ToStringHelperDefault.INSTANCE;
}
