package space.util.conversion.delegate;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;

import java.util.function.Supplier;

public class SupplierConverterMap<MINFROM, MINTO> implements ConverterMap<MINFROM, MINTO> {
	
	public Supplier<ConverterMap<MINFROM, MINTO>> map;
	
	public SupplierConverterMap(Supplier<ConverterMap<MINFROM, MINTO>> map) {
		this.map = map;
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		return map.get().getConverter(fromClass, toClass);
	}
}
