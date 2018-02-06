package space.util.keygen.map;

import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.indexmap.IndexMapArray;
import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;
import space.util.keygen.IllegalKeyException;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.function.Supplier;

public class KeyMapImpl<VALUE> implements IKeyMap<VALUE>, space.util.baseobject.ToString {
	
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
	public Iteratorable<VALUE> iterator() {
		return map.iterator();
	}
	
	@Override
	public Iteratorable<IndexMapEntry<VALUE>> tableIterator() {
		return map.tableIterator();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		tsh.add("gen", this.gen);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
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
	}
}
