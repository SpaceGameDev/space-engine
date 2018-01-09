package space.util.string.builder;

import space.util.annotation.Self;
import space.util.string.toStringHelper.ToStringHelper;

public interface IStringBuilder<@Self SELF extends IStringBuilder<SELF>> {
	
	//capacity
	default boolean ensureCapacityIndex(int index) {
		return ensureCapacity(index + 1);
	}
	
	boolean ensureCapacity(int capa);
	
	//append
	SELF append(String str);
	
	default SELF append(Object obj) {
		return obj == null ? append("null") : append(ToStringHelper.getDefault().toString(obj).toString());
	}
	
	default SELF append(byte v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	default SELF append(short v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	default SELF append(char v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	default SELF append(int v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	default SELF append(long v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	default SELF append(float v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	default SELF append(double v) {
		return append(ToStringHelper.getDefault().toString(v));
	}
	
	default SELF append(char[] str) {
		return append(new String(str));
	}
	
	default SELF append(IStringBuilder1D<?> b) {
		return append(b.getChars());
	}
	
	//fill
	SELF fill(int length, char c);
}
