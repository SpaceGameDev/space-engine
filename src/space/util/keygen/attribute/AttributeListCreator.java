package space.util.keygen.attribute;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.indexmap.IndexMapArrayWithDefault;
import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;
import space.util.keygen.IllegalKeyException;
import space.util.keygen.impl.DisposableKeyGenerator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.function.Supplier;

public class AttributeListCreator implements IAttributeListCreator, ToString {
	
	static {
		Copyable.manualEntry(AttributeList.class, AttributeList::copy);
	}
	
	public final IKeyGenerator gen;
	
	public AttributeListCreator() {
		this(new DisposableKeyGenerator(false));
	}
	
	public AttributeListCreator(IKeyGenerator gen) {
		this.gen = gen;
	}
	
	//delegate to gen
	@Override
	public <T> IKey<T> generateKey() {
		return gen.generateKey();
	}
	
	@Override
	public <T> IKey<T> generateKey(T def) {
		return gen.generateKey(def);
	}
	
	@Override
	public <T> IKey<T> generateKey(Supplier<T> def) {
		return gen.generateKey(def);
	}
	
	@Override
	public boolean isKeyOf(IKey<?> key) {
		return gen.isKeyOf(key);
	}
	
	//create
	@Override
	public IAttributeList create() {
		return new AttributeList();
	}
	
	public class AttributeList implements IAttributeList, ToString {
		
		public IndexMap<Object> indexMap;
		
		protected AttributeList() {
			this(gen instanceof DisposableKeyGenerator ? new IndexMapArrayWithDefault<>(((DisposableKeyGenerator) gen).counter, DEFAULT_OBJECT) : new IndexMapArrayWithDefault<>(DEFAULT_OBJECT));
		}
		
		private AttributeList(IndexMap<Object> indexMap) {
			this.indexMap = indexMap;
		}
		
		private AttributeList copy() {
			return new AttributeList(Copyable.copy(indexMap));
		}
		
		public void check(IKey<?> key) {
			if (gen != null && !gen.isKeyOf(key))
				throw new IllegalKeyException();
		}
		
		public <V> V correctDefault(V v, IKey<V> key) {
			return v != DEFAULT_OBJECT ? v : key.getDefaultValue();
		}
		
		//overrides
		@Override
		@SuppressWarnings("unchecked")
		public <V> V getDirect(IKey<V> key) {
			check(key);
			return (V) indexMap.get(key.getID());
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> V get(IKey<V> key) {
			check(key);
			return correctDefault((V) indexMap.get(key.getID()), key);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> void put(IKey<V> key, V v) {
			check(key);
			indexMap.put(key.getID(), v);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> void reset(IKey<V> key) {
			check(key);
			indexMap.put(key.getID(), DEFAULT_OBJECT);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> V getOrDefault(IKey<V> key, V def) {
			check(key);
			Object o = indexMap.get(key.getID());
			return o != DEFAULT_OBJECT ? (V) o : def;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean putIfDefault(IKey<V> key, V v) {
			check(key);
			return indexMap.replace(key.getID(), DEFAULT_OBJECT, v);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean putIfDefault(IKey<V> key, Supplier<? extends V> v) {
			check(key);
			return indexMap.replace(key.getID(), DEFAULT_OBJECT, v);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> V replace(IKey<V> key, V v) {
			check(key);
			return correctDefault((V) indexMap.put(key.getID(), v), key);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean replace(IKey<V> key, V oldValue, V newValue) {
			check(key);
			return indexMap.replace(key.getID(), oldValue, newValue);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean replace(IKey<V> key, V oldValue, Supplier<? extends V> newValue) {
			check(key);
			return indexMap.replace(key.getID(), oldValue, newValue);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean reset(IKey<V> key, V v) {
			check(key);
			return indexMap.remove(key.getID(), v);
		}
		
		//delegate
		@Override
		public int size() {
			return indexMap.size();
		}
		
		@Override
		public void clear() {
			indexMap.clear();
		}
		
		@Override
		public Iteratorable<Object> iterator() {
			return indexMap.iterator();
		}
		
		@Override
		public Iteratorable<IndexMapEntry<Object>> tableIterator() {
			return indexMap.tableIterator();
		}
		
		//toString
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("indexMap", indexMap);
			tsh.add("creator", AttributeListCreator.this);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("gen", this.gen);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
