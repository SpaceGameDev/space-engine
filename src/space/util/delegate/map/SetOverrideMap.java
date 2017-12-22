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
	
	//	@Override
//	public V remove(Object key) {
//		V ret = super.remove(key);
//		onModification.run();
//		return ret;
//	}
//
//	@Override
//	public void putAll(Map<? extends K, ? extends V> m) {
//		super.putAll(m);
//		if (m.size() != 0)
//			onModification.run();
//	}
//
//	@Override
//	public void clear() {
//		super.clear();
//		if (map.size() != 0)
//			onModification.run();
//	}
//
//	@Override
//	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
//		boolean[] mod = new boolean[1];
//		super.replaceAll((k, v) -> {
//			V ret = function.apply(k, v);
//			if (ret != v)
//				mod[0] = true;
//			return ret;
//		});
//		if (mod[0])
//			onModification.run();
//	}
//
//	@Override
//	public V putIfAbsent(K key, V value) {
//		boolean[] mod = new boolean[1];
//		V ret = super.computeIfAbsent(key, k -> {
//			mod[0] = true;
//			return value;
//		});
//		if (mod[0])
//			onModification.run();
//		return ret;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public boolean remove(Object key, Object value) {
//		if (get(key) != value)
//			return false;
//		remove(key);
//		onModification.run();
//		return true;
//	}
//
//	@Override
//	public boolean replace(K key, V oldValue, V newValue) {
//		if (get(key) != oldValue)
//			return false;
//		put(key, newValue);
//		onModification.run();
//		return true;
//	}
//
//	@Override
//	public V replace(K key, V value) {
//		V ret = super.replace(key, value);
//		onModification.run();
//		return ret;
//	}
//
//	@Override
//	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
//		boolean[] mod = new boolean[1];
//		V ret = super.computeIfAbsent(key, k -> {
//			mod[0] = true;
//			return mappingFunction.apply(k);
//		});
//		if (mod[0])
//			onModification.run();
//		return ret;
//	}
//
//	@Override
//	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
//		boolean[] mod = new boolean[1];
//		V ret = super.computeIfPresent(key, (k, v) -> {
//			mod[0] = true;
//			return remappingFunction.apply(k, v);
//		});
//		if (mod[0])
//			onModification.run();
//		return ret;
//	}
//
//	@Override
//	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
//
//	}
//
//	@Override
//	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
//		return supermerge(key, value, remappingFunction);
//	}
}
