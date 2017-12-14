package space.util.conversion;

import java.util.function.BiFunction;

import static space.util.GetClass.gClass;

public interface ConverterMap<MINFROM, MINTO> {
	
	<FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass);
	
	default <FROM extends MINFROM, TO extends MINTO> TO convert(FROM from, Class<TO> toClass) {
		Converter<FROM, TO> converter = getConverter(gClass(from), toClass);
		return converter == null ? null : converter.convertNew(from);
	}
	
	default <FROM extends MINFROM, TO extends MINTO> TO convert(FROM from, TO to) {
		Converter<FROM, TO> converter = getConverter(gClass(from), gClass(to));
		return converter == null ? null : converter.convertInstance(from, to);
	}
	
	interface ConverterMapAdvanced<MINFROM, MINTO> extends ConverterMap<MINFROM, MINTO> {
		
		<FROM extends MINFROM, TO extends MINTO> void putConverter(Class<FROM> fromClass, Class<TO> toClass, Converter<FROM, TO> converter);
		
		<FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> computeIfAbsent(Class<FROM> fromClass, Class<TO> toClass, BiFunction<Class<? extends MINFROM>, Class<? extends MINTO>, Converter<? extends MINFROM, ? extends MINTO>> function);
	}
}
