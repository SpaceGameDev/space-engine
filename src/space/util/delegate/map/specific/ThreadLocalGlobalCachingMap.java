package space.util.delegate.map.specific;

import space.util.delegate.map.CachingMap;
import space.util.delegate.map.DefaultingMap;
import space.util.delegate.map.DelegatingMap;
import space.util.delegate.map.SupplierMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ThreadLocalGlobalCachingMap<K, V> extends DelegatingMap<K, V> {
	
	public final Map<K, V> globalMap;
	public final ThreadLocal<Map<K, V>> localMap;
	
	public ThreadLocalGlobalCachingMap(Function<K, V> creator) {
		super(null);
		
		localMap = ThreadLocal.withInitial(HashMap::new);
		globalMap = new CachingMap<>(new ConcurrentHashMap<>(), creator, false);
		
		map = new CachingMap<>(new SupplierMap<>(localMap::get), DefaultingMap.makeDefaultFunctionFromMap(globalMap), false);
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
