package space.util.string.toStringHelper;

import java.util.function.Supplier;

public interface ToStringHelper {
	
	static ToStringHelperCollection get() {
		return ToStringHelperDefault.DEFAULT.get();
	}
	
	static void setToStringCollection(Supplier<ToStringHelperCollection> coll) {
		ToStringHelperDefault.DEFAULT = coll;
	}
	
	static void setToStringCollection(ToStringHelperCollection coll) {
		ToStringHelperDefault.DEFAULT = () -> coll;
	}
	
	void setToStringHelperCollection(ToStringHelperCollection coll);
}
