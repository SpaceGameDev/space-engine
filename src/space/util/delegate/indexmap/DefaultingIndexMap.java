package space.util.delegate.indexmap;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;
import space.util.string.toStringHelper.objects.TSHObjects.TSHObjectsInstance;

import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * If an index cannot be found in the delegating {@link IndexMap}, it will redirect to the def {@link IntFunction} trying to get the default value and return that.
 */
public class DefaultingIndexMap<VALUE> extends DelegatingIndexMap<VALUE> implements BaseObject {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(DefaultingIndexMap.class, d -> new DefaultingIndexMap(Copyable.copy(d.indexMap), Copyable.copy(d.def), d.iterateOverDef));
	}
	
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
		super(indexMap);
		this.def = def;
		this.iterateOverDef = iterateOverDef;
	}
	
	public static <VALUE> DefaultFunctionWithIteration<VALUE> makeDefaultFunctionFromIndexMap(IndexMap<VALUE> map) {
		return new DefaultFunctionWithIteration<VALUE>() {
			
			@Override
			public VALUE get(int index) {
				return map.get(index);
			}
			
			@Override
			public int indexOf(VALUE v) {
				return map.indexOf(v);
			}
			
			@Override
			public void addAll(IndexMap<VALUE> indexMap) {
				indexMap.putAll(map);
			}
		};
	}
	
	//get
	@Override
	public VALUE get(int index) {
		VALUE thisV = indexMap.get(index);
		return thisV != null ? thisV : def.get(index);
	}
	
	@Override
	@SuppressWarnings("ConstantConditions")
	public int indexOf(VALUE v) {
		int thisV = indexMap.indexOf(v);
		return iterateOverDef && def instanceof DefaultFunctionWithIteration<?> && thisV != -1 ? thisV : ((DefaultFunctionWithIteration<VALUE>) def).indexOf(v);
	}
	
	@Override
	public VALUE[] toArray() {
		return super.toArray();
	}
	
	@Override
	public VALUE[] toArray(VALUE[] array) {
		return super.toArray(array);
	}
	
	//all delegating to IndexMap default methods
	@Override
	public boolean contains(int index) {
		return supercontains(index);
	}
	
	@Override
	public void putAllReplace(IndexMap<VALUE> indexMap) {
		superputAllReplace(indexMap);
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
		superputAllIfAbsent(indexMap);
	}
	
	@Override
	public VALUE getOrDefault(int index, VALUE def) {
		return supergetOrDefault(index, def);
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		return superputIfAbsent(index, v);
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		return superputIfAbsent(index, v);
	}
	
	@Override
	public VALUE replace(int index, VALUE newValue) {
		return superreplace(index, newValue);
	}
	
	@Override
	public VALUE replace(int index, Supplier<? extends VALUE> newValue) {
		return superreplace(index, newValue);
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		return superreplace(index, oldValue, newValue);
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		return superreplace(index, oldValue, newValue);
	}
	
	@Override
	public boolean remove(int index, VALUE v) {
		return superremove(index, v);
	}
	
	@Override
	public void forEach(Consumer<? super VALUE> action) {
		superforEach(action);
	}
	
	@Override
	public Spliterator<VALUE> spliterator() {
		return superspliterator();
	}
	
	//iterators
	@Override
	public Iteratorable<VALUE> iterator() {
		if (!iterateOverDef || !(def instanceof DefaultFunctionWithIteration<?>))
			return indexMap.iterator();
		
		IndexMap<VALUE> map = new IndexMapArray<>();
		((DefaultFunctionWithIteration<VALUE>) def).addAll(map);
		map.putAll(indexMap);
		return map.iterator();
	}
	
	@Override
	public Iteratorable<IndexMapEntry<VALUE>> tableIterator() {
		if (!iterateOverDef || !(def instanceof DefaultFunctionWithIteration<?>))
			return indexMap.tableIterator();
		
		IndexMapArray<VALUE> map = new IndexMapArray<>();
		((DefaultFunctionWithIteration<VALUE>) def).addAll(map);
		map.putAll(indexMap);
		return new Iteratorable<IndexMapEntry<VALUE>>() {
			Iteratorable<IndexMapEntry<VALUE>> iter = map.tableIterator();
			IndexMapEntry<VALUE> curr;
			
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}
			
			@Override
			public IndexMapEntry<VALUE> next() {
				if (!iter.hasNext())
					throw new NoSuchElementException();
				curr = iter.next();
				
				return new IndexMapEntry<VALUE>() {
					IndexMapEntry<VALUE> entry = curr;
					VALUE v = entry.getValue();
					
					@Override
					public int getIndex() {
						return entry.getIndex();
					}
					
					@Override
					public VALUE getValue() {
						return v;
					}
					
					@Override
					public void setValue(VALUE v) {
						this.v = v;
						put(getIndex(), v);
					}
				};
			}
			
			@Override
			public void remove() {
				DefaultingIndexMap.this.remove(curr.getIndex());
			}
		};
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("def", this.def);
		tsh.add("iterateOverDef", this.iterateOverDef);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	@FunctionalInterface
	public interface DefaultFunction<VALUE> {
		
		VALUE get(int index);
	}
	
	public interface DefaultFunctionWithIteration<VALUE> extends DefaultFunction<VALUE> {
		
		int indexOf(VALUE v);
		
		/**
		 * being used when iterating over the def Object
		 */
		void addAll(IndexMap<VALUE> indexMap);
	}
}
