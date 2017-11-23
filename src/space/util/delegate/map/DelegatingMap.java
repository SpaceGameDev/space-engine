package space.util.delegate.map;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DelegatingMap<K, V> implements ToString, Map<K, V> {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(DelegatingMap.class, d -> new DelegatingMap(Copyable.copy(d.map)));
	}
	
	public Map<K, V> map;
	
	public DelegatingMap(Map<K, V> map) {
		this.map = map;
	}
	
	@Override
	public int size() {
		return map.size();
	}
	
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	
	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	
	@Override
	public V get(Object key) {
		return map.get(key);
	}
	
	@Override
	public V put(K key, V value) {
		return map.put(key, value);
	}
	
	@Override
	public V remove(Object key) {
		return map.remove(key);
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}
	
	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public Set<K> keySet() {
		return map.keySet();
	}
	
	@Override
	public Collection<V> values() {
		return map.values();
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}
	
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object o) {
		return map.equals(o);
	}
	
	@Override
	public int hashCode() {
		return map.hashCode();
	}
	
	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return map.getOrDefault(key, defaultValue);
	}
	
	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		map.forEach(action);
	}
	
	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		map.replaceAll(function);
	}
	
	@Override
	public V putIfAbsent(K key, V value) {
		return map.putIfAbsent(key, value);
	}
	
	@Override
	public boolean remove(Object key, Object value) {
		return map.remove(key, value);
	}
	
	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return map.replace(key, oldValue, newValue);
	}
	
	@Override
	public V replace(K key, V value) {
		return map.replace(key, value);
	}
	
	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return map.computeIfAbsent(key, mappingFunction);
	}
	
	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return map.computeIfPresent(key, remappingFunction);
	}
	
	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return map.compute(key, remappingFunction);
	}
	
	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return map.merge(key, value, remappingFunction);
	}
	
	protected V supergetOrDefault(Object key, V defaultValue) {
		return Map.super.getOrDefault(key, defaultValue);
	}
	
	protected void superforEach(BiConsumer<? super K, ? super V> action) {
		Map.super.forEach(action);
	}
	
	protected void superreplaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		Map.super.replaceAll(function);
	}
	
	protected V superputIfAbsent(K key, V value) {
		return Map.super.putIfAbsent(key, value);
	}
	
	protected boolean superremove(Object key, Object value) {
		return Map.super.remove(key, value);
	}
	
	protected boolean superreplace(K key, V oldValue, V newValue) {
		return Map.super.replace(key, oldValue, newValue);
	}
	
	protected V superreplace(K key, V value) {
		return Map.super.replace(key, value);
	}
	
	protected V supercomputeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return Map.super.computeIfAbsent(key, mappingFunction);
	}
	
	protected V supercomputeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return Map.super.computeIfPresent(key, remappingFunction);
	}
	
	protected V supercompute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return Map.super.compute(key, remappingFunction);
	}
	
	protected V supermerge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return Map.super.merge(key, value, remappingFunction);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("delegate", map);
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
