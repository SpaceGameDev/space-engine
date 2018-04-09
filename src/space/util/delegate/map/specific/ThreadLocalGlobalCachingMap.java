package space.util.delegate.map.specific;

import space.util.baseobject.ToString;
import space.util.delegate.map.CachingMap;
import space.util.delegate.map.SupplierMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ThreadLocalGlobalCachingMap<K, V> implements ToString {
	
	public Map<K, V> globalMap;
	public ThreadLocal<Map<K, V>> localMap;
	public Map<K, V> map;
	
	public ThreadLocalGlobalCachingMap() {
		this(null);
	}
	
	public ThreadLocalGlobalCachingMap(Function<K, V> creator) {
		localMap = ThreadLocal.withInitial(HashMap::new);
		globalMap = creator != null ? new CachingMap<>(new ConcurrentHashMap<>(), creator, false) : new ConcurrentHashMap<>();
		map = new CachingMap<>(new SupplierMap<>(localMap::get), DefaultingMap.makeDefaultFunctionFromMap(globalMap), false);
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("globalMap", this.globalMap);
		
		Map<K, V> localMap = this.localMap.get();
		if (localMap != null)
			tsh.add("localMap.size()", localMap.size());
		else
			tsh.add("localMap", "null");
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
