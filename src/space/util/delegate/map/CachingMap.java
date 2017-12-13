package space.util.delegate.map;

import space.util.baseobject.Copyable;
import space.util.baseobject.additional.Cache;

import java.util.Map;
import java.util.function.Function;

import static space.util.delegate.util.CacheUtil.*;

/**
 * {@link CachingMap} is threadsafe, if the internal {@link CachingMap#map} is threadsafe.
 */
public class CachingMap<K, V> extends DefaultingMap<K, V> implements Cache {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(CachingMap.class, d -> new CachingMap(d.map, d.def, d.iterateOverDef));
	}
	
	//no def iteration
	public CachingMap(Map<K, V> map, Function<K, V> def) {
		super(map, def);
	}
	
	//with def iteration
	public CachingMap(Map<K, V> map, DefaultFunctionWithIteration<K, V> def) {
		super(map, def);
	}
	
	public CachingMap(Map<K, V> map, Map<K, V> def) {
		super(map, def);
	}
	
	//with boolean
	public CachingMap(Map<K, V> map, Map<K, V> def, boolean iterateOverDef) {
		super(map, def, iterateOverDef);
	}
	
	public CachingMap(Map<K, V> map, Function<K, V> def, boolean iterateOverDef) {
		super(map, def, iterateOverDef);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		return fromObjectToNull(map.computeIfAbsent((K) key, (K k) -> fromNullToObject(def.apply(k))));
	}
	
	@Override
	public void clearCache() {
		map.clear();
	}
}
