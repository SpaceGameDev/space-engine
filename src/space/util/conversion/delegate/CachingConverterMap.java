package space.util.conversion.delegate;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;

import java.util.Map;

/**
 * Is threadsafe if the internal {@link Map} is threadsafe.
 */
public class CachingConverterMap<MINFROM, MINTO> extends DefaultingConverterMap<MINFROM, MINTO> {
	
	public CachingConverterMap(ConverterMap<MINFROM, MINTO> map, ConverterMap<MINFROM, MINTO> def) {
		super(map, def);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		if (fromClass.equals(toClass))
			return Converter.identity();
		return map.getConverterOrAdd(fromClass, toClass, (fromClass1, toClass1) -> def.getConverter(fromClass1, toClass1));
	}
}
