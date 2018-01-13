package space.util.keygen.map;

import space.util.indexmap.IndexMap;
import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;

import java.util.function.Supplier;

public class KeyMapGlobalGeneric<VALUE> extends KeyMap<VALUE> implements IKeyMapGeneralGeneric<VALUE> {
	
	public KeyMapGlobalGeneric() {
	}
	
	public KeyMapGlobalGeneric(IndexMap<VALUE> map) {
		super(map);
	}
	
	public KeyMapGlobalGeneric(IKeyGenerator gen) {
		super(gen);
	}
	
	public KeyMapGlobalGeneric(IndexMap<VALUE> map, IKeyGenerator gen) {
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
	public VALUE get(IKey<?> key) {
		check(key);
		return map.get(key.getID());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE put(IKey<?> key, VALUE v) {
		check(key);
		return map.put(key.getID(), v);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE remove(IKey<?> key) {
		check(key);
		return map.remove(key.getID());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE getOrDefault(IKey<?> key, VALUE def) {
		check(key);
		return map.getOrDefault(key.getID(), def);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE putIfAbsent(IKey<?> key, VALUE v) {
		check(key);
		return map.putIfAbsent(key.getID(), v);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE putIfAbsent(IKey<?> key, Supplier<? extends VALUE> v) {
		check(key);
		return map.putIfAbsent(key.getID(), v);
	}
	
	@Override
	public boolean replace(IKey<?> key, VALUE oldValue, VALUE newValue) {
		check(key);
		return map.replace(key.getID(), oldValue, newValue);
	}
	
	@Override
	public boolean replace(IKey<?> key, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		check(key);
		return map.replace(key.getID(), oldValue, newValue);
	}
	
	@Override
	public boolean remove(IKey<?> key, VALUE v) {
		check(key);
		return map.remove(key.getID(), v);
	}
	
	public static class KeyMapGlobalGenericWithGenerator<VALUE> extends KeyMapGlobalGeneric<VALUE> implements IKeyGenerator {
		
		public <T> IKey<T> generateKey() {
			return gen.generateKey();
		}
		
		public boolean isKeyOf(IKey<?> key) {
			return gen.isKeyOf(key);
		}
	}
}
