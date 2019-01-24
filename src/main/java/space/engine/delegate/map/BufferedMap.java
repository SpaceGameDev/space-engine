package space.engine.delegate.map;

import org.jetbrains.annotations.NotNull;
import space.engine.string.toStringHelper.ToStringHelper;
import space.engine.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A {@link BufferedMap} is a Map having a {@link BufferedMap#buffer} of entries, which can be flushed into another map with {@link BufferedMap#setSink(Map) setSink(Map)}.
 * The {@link BufferedMap} is also synchronized internally.<br>
 * <p>
 * Usage example:
 * This allows for pre-initialization {@link Map Maps} to exist and catch all data, while the Runtime {@link Map} is being created.
 * When initialization of the Runtime {@link Map} is done, all data can be flushed to it with {@link BufferedMap#setSink(Map)}.
 */
public class BufferedMap<K, V> extends DelegatingMap<K, V> {
	
	public Map<K, V> buffer;
	
	public BufferedMap(Map<K, V> buffer) {
		super(null);
		this.buffer = buffer;
	}
	
	//sink
	public synchronized void setSink(Map<K, V> sink) {
		map = sink;
		sink.putAll(buffer);
		buffer = null;
	}
	
	public synchronized boolean hasSink() {
		return map != null;
	}
	
	//delegate
	@Override
	public synchronized int size() {
		return hasSink() ? super.size() : buffer.size();
	}
	
	@Override
	public synchronized boolean isEmpty() {
		return hasSink() ? super.isEmpty() : buffer.isEmpty();
	}
	
	@Override
	public synchronized boolean containsKey(Object key) {
		return hasSink() ? super.containsKey(key) : buffer.containsKey(key);
	}
	
	@Override
	public synchronized boolean containsValue(Object value) {
		return hasSink() ? super.containsValue(value) : buffer.containsValue(value);
	}
	
	@Override
	public synchronized V get(Object key) {
		return hasSink() ? super.get(key) : buffer.get(key);
	}
	
	@Override
	public synchronized V put(K key, V value) {
		return hasSink() ? super.put(key, value) : buffer.put(key, value);
	}
	
	@Override
	public synchronized V remove(Object key) {
		return hasSink() ? super.remove(key) : buffer.remove(key);
	}
	
	@Override
	public synchronized void putAll(@NotNull Map<? extends K, ? extends V> m) {
		if (hasSink())
			super.putAll(m);
		else
			buffer.putAll(m);
	}
	
	@Override
	public synchronized void clear() {
		if (hasSink())
			super.clear();
		else
			buffer.clear();
	}
	
	@NotNull
	@Override
	public synchronized Set<K> keySet() {
		return hasSink() ? super.keySet() : buffer.keySet();
	}
	
	@NotNull
	@Override
	public synchronized Collection<V> values() {
		return hasSink() ? super.values() : buffer.values();
	}
	
	@NotNull
	@Override
	public synchronized Set<Entry<K, V>> entrySet() {
		return hasSink() ? super.entrySet() : buffer.entrySet();
	}
	
	@Override
	public synchronized V getOrDefault(Object key, V defaultValue) {
		return hasSink() ? super.getOrDefault(key, defaultValue) : buffer.getOrDefault(key, defaultValue);
	}
	
	@Override
	public synchronized void forEach(BiConsumer<? super K, ? super V> action) {
		if (hasSink())
			super.forEach(action);
		else
			buffer.forEach(action);
	}
	
	@Override
	public synchronized void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		if (hasSink())
			super.replaceAll(function);
		else
			buffer.replaceAll(function);
	}
	
	@Override
	public synchronized V putIfAbsent(K key, V value) {
		return hasSink() ? super.putIfAbsent(key, value) : buffer.putIfAbsent(key, value);
	}
	
	@Override
	public synchronized boolean remove(Object key, Object value) {
		return hasSink() ? super.remove(key, value) : buffer.remove(key, value);
	}
	
	@Override
	public synchronized boolean replace(K key, V oldValue, V newValue) {
		return hasSink() ? super.replace(key, oldValue, newValue) : buffer.replace(key, oldValue, newValue);
	}
	
	@Override
	public synchronized V replace(K key, V value) {
		return hasSink() ? super.replace(key, value) : buffer.replace(key, value);
	}
	
	@Override
	public synchronized V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return hasSink() ? super.computeIfAbsent(key, mappingFunction) : buffer.computeIfAbsent(key, mappingFunction);
	}
	
	@Override
	public synchronized V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return hasSink() ? super.computeIfPresent(key, remappingFunction) : buffer.computeIfPresent(key, remappingFunction);
	}
	
	@Override
	public synchronized V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return hasSink() ? super.compute(key, remappingFunction) : buffer.compute(key, remappingFunction);
	}
	
	@Override
	public synchronized V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return hasSink() ? super.merge(key, value, remappingFunction) : buffer.merge(key, value, remappingFunction);
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		tsh.add("buffer", this.buffer);
		return tsh.build();
	}
}
