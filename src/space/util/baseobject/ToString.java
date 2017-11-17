package space.util.baseobject;

import space.util.string.toStringHelper.ToStringHelper;

public interface ToString {
	
	//non-static
	<T> T toTSH(ToStringHelper<T> api);
	
	default Object toTSH() {
		return toTSH(ToStringHelper.getDefault());
	}
	
	default String toString0() {
		return toTSH().toString();
	}
}
