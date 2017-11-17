package space.util.conversion.impl;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;

import java.util.HashMap;
import java.util.Map;

public class ConverterMapMiddle<MINFROM, MINTO, MIDDLE> implements ConverterMap<MINFROM, MINTO> {
	
	public Map<Class<?>, Converter<?, MIDDLE>> mapFrom = new HashMap<>();
	public Map<Class<?>, Converter<MIDDLE, ?>> mapTo = new HashMap<>();
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		Converter<?, MIDDLE> convFM = mapFrom.get(fromClass);
		if (convFM == null)
			return null;
		
		Converter<MIDDLE, ?> convMT = mapTo.get(toClass);
		if (convMT == null)
			return null;
		
		//noinspection unchecked
		return ((Converter<FROM, MIDDLE>) convFM).andThen((Converter<MIDDLE, TO>) convMT);
	}
	
	public <FROM extends MINFROM> Converter<?, MIDDLE> putFromConvMiddle(Class<? extends FROM> key, Converter<FROM, MIDDLE> value) {
		return mapFrom.put(key, value);
	}
	
	public <TO extends MINTO> Converter<MIDDLE, ?> putMiddleConvTo(Class<? extends TO> key, Converter<MIDDLE, TO> value) {
		return mapTo.put(key, value);
	}
}
