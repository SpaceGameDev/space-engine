package space.util.key.map;

import space.util.baseobject.ToString;
import space.util.delegate.collection.ConvertingCollection;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.indexmap.IndexMapArray;
import space.util.key.IKey;
import space.util.key.IKeyGenerator;
import space.util.key.IllegalKeyException;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.function.Supplier;

public class KeyMapImpl<VALUE> implements IKeyMap<VALUE>, ToString {
	
	public IndexMap<VALUE> map;
	public IKeyGenerator gen;
	
	public KeyMapImpl() {
		this(new IndexMapArray<>());
	}
	
	public KeyMapImpl(IndexMap<VALUE> map) {
		this.map = map;
	}
	
	public KeyMapImpl(IKeyGenerator gen) {
		this(new IndexMapArray<>(), gen);
	}
	
	public KeyMapImpl(IndexMap<VALUE> map, IKeyGenerator gen) {
		this.map = map;
		this.gen = gen;
	}
	
	public void check(IKey<?> key) {
		if (gen != null && !gen.isKeyOf(key))
			throw new IllegalKeyException();
	}
	
	//methods
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
		return new ConvertingCollection<>(map.table(), Entry::new, entry -> map.getEntry(entry.getKey().getID()));
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
		
		public IndexMapEntry<?> entry;
		
		public Entry(IndexMapEntry entry) {
			this.entry = entry;
		}
		
		@Override
		public IKey<?> getKey() {
			return gen.getKey(entry.getIndex());
		}
		
		@Override
		public Object getValue() {
			return entry.getValue();
		}
	}
	
	public static class KeyMapImplWithGenerator<VALUE> extends KeyMapImpl<VALUE> implements IKeyGenerator {
		
		public <T> IKey<T> generateKey() {
			return gen.generateKey();
		}
		
		public <T> IKey<T> generateKey(Supplier<T> def) {
			return gen.generateKey(def);
		}
		
		@Override
		public IKey<?> getKey(int id) {
			return gen.getKey(id);
		}
		
		public boolean isKeyOf(IKey<?> key) {
			return gen.isKeyOf(key);
		}
		
		public Collection<IKey<?>> getKeys() {
			return gen.getKeys();
		}
	}
}
