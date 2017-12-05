package space.util.conversion.delegate;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;
import space.util.conversion.impl.ConverterMapImpl;

import java.util.Map;

/**
 * Is threadsafe if the internal {@link Map} is threadsafe.
 */
public class ConverterMapCaching<MINFROM, MINTO> extends ConverterMapImpl<MINFROM, MINTO> {
	
	public ConverterMap<MINFROM, MINTO> def;
	
	public ConverterMapCaching(Map<Key<Class<?>, Class<?>>, Converter<?, ?>> map, ConverterMap<MINFROM, MINTO> def) {
		super(map);
		this.def = def;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		if (fromClass.equals(toClass))
			return Converter.identity();
		return (Converter<FROM, TO>) map.computeIfAbsent(new Key<>(fromClass, toClass), key -> def.getConverter(fromClass, toClass));
	}
}
