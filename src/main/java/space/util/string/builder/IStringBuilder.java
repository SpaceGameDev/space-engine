package space.util.string.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.annotation.Self;
import space.util.string.toStringHelper.ToStringHelper;

public interface IStringBuilder<@Self SELF extends IStringBuilder<SELF>> {
	
	//capacity
	default boolean ensureCapacityIndex(int index) {
		return ensureCapacity(index + 1);
	}
	
	boolean ensureCapacity(int capa);
	
	//append
	@NotNull SELF append(@NotNull String str);
	
	@NotNull
	default SELF append(@Nullable Object obj) {
		return append(obj == null ? "null" : ToStringHelper.getDefault().toString(obj).toString());
	}
	
	@NotNull
	default SELF append(byte v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	@NotNull
	default SELF append(short v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	@NotNull
	default SELF append(char v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	@NotNull
	default SELF append(int v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	@NotNull
	default SELF append(long v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	@NotNull
	default SELF append(float v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	@NotNull
	default SELF append(double v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	@NotNull
	default SELF append(@NotNull char[] str) {
		return append(new String(str));
	}
	
	@NotNull
	default SELF append(@NotNull IStringBuilder1D<?> b) {
		return append(b.getChars());
	}
	
	//fill
	@NotNull SELF fill(int length, char c);
}
