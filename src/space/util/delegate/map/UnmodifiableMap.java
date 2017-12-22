package space.util.delegate.map;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The {@link UnmodifiableMap} makes the {@link Map} unmodifiable.
 */
public class UnmodifiableMap<K, V> extends DelegatingMap<K, V> {
	
	public UnmodifiableMap(Map<K, V> map) {
		super(map);
	}
	
	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public V putIfAbsent(K key, V value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean remove(Object key, Object value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public V replace(K key, V value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
}
