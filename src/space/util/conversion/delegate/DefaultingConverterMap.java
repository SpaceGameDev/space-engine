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
public class DefaultingConverterMap<MINFROM, MINTO> extends DelegatingConverterMap<MINFROM, MINTO> implements ToString {
	
	public ConverterMap<MINFROM, MINTO> def;
	
	public DefaultingConverterMap(ConverterMap<MINFROM, MINTO> map, ConverterMap<MINFROM, MINTO> def) {
		super(map);
		this.def = def;
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		Converter<FROM, TO> conv = super.getConverter(fromClass, toClass);
		if (conv != null)
			return conv;
		return def.getConverter(fromClass, toClass);
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
