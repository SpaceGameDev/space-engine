package space.util.conversion.delegate.special;

import space.util.baseobject.ToString;
import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;
import space.util.conversion.delegate.CachingConverterMap;
import space.util.conversion.impl.ConverterMapImpl;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocalGlobalCachingConverterMap<MINFROM, MINTO> implements ConverterMap<MINFROM, MINTO>, ToString {
	
	public final ConverterMap<MINFROM, MINTO> map;
	public final ConverterMap<MINFROM, MINTO> globalMap;
	public final ThreadLocal<ConverterMap<MINFROM, MINTO>> localMap;
	
	public ThreadLocalGlobalCachingConverterMap() {
		this(null);
	}
	
	public ThreadLocalGlobalCachingConverterMap(ConverterMap<MINFROM, MINTO> creator) {
		localMap = ThreadLocal.withInitial(() -> new ConverterMapImpl<>(new HashMap<>()));
		globalMap = creator != null ? new CachingConverterMap<>(new ConcurrentHashMap<>(), creator) : new ConverterMapImpl<>(new ConcurrentHashMap<>());
		
		//FIXME: that null right there
		map = new CachingConverterMap<>(null, globalMap);
	}
	
	@Override
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		return map.getConverter(fromClass, toClass);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("globalMap", this.globalMap);
		tsh.add("localMap", this.localMap.get());
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
