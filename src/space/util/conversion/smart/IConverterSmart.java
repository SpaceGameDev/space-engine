package space.util.conversion.smart;

import space.util.baseobject.BaseObject;
import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public interface IConverterSmart<MIN> extends ConverterMap<MIN, MIN> {
	
	@Override
	<FROM extends MIN, TO extends MIN> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass);
}
