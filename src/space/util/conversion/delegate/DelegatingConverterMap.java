package space.util.conversion.delegate;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;

import java.util.function.BiFunction;

public class DelegatingConverterMap<MINFROM, MINTO> implements ConverterMap<MINFROM, MINTO> {
	
	public ConverterMap<MINFROM, MINTO> map;
	
	public DelegatingConverterMap(ConverterMap<MINFROM, MINTO> map) {
		this.map = map;
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		return map.getConverter(fromClass, toClass);
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> void putConverter(Class<FROM> fromClass, Class<TO> toClass, Converter<FROM, TO> converter) {
		map.putConverter(fromClass, toClass, converter);
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverterOrAdd(Class<FROM> fromClass, Class<TO> toClass, BiFunction<Class<? extends MINFROM>, Class<? extends MINTO>, Converter<? extends MINFROM, ? extends MINTO>> function) {
		return map.getConverterOrAdd(fromClass, toClass, function);
	}
}
