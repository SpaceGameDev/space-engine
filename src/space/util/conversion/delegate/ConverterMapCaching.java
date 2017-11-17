package space.util.conversion.delegate;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;
import space.util.conversion.impl.ConverterMapImpl;

public class ConverterMapCaching<MINFROM, MINTO> extends ConverterMapImpl<MINFROM, MINTO> {
	
	public ConverterMap<MINFROM, MINTO> def;
	
	public ConverterMapCaching(ConverterMap<MINFROM, MINTO> def) {
		this.def = def;
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		Converter<FROM, TO> conv = super.getConverter(fromClass, toClass);
		if (conv != null)
			return conv;
		
		conv = def.getConverter(fromClass, toClass);
		putConverter(fromClass, toClass, conv);
		return conv;
	}
}
