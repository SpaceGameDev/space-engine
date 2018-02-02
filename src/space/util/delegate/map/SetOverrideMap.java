package space.util.delegate.map;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An abstract {@link Map} for easier overriding of all put / set Methods.
 */
public abstract class SetOverrideMap<K, V> extends DelegatingMap<K, V> {
	
	public SetOverrideMap(Map<K, V> map) {
		super(map);
	}
	
	@Override
	public abstract V put(K key, V value);
	
	@Override
	public abstract V remove(Object key);
	
	@Override
	public abstract void putAll(Map<? extends K, ? extends V> m);
	
	@Override
	public abstract void clear();
	
	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		superreplaceAll(function);
	}
	
	@Override
	public V putIfAbsent(K key, V value) {
		return superputIfAbsent(key, value);
	}
	
	@Override
	public boolean remove(Object key, Object value) {
		return superremove(key, value);
	}
	
	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return superreplace(key, oldValue, newValue);
	}
	
	@Override
	public V replace(K key, V value) {
		return superreplace(key, value);
	}
	
	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return supercomputeIfAbsent(key, mappingFunction);
	}
	
	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return supercomputeIfPresent(key, remappingFunction);
	}
	
	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return supercompute(key, remappingFunction);
	}
	
	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return supermerge(key, value, remappingFunction);
	}
}
