package space.util.conversion.impl;

import space.util.baseobject.ToString;
import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Map;

/**
 * maps FROM something to a MIDDLE type and from the MIDDLE type TO something.
 * Is threadsafe if the internal {@link Map} is threadsafe.
 */
public class ConverterMapMiddle<MINFROM, MINTO, MIDDLE> implements ConverterMap<MINFROM, MINTO>, ToString {
	
	public Map<Class<?>, Converter<?, MIDDLE>> mapFrom;
	public Map<Class<?>, Converter<MIDDLE, ?>> mapTo;
	
	public ConverterMapMiddle(Map<Class<?>, Converter<?, MIDDLE>> mapFrom, Map<Class<?>, Converter<MIDDLE, ?>> mapTo) {
		this.mapFrom = mapFrom;
		this.mapTo = mapTo;
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		if (fromClass.equals(toClass))
			return Converter.identity();
		
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
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("mapFrom", this.mapFrom);
		tsh.add("mapTo", this.mapTo);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
