package space.util.delegate.map;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class GetOverrideMap<K, V> extends DelegatingMap<K, V> {
	
	public GetOverrideMap(Map<K, V> map) {
		super(map);
	}
	
	//get
	@Override
	public abstract V get(Object key);
	
	@Override
	public abstract boolean containsKey(Object key);
	
	//redirect to default impl
	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return supergetOrDefault(key, defaultValue);
	}
	
	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		superforEach(action);
	}
	
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
