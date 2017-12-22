package space.util.delegate.map;

import space.util.delegate.collection.ReferenceCollection;
import space.util.delegate.iterator.Iteratorable;
import space.util.delegate.util.ReferenceUtil;

import java.lang.ref.Reference;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Remaps the Value V to a {@link Reference} of type V. These References are created by the {@link ReferenceMap#refCreator Reference Creator} supplied with the Constructor or directly set.<br>
 * <br>
 * If the Key K (and NOT the Value) should be a {@link java.lang.ref.WeakReference}, refer to {@link java.util.WeakHashMap}.
 */
public class ReferenceMap<K, V> implements Map<K, V> {
	
	public Map<K, Reference<? extends V>> map;
	public Function<V, ? extends Reference<? extends V>> refCreator;
	
	public ReferenceMap(Map<K, Reference<? extends V>> map) {
		this(map, ReferenceUtil.defRefCreator());
	}
	
	public ReferenceMap(Map<K, Reference<? extends V>> map, Function<V, ? extends Reference<? extends V>> refCreator) {
		this.map = map;
		this.refCreator = refCreator;
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
		for (Reference<? extends V> e : map.values())
			if (e != null && e.get() == value)
				return true;
		return false;
	}
	
	@Override
	public V get(Object key) {
		return ReferenceUtil.getSafe(map.get(key));
	}
	
	@Override
	public V put(K key, V value) {
		return ReferenceUtil.getSafe(map.put(key, refCreator.apply(value)));
	}
	
	@Override
	public V remove(Object key) {
		return ReferenceUtil.getSafe(map.remove(key));
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> e : m.entrySet())
			put(e.getKey(), e.getValue());
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
		return new ReferenceCollection<>(map.values(), refCreator);
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, Reference<? extends V>>> set = map.entrySet();
		return new AbstractSet<Entry<K, V>>() {
			@Override
			public Iterator<Entry<K, V>> iterator() {
				Iterator<Entry<K, Reference<? extends V>>> iter = set.iterator();
				return new Iteratorable<Entry<K, V>>() {
					@Override
					public boolean hasNext() {
						return iter.hasNext();
					}
					
					@Override
					public Entry<K, V> next() {
						Entry<K, Reference<? extends V>> entry = iter.next();
						return new Entry<K, V>() {
							//making a strong reference to prevent gc interference while operating on the object
							V v = ReferenceUtil.getSafe(entry.getValue());
							
							@Override
							public K getKey() {
								return entry.getKey();
							}
							
							@Override
							public V getValue() {
								return v;
							}
							
							@Override
							public V setValue(V value) {
								V old = v;
								v = value;
								entry.setValue(refCreator.apply(v));
								return old;
							}
						};
					}
				};
			}
			
			@Override
			public int size() {
				return set.size();
			}
		};
	}
	
	@Override
	public V getOrDefault(Object key, V defaultValue) {
		//noinspection SuspiciousMethodCalls
		Reference<? extends V> ret = map.get(key);
		if (ret == null)
			return defaultValue;
		V v = ret.get();
		if (v == null)
			return defaultValue;
		return v;
	}
	
	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		map.forEach((k, reference) -> action.accept(k, ReferenceUtil.getSafe(reference)));
	}
	
	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		map.replaceAll((k, reference) -> {
			V o = ReferenceUtil.getSafe(reference);
			V n = function.apply(k, o);
			return o == n ? reference : refCreator.apply(n);
		});
	}
	
	@Override
	public V putIfAbsent(K key, V value) {
		return ReferenceUtil.getSafe(map.computeIfAbsent(key, k -> refCreator.apply(value)));
	}
	
	@Override
	public boolean remove(Object key, Object value) {
		return map.remove(key, value);
	}
	
	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		Reference<? extends V> curr = map.get(key);
		if (curr == null)
			return false;
		V currV = curr.get();
		if (currV != oldValue)
			return false;
		map.put(key, refCreator.apply(newValue));
		return true;
	}
	
	@Override
	public V replace(K key, V value) {
		return ReferenceUtil.getSafe(map.computeIfAbsent(key, k -> refCreator.apply(value)));
	}
	
	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return ReferenceUtil.getSafe(map.computeIfAbsent(key, k -> refCreator.apply(mappingFunction.apply(k))));
	}
	
	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return ReferenceUtil.getSafe(map.computeIfPresent(key, (k, reference) -> {
			V o = ReferenceUtil.getSafe(reference);
			V n = remappingFunction.apply(k, o);
			return o == n ? reference : refCreator.apply(n);
		}));
	}
	
	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return ReferenceUtil.getSafe(map.compute(key, (k, reference) -> {
			V o = ReferenceUtil.getSafe(reference);
			V n = remappingFunction.apply(k, o);
			return o == n ? reference : refCreator.apply(n);
		}));
	}
	
	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		V o = get(key);
		V n = remappingFunction.apply(o, value);
		if (o != n)
			put(key, n);
		return n;
	}
	
	@Override
	@SuppressWarnings("SimplifiableIfStatement")
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ReferenceMap))
			return false;
		
		return map.equals(((ReferenceMap<?, ?>) o).map);
	}
	
	@Override
	public int hashCode() {
		return map.hashCode();
	}
}
