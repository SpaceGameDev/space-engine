package space.util.delegate.map;

import space.util.baseobject.Cache;
import space.util.delegate.util.CacheUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The {@link CachingMap} tries to get a value from the {@link CachingMap#map}, and when no value has been found, it will get the value from the {@link CachingMap#def}, write it into the local map and return it;
 * <p>
 * {@link CachingMap} is threadsafe, if the internal {@link CachingMap#map} is threadsafe.
 */
public class CachingMap<K, V> extends ConvertingMap.BiDirectional<K, V, V> implements Cache {
	
	public Function<K, V> def;
	public boolean allowIterateOverExisting;
	
	public CachingMap(Map<K, V> indexMap, Function<K, V> def) {
		this(indexMap, def, true);
	}
	
	public CachingMap(Map<K, V> indexMap, Function<K, V> def, boolean allowIterateOverExisting) {
		super(indexMap, CacheUtil::fromCache, CacheUtil::toCache);
		this.def = def;
		this.allowIterateOverExisting = allowIterateOverExisting;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean containsKey(Object key) {
		return super.computeIfAbsent((K) key, def) != null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		return super.computeIfAbsent((K) key, def);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public V getOrDefault(Object key, V defaultValue) {
		V ret = super.computeIfAbsent((K) key, def);
		return ret == null ? defaultValue : ret;
	}
	
	@Override
	public Set<K> keySet() {
		if (allowIterateOverExisting)
			return super.keySet();
		throw new UnsupportedOperationException("Cache iteration not allowed!");
	}
	
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		if (allowIterateOverExisting)
			return super.entrySet();
		throw new UnsupportedOperationException("Cache iteration not allowed!");
	}
	
	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		if (allowIterateOverExisting)
			super.replaceAll(function);
		throw new UnsupportedOperationException("Cache iteration not allowed!");
	}
	
	@Override
	public V putIfAbsent(K key, V value) {
		super.computeIfAbsent(key, def);
		return super.putIfAbsent(key, value);
	}
	
	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		super.computeIfAbsent(key, def);
		return super.replace(key, oldValue, newValue);
	}
	
	@Override
	public V replace(K key, V value) {
		super.computeIfAbsent(key, def);
		return super.replace(key, value);
	}
	
	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return super.computeIfAbsent(key, k -> {
			V defValue = def.apply(k);
			return defValue != null ? defValue : mappingFunction.apply(k);
		});
	}
	
	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return map.compute(key, (k, v) -> {
			//IfPresent missing
			V v2 = (v == null) ? def.apply(k) : CacheUtil.fromCache(v);
			return CacheUtil.toCache(remappingFunction.apply(k, v2));
		});
	}
	
	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return map.compute(key, (k, v) -> {
			V v2 = (v == null) ? def.apply(k) : CacheUtil.fromCache(v);
			return CacheUtil.toCache(remappingFunction.apply(k, v2));
		});
	}
	
	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return super.merge(key, value, remappingFunction);
	}
	
	@Override
	public Collection<V> values() {
		if (allowIterateOverExisting)
			return super.values();
		throw new UnsupportedOperationException("Cache iteration not allowed!");
	}
	
	@Override
	public boolean remove(Object key, Object value) {
		return super.remove(key, value);
	}
	
	@Override
	public void clearCache() {
		map.clear();
	}
}
