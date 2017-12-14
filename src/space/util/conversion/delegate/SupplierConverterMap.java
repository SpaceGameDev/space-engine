package space.util.conversion.delegate;

import space.util.baseobject.ToString;
import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;
import space.util.conversion.ConverterMap.ConverterMapAdvanced;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class SupplierConverterMap<MINFROM, MINTO> implements ConverterMapAdvanced<MINFROM, MINTO>, ToString {
	
	public Supplier<ConverterMap<MINFROM, MINTO>> map;
	
	public SupplierConverterMap(Supplier<ConverterMap<MINFROM, MINTO>> map) {
		this.map = map;
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		return map.get().getConverter(fromClass, toClass);
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> void putConverter(Class<FROM> fromClass, Class<TO> toClass, Converter<FROM, TO> converter) {
		ConverterMap<MINFROM, MINTO> map = this.map.get();
		if (map instanceof ConverterMapAdvanced<?, ?>)
			((ConverterMapAdvanced<MINFROM, MINTO>) map).putConverter(fromClass, toClass, converter);
		throw new NoSuchMethodError("delegated map is not instanceof ConverterMapAdvanced");
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> computeIfAbsent(Class<FROM> fromClass, Class<TO> toClass, BiFunction<Class<? extends MINFROM>, Class<? extends MINTO>, Converter<? extends MINFROM, ? extends MINTO>> function) {
		ConverterMap<MINFROM, MINTO> map = this.map.get();
		if (map instanceof ConverterMapAdvanced<?, ?>)
			return ((ConverterMapAdvanced<MINFROM, MINTO>) map).computeIfAbsent(fromClass, toClass, function);
		throw new NoSuchMethodError("delegated map is not instanceof ConverterMapAdvanced");
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
