package space.util.conversion.delegate;

import space.util.baseobject.ToString;
import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Map;

/**
 * Is threadsafe if the internal {@link Map} is threadsafe.
 */
public class CachingConverterMap<MINFROM, MINTO> implements ConverterMap<MINFROM, MINTO>, ToString {
	
	public ConverterMapAdvanced<MINFROM, MINTO> map;
	public ConverterMap<MINFROM, MINTO> def;
	
	public CachingConverterMap(ConverterMapAdvanced<MINFROM, MINTO> map, ConverterMap<MINFROM, MINTO> def) {
		this.map = map;
		this.def = def;
	}
	
	//get
	@Override
	@SuppressWarnings("unchecked")
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		if (fromClass.equals(toClass))
			return Converter.identity();
		return map.computeIfAbsent(fromClass, toClass, (fromClass1, toClass1) -> def.getConverter(fromClass1, toClass1));
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		tsh.add("def", this.def);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
