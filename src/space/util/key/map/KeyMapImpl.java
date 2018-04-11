package space.util.key.map;

import space.util.baseobject.ToString;
import space.util.delegate.collection.ConvertingCollection;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;
import space.util.key.IllegalKeyException;
import space.util.key.Key;
import space.util.key.KeyGenerator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.function.Supplier;

public class KeyMapImpl<VALUE> implements KeyMap<VALUE>, ToString {
	
	public IndexMap<VALUE> map;
	public KeyGenerator gen;
	
	public KeyMapImpl() {
		this(new IndexMapArray<>());
	}
	
	public KeyMapImpl(IndexMap<VALUE> map) {
		this.map = map;
	}
	
	public KeyMapImpl(KeyGenerator gen) {
		this(new IndexMapArray<>(), gen);
	}
	
	public KeyMapImpl(IndexMap<VALUE> map, KeyGenerator gen) {
		this.map = map;
		this.gen = gen;
	}
	
	public void check(Key<?> key) {
		if (gen != null && !gen.isKeyOf(key))
			throw new IllegalKeyException();
	}
	
	//methods
	@Override
	@SuppressWarnings("unchecked")
	public VALUE get(Key<?> key) {
		check(key);
		return map.get(key.getID());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE put(Key<?> key, VALUE v) {
		check(key);
		return map.put(key.getID(), v);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE remove(Key<?> key) {
		check(key);
		return map.remove(key.getID());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE getOrDefault(Key<?> key, VALUE def) {
		check(key);
		return map.getOrDefault(key.getID(), def);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE putIfAbsent(Key<?> key, VALUE v) {
		check(key);
		return map.putIfAbsent(key.getID(), v);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE putIfAbsent(Key<?> key, Supplier<? extends VALUE> v) {
		check(key);
		return map.computeIfAbsent(key.getID(), v);
	}
	
	@Override
	public boolean replace(Key<?> key, VALUE oldValue, VALUE newValue) {
		check(key);
		return map.replace(key.getID(), oldValue, newValue);
	}
	
	@Override
	public boolean replace(Key<?> key, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		check(key);
		return map.replace(key.getID(), oldValue, newValue);
	}
	
	@Override
	public boolean remove(Key<?> key, VALUE v) {
		check(key);
		return map.remove(key.getID(), v);
	}
	
	@Override
	public int size() {
		return map.size();
	}
	
	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public Collection<VALUE> iterator() {
		return map.values();
	}
	
	@Override
	public Collection<? extends Entry> tableIterator() {
		return new ConvertingCollection.BiDirectional<>(map.table(), KeyMapImpl.Entry::new, entry -> map.getEntry(entry.getKey().getID()));
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		tsh.add("gen", this.gen);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	private class Entry implements KeyMapEntry {
		
		public IndexMap.Entry<?> entry;
		
		public Entry(IndexMap.Entry entry) {
			this.entry = entry;
		}
		
		@Override
		public Key<?> getKey() {
			return gen.getKey(entry.getIndex());
		}
		
		@Override
		public Object getValue() {
			return entry.getValue();
		}
	}
	
	public static class KeyMapImplWithGenerator<VALUE> extends KeyMapImpl<VALUE> implements KeyGenerator {
		
		public <T> Key<T> generateKey() {
			return gen.generateKey();
		}
		
		public <T> Key<T> generateKey(Supplier<T> def) {
			return gen.generateKey(def);
		}
		
		@Override
		public Key<?> getKey(int id) {
			return gen.getKey(id);
		}
		
		public boolean isKeyOf(Key<?> key) {
			return gen.isKeyOf(key);
		}
		
		public Collection<Key<?>> getKeys() {
			return gen.getKeys();
		}
	}
}
