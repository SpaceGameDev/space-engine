package space.util.delegate.indexmap;

import space.util.baseobject.ToString;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * The {@link DefaultingIndexMap} tries to get a value from the {@link DefaultingIndexMap#indexMap}, and when no value has been found, it will return the value from the {@link DefaultingIndexMap#def};
 */
public class DefaultingIndexMap<VALUE> implements IndexMap<VALUE>, ToString {
	
	public IndexMap<VALUE> indexMap;
	public DefaultFunction<VALUE> def;
	public boolean iterateOverDef;
	
	//no def iteration
	public DefaultingIndexMap(IndexMap<VALUE> indexMap, IntFunction<VALUE> def) {
		this(indexMap, def::apply, false);
	}
	
	public DefaultingIndexMap(IndexMap<VALUE> indexMap, DefaultFunction<VALUE> def) {
		this(indexMap, def, false);
	}
	
	//with def iteration
	public DefaultingIndexMap(IndexMap<VALUE> indexMap, DefaultFunctionWithIteration<VALUE> def) {
		this(indexMap, def, true);
	}
	
	public DefaultingIndexMap(IndexMap<VALUE> indexMap, IndexMap<VALUE> def) {
		this(indexMap, makeDefaultFunctionFromIndexMap(def), true);
	}
	
	//with boolean
	public DefaultingIndexMap(IndexMap<VALUE> indexMap, IndexMap<VALUE> def, boolean iterateOverDef) {
		this(indexMap, makeDefaultFunctionFromIndexMap(def), iterateOverDef);
	}
	
	public DefaultingIndexMap(IndexMap<VALUE> indexMap, DefaultFunction<VALUE> def, boolean iterateOverDef) {
		this.indexMap = indexMap;
		this.def = def;
		this.iterateOverDef = iterateOverDef;
	}
	
	@Override
	public boolean isExpandable() {
		return indexMap.isExpandable();
	}
	
	@Override
	public int size() {
		return indexMap.size();
	}
	
	@Override
	public boolean isEmpty() {
		return indexMap.isEmpty();
	}
	
	@Override
	public boolean contains(int index) {
		return indexMap.contains(index) || def.contains(index);
	}
	
	@Override
	public boolean contains(VALUE v) {
		return indexMap.contains(v) || def.contains(v);
	}
	
	@Override
	public void add(VALUE v) {
		indexMap.add(v);
	}
	
	@Override
	public VALUE get(int index) {
		VALUE v = indexMap.get(index);
		if (v != null)
			return v;
		
		return def.get(index);
	}
	
	@Override
	public IndexMapEntry<VALUE> getEntry(int index) {
		return new IndexMapEntry<>() {
			IndexMapEntry<VALUE> entry = indexMap.getEntry(index);
			
			@Override
			public int getIndex() {
				return entry.getIndex();
			}
			
			@Override
			public VALUE getValue() {
				VALUE value = entry.getValue();
				return value != null ? value : def.get(index);
			}
			
			@Override
			public void setValue(VALUE v) {
				entry.setValue(v);
			}
		};
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		return indexMap.put(index, v);
	}
	
	@Override
	public int indexOf(VALUE v) {
		int ret = indexMap.indexOf(v);
		return ret != -1 ? ret : def.indexOf(v);
	}
	
	@Override
	public VALUE remove(int index) {
		return indexMap.remove(index);
	}
	
	@Override
	public VALUE[] toArray() {
		return new VALUE[0];
	}
	
	@Override
	public VALUE[] toArray(VALUE[] array) {
		return new VALUE[0];
	}
	
	@Override
	public void addAll(Collection<VALUE> coll) {
		indexMap.addAll(coll);
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		indexMap.putAll(indexMap);
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
		indexMap.putAllIfAbsent(indexMap);
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		return indexMap.putIfAbsent(index, () -> {
			VALUE value = def.get(index);
			return value != null ? value : v;
		});
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		return indexMap.putIfAbsent(index, () -> {
			VALUE value = def.get(index);
			return value != null ? value : v.get();
		});
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		return indexMap.replace(index, oldValue, newValue);
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		return indexMap.replace(index, oldValue, newValue);
	}
	
	@Override
	public boolean remove(VALUE v) {
		return indexMap.remove(v);
	}
	
	@Override
	public boolean remove(int index, VALUE v) {
		return indexMap.remove(index, v);
	}
	
	@Override
	public void clear() {
		indexMap.clear();
	}
	
	@Override
	public Collection<VALUE> values() {
		return null;
	}
	
	@Override
	public Collection<IndexMapEntry<VALUE>> table() {
		return null;
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("def", this.def);
		tsh.add("iterateOverDef", this.iterateOverDef);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static <VALUE> DefaultFunction<VALUE> makeDefaultFunctionFromIndexMap(IndexMap<VALUE> map) {
		return new DefaultFunction<>() {
			
			@Override
			public VALUE get(int index) {
				return map.get(index);
			}

//			@Override
//			public int indexOf(VALUE v) {
//				return map.indexOf(v);
//			}
//
//			@Override
//			public void addAll(IndexMap<VALUE> indexMap) {
//				indexMap.putAll(map);
//			}
		};
	}
	
	@FunctionalInterface
	public interface DefaultFunction<VALUE> {
		
		VALUE get(int index);
		
		default boolean contains(int index) {
			return false;
		}
		
		default boolean contains(VALUE v) {
			return false;
		}
		
		default int indexOf(VALUE v) {
			return -1;
		}

//		default void addAll(IndexMap<VALUE> indexMap) {
//
//		}
	}
	
	//	//get
//	@Override
//	public VALUE get(int index) {
//		VALUE thisV = indexMap.get(index);
//		return thisV != null ? thisV : def.get(index);
//	}
//
//	@Override
//	@SuppressWarnings("ConstantConditions")
//	public int indexOf(VALUE v) {
//		int thisV = indexMap.indexOf(v);
//		return iterateOverDef && def instanceof DefaultFunctionWithIteration<?> && thisV != -1 ? thisV : ((DefaultFunctionWithIteration<VALUE>) def).indexOf(v);
//	}
//
//	@Override
//	public VALUE[] toArray() {
//		return super.toArray();
//	}
//
//	@Override
//	public VALUE[] toArray(VALUE[] array) {
//		return super.toArray(array);
//	}
//
//	//all delegating to IndexMap default methods
//	@Override
//	public boolean contains(int index) {
//		return supercontains(index);
//	}
//
//	@Override
//	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
//		superputAllIfAbsent(indexMap);
//	}
//
//	@Override
//	public VALUE getOrDefault(int index, VALUE def) {
//		return supergetOrDefault(index, def);
//	}
//
//	@Override
//	public VALUE putIfAbsent(int index, VALUE v) {
//		return superputIfAbsent(index, v);
//	}
//
//	@Override
//	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
//		return superputIfAbsent(index, v);
//	}
//
//	@Override
//	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
//		return superreplace(index, oldValue, newValue);
//	}
//
//	@Override
//	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
//		return superreplace(index, oldValue, newValue);
//	}
//
//	@Override
//	public boolean remove(int index, VALUE v) {
//		return superremove(index, v);
//	}
//
//	@Override
//	public void forEach(Consumer<? super VALUE> action) {
//		superforEach(action);
//	}
//
//	@Override
//	public Spliterator<VALUE> spliterator() {
//		return superspliterator();
//	}
//
//	//iterators
//	@Override
//	public Collection<VALUE> values() {
//		if (!iterateOverDef || !(def instanceof DefaultFunctionWithIteration<?>))
//			return indexMap.values();
//
//		IndexMap<VALUE> map = new IndexMapArray<>();
//		((DefaultFunctionWithIteration<VALUE>) def).addAll(map);
//		map.putAll(indexMap);
//		return map.values();
//	}
//
//	@Override
//	public Collection<IndexMapEntry<VALUE>> table() {
//		if (!iterateOverDef || !(def instanceof DefaultFunctionWithIteration<?>))
//			return indexMap.table();
//
//		IndexMapArray<VALUE> map = new IndexMapArray<>();
//		((DefaultFunctionWithIteration<VALUE>) def).addAll(map);
//		map.putAll(indexMap);
//		return new Iteratorable<>() {
//			Collection<IndexMapEntry<VALUE>> iter = map.table();
//			IndexMapEntry<VALUE> curr;
//
//			@Override
//			public boolean hasNext() {
//				return iter.hasNext();
//			}
//
//			@Override
//			public IndexMapEntry<VALUE> next() {
//				if (!iter.hasNext())
//					throw new NoSuchElementException();
//				curr = iter.next();
//
//				return new IndexMapEntry<>() {
//					IndexMapEntry<VALUE> entry = curr;
//					VALUE v = entry.getValue();
//
//					@Override
//					public int getIndex() {
//						return entry.getIndex();
//					}
//
//					@Override
//					public VALUE getValue() {
//						return v;
//					}
//
//					@Override
//					public void setValue(VALUE v) {
//						this.v = v;
//						put(getIndex(), v);
//					}
//				};
//			}
//
//			@Override
//			public void remove() {
//				DefaultingIndexMap.this.remove(curr.getIndex());
//			}
//		};
//	}
}
