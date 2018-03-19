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
//		return indexMap.contains(index);
	}
	
	@Override
	public boolean contains(VALUE v) {
//		return indexMap.contains(v);
	}
	
	@Override
	public void add(VALUE v) {
		indexMap.add(v);
	}
	
	@Override
	public VALUE get(int index) {
	
	}
	
	@Override
	public IndexMapEntry<VALUE> getEntry(int index) {
	
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		return null;
	}
	
	@Override
	public int indexOf(VALUE v) {
		return 0;
	}
	
	@Override
	public VALUE remove(int index) {
		return null;
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
	
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
	
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
	
	}
	
	@Override
	public VALUE getOrDefault(int index, VALUE def) {
		return null;
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		return null;
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		return null;
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		return false;
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		return false;
	}
	
	@Override
	public boolean remove(VALUE v) {
		return false;
	}
	
	@Override
	public boolean remove(int index, VALUE v) {
		return false;
	}
	
	@Override
	public void clear() {
	
	}
	
	@Override
	public Collection<VALUE> values() {
		return null;
	}
	
	@Override
	public Collection<IndexMapEntry<VALUE>> table() {
		return null;
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
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("def", this.def);
		tsh.add("iterateOverDef", this.iterateOverDef);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	@FunctionalInterface
	public interface DefaultFunction<VALUE> {
		
		VALUE get(int index);

//		default int indexOf(VALUE v) {
//			return -1;
//		}
//
//		default void addAll(IndexMap<VALUE> indexMap) {
//
//		}
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
}
