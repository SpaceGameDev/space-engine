package space.util.baseobject;

import space.util.string.toStringHelper.ToStringHelper;

public interface BaseObject extends Makeable, Setable, Copyable, ToString {
	
	@Override
	<T> T toTSH(ToStringHelper<T> api);
}
