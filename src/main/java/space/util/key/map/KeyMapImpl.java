package space.util.key.map;

import org.jetbrains.annotations.NotNull;
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
	public VALUE get(@NotNull Key<?> key) {
		check(key);
		return map.get(key.getID());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE put(@NotNull Key<?> key, VALUE v) {
		check(key);
		return map.put(key.getID(), v);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE remove(@NotNull Key<?> key) {
		check(key);
		return map.remove(key.getID());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE getOrDefault(@NotNull Key<?> key, VALUE def) {
		check(key);
		return map.getOrDefault(key.getID(), def);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE putIfAbsent(@NotNull Key<?> key, VALUE v) {
		check(key);
		return map.putIfAbsent(key.getID(), v);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE putIfAbsent(@NotNull Key<?> key, @NotNull Supplier<? extends VALUE> v) {
		check(key);
		return map.computeIfAbsent(key.getID(), v);
	}
	
	@Override
	public boolean replace(@NotNull Key<?> key, VALUE oldValue, VALUE newValue) {
		check(key);
		return map.replace(key.getID(), oldValue, newValue);
	}
	
	@Override
	public boolean replace(@NotNull Key<?> key, VALUE oldValue, @NotNull Supplier<? extends VALUE> newValue) {
		check(key);
		return map.replace(key.getID(), oldValue, newValue);
	}
	
	@Override
	public boolean remove(@NotNull Key<?> key, VALUE v) {
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
	
	@NotNull
	@Override
	public Collection<VALUE> values() {
		return map.values();
	}
	
	@NotNull
	@Override
	public Collection<? extends Entry> table() {
		return new ConvertingCollection.BiDirectional<>(map.table(), EntryImpl::new, entry -> map.getEntry(entry.getKey().getID()));
	}
	
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		tsh.add("gen", this.gen);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	private class EntryImpl implements KeyMap.Entry {
		
		public IndexMap.Entry<?> entry;
		
		public EntryImpl(IndexMap.Entry<?> entry) {
			this.entry = entry;
		}
		
		@NotNull
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
		
		@NotNull
		public <T> Key<T> generateKey() {
			return gen.generateKey();
		}
		
		@NotNull
		public <T> Key<T> generateKey(@NotNull Supplier<T> def) {
			return gen.generateKey(def);
		}
		
		@Override
		public Key<?> getKey(int id) {
			return gen.getKey(id);
		}
		
		public boolean isKeyOf(Key<?> key) {
			return gen.isKeyOf(key);
		}
		
		@NotNull
		public Collection<Key<?>> getKeys() {
			return gen.getKeys();
		}
		
		public int estimateKeyPoolMax() {
			return gen.estimateKeyPoolMax();
		}
	}
}
