package space.util.conversion.delegate.special;

import space.util.baseobject.ToString;
import space.util.conversion.ConverterMap;
import space.util.conversion.delegate.CachingConverterMap;
import space.util.conversion.delegate.DelegatingConverterMap;
import space.util.conversion.delegate.SupplierConverterMap;
import space.util.conversion.impl.ConverterMapImpl;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocalGlobalCachingConverterMap<MINFROM, MINTO> extends DelegatingConverterMap<MINFROM, MINTO> implements ToString {
	
	public final ConverterMap<MINFROM, MINTO> globalMap;
	public final ThreadLocal<ConverterMap<MINFROM, MINTO>> localMap;
	
	public ThreadLocalGlobalCachingConverterMap() {
		this(null);
	}
	
	public ThreadLocalGlobalCachingConverterMap(ConverterMap<MINFROM, MINTO> creator) {
		super(null);
		localMap = ThreadLocal.withInitial(() -> new ConverterMapImpl<>(new HashMap<>()));
		globalMap = creator != null ? new CachingConverterMap<>(new ConverterMapImpl<>(new ConcurrentHashMap<>()), creator) : new ConverterMapImpl<>(new ConcurrentHashMap<>());
		map = new CachingConverterMap<>(new SupplierConverterMap<>(localMap::get), globalMap);
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("globalMap", this.globalMap);
		tsh.add("localMap", this.localMap.get());
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
