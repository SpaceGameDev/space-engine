package space.util.keygen.map;

import space.util.indexmap.IndexMap;
import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;

import java.util.function.Supplier;

public class KeyMapKeyGeneric<VALUE> extends KeyMap<VALUE> implements IKeyMapKeyGeneric<VALUE> {
	
	public KeyMapKeyGeneric() {
	}
	
	public KeyMapKeyGeneric(IndexMap<VALUE> map) {
		super(map);
	}
	
	public KeyMapKeyGeneric(IKeyGenerator gen) {
		super(gen);
	}
	
	public KeyMapKeyGeneric(IndexMap<VALUE> map, IKeyGenerator gen) {
		super(map, gen);
	}
	
	//methods
	@Override
	public boolean contains(IKey<?> key) {
		check(key);
		return map.contains(key.getID());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <V extends VALUE> V get(IKey<V> key) {
		check(key);
		return (V) map.get(key.getID());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <V extends VALUE> V put(IKey<V> key, V v) {
		check(key);
		return (V) map.put(key.getID(), v);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <V extends VALUE> V remove(IKey<V> key) {
		check(key);
		return (V) map.remove(key.getID());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <V extends VALUE> V getOrDefault(IKey<V> key, V def) {
		check(key);
		return (V) map.getOrDefault(key.getID(), def);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <V extends VALUE> V putIfAbsent(IKey<V> key, V v) {
		check(key);
		return (V) map.putIfAbsent(key.getID(), v);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <V extends VALUE> V putIfAbsent(IKey<V> key, Supplier<? extends V> v) {
		check(key);
		return (V) map.putIfAbsent(key.getID(), v);
	}
	
	@Override
	public <V extends VALUE> boolean replace(IKey<V> key, V oldValue, V newValue) {
		check(key);
		return map.replace(key.getID(), oldValue, newValue);
	}
	
	@Override
	public <V extends VALUE> boolean replace(IKey<V> key, V oldValue, Supplier<? extends V> newValue) {
		check(key);
		return map.replace(key.getID(), oldValue, newValue);
	}
	
	@Override
	public <V extends VALUE> boolean remove(IKey<V> key, V v) {
		check(key);
		return map.remove(key.getID(), v);
	}
	
	public static class KeyMapKeyGenericWithGenerator<VALUE> extends KeyMapKeyGeneric<VALUE> implements IKeyGenerator {
		
		public <T> IKey<T> generateKey() {
			return gen.generateKey();
		}
		
		public boolean isKeyOf(IKey<?> key) {
			return gen.isKeyOf(key);
		}
	}
}
