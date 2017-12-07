package space.util.conversion.delegate;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;

import java.util.Map;

/**
 * Is threadsafe if the internal {@link Map} is threadsafe.
 */
public class CachingConverterMap<MINFROM, MINTO> extends DefaultingConverterMap<MINFROM, MINTO> {
	
	public CachingConverterMap(Map<Key<Class<? extends MINFROM>, Class<? extends MINTO>>, Converter<? extends MINFROM, ? extends MINTO>> map, ConverterMap<MINFROM, MINTO> def) {
		super(map, def);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		if (fromClass.equals(toClass))
			return Converter.identity();
		return (Converter<FROM, TO>) map.computeIfAbsent(new Key<>(fromClass, toClass), key -> def.getConverter(fromClass, toClass));
	}
}
