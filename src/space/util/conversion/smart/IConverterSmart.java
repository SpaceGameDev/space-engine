package space.util.conversion.smart;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;

public interface IConverterSmart<MIN> extends ConverterMap<MIN, MIN> {
	
	@Override
	<FROM extends MIN, TO extends MIN> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass);
}
