package space.util.delegate.map;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ModificationAwareMap<K, V> extends DelegatingMap<K, V> {
	
	public Runnable onModification;
	
	public ModificationAwareMap(Map<K, V> map, Runnable onModification) {
		super(map);
		this.onModification = onModification;
	}
	
	@Override
	public V put(K key, V value) {
		V ret = super.put(key, value);
		onModification.run();
		return ret;
	}
	
	@Override
	public V remove(Object key) {
		V ret = super.remove(key);
		onModification.run();
		return ret;
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		super.putAll(m);
		if (m.size() != 0)
			onModification.run();
	}
	
	@Override
	public void clear() {
		super.clear();
		if (map.size() != 0)
			onModification.run();
	}
	
	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		boolean[] mod = new boolean[1];
		super.replaceAll((k, v) -> {
			V ret = function.apply(k, v);
			if (ret != v)
				mod[0] = true;
			return ret;
		});
		if (mod[0])
			onModification.run();
	}
	
	@Override
	public V putIfAbsent(K key, V value) {
		boolean[] mod = new boolean[1];
		V ret = super.computeIfAbsent(key, k -> {
			mod[0] = true;
			return value;
		});
		if (mod[0])
			onModification.run();
		return ret;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object key, Object value) {
		if (get(key) != value)
			return false;
		remove(key);
		onModification.run();
		return true;
	}
	
	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		if (get(key) != oldValue)
			return false;
		put(key, newValue);
		onModification.run();
		return true;
	}
	
	@Override
	public V replace(K key, V value) {
		V ret = super.replace(key, value);
		onModification.run();
		return ret;
	}
	
	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		boolean[] mod = new boolean[1];
		V ret = super.computeIfAbsent(key, k -> {
			mod[0] = true;
			return mappingFunction.apply(k);
		});
		if (mod[0])
			onModification.run();
		return ret;
	}
	
	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		boolean[] mod = new boolean[1];
		V ret = super.computeIfPresent(key, (k, v) -> {
			mod[0] = true;
			return remappingFunction.apply(k, v);
		});
		if (mod[0])
			onModification.run();
		return ret;
	}
	
	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		boolean[] mod = new boolean[1];
		V ret = super.compute(key, (k, v) -> {
			mod[0] = true;
			return remappingFunction.apply(k, v);
		});
		if (mod[0])
			onModification.run();
		return ret;
	}
	
	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		boolean[] mod = new boolean[1];
		V ret = super.merge(key, value, (v, v2) -> {
			mod[0] = true;
			return remappingFunction.apply(v, v2);
		});
		if (mod[0])
			onModification.run();
		return ret;
	}
}
