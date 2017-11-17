package space.util.delegate.map;

import space.util.baseobjectOld.BaseObject;
import space.util.baseobjectOld.Copyable;
import space.util.delegate.collection.MergingCollection;
import space.util.delegate.iterator.Iteratorable;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class DefaultingMap<K, V> extends GetOverrideMap<K, V> {
	
	static {
		//noinspection unchecked,RedundantTypeArguments
		BaseObject.<DefaultingMap>initClass(DefaultingMap.class, d -> new DefaultingMap(Copyable.copy(d.map), Copyable.copy(d.def), d.iterateOverDef));
		//noinspection unchecked,RedundantTypeArguments
		BaseObject.<DefaultingMap.KeySet>initClass(DefaultingMap.KeySet.class, d -> d);
		//noinspection unchecked,RedundantTypeArguments
		BaseObject.<DefaultingMap.EntrySet>initClass(DefaultingMap.EntrySet.class, d -> d);
	}
	
	public Function<K, V> def;
	public boolean iterateOverDef;
	
	//no def iteration
	public DefaultingMap(Map<K, V> map, Function<K, V> def) {
		this(map, def, false);
	}
	
	//with def iteration
	public DefaultingMap(Map<K, V> map, DefaultFunctionWithIteration<K, V> def) {
		this(map, def, true);
	}
	
	public DefaultingMap(Map<K, V> map, Map<K, V> def) {
		this(map, makeDefaultFunctionFromMap(def), true);
	}
	
	//with boolean
	public DefaultingMap(Map<K, V> map, Map<K, V> def, boolean iterateOverDef) {
		this(map, makeDefaultFunctionFromMap(def), iterateOverDef);
	}
	
	public DefaultingMap(Map<K, V> map, Function<K, V> def, boolean iterateOverDef) {
		super(map);
		this.def = def;
		this.iterateOverDef = iterateOverDef;
	}
	
	public static <K, V> DefaultFunctionWithIteration<K, V> makeDefaultFunctionFromMap(Map<K, V> map) {
		return new DefaultFunctionWithIteration<K, V>() {
			
			@Override
			public V apply(K index) {
				return map.get(index);
			}
			
			@Override
			public void addKeySet(Collection<K> keySet) {
				keySet.addAll(map.keySet());
			}
			
			@Override
			public void addValues(Collection<V> valueSet) {
				valueSet.addAll(map.values());
			}
			
			@Override
			public void addEntrySet(Collection<Entry<K, V>> entrySet) {
				entrySet.addAll(map.entrySet());
			}
		};
	}
	
	//get
	@Override
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		V thisV = map.get(key);
		return thisV != null ? thisV : def.apply((K) key);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean containsKey(Object key) {
		return map.containsKey(key) || def.apply((K) key) != null;
	}
	
	//collections
	@Override
	public Set<K> keySet() {
		return allowIterateOverDefault() ? new KeySet() : map.keySet();
	}
	
	@Override
	public Collection<V> values() {
		if (!allowIterateOverDefault())
			return map.values();
		Collection<V> mapValues = map.values();
		Collection<V> defValues = new ArrayList<>();
		((DefaultFunctionWithIteration<K, V>) def).addValues(defValues);
		return MergingCollection.createWithAddCollection(mapValues, mapValues, defValues);
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		return allowIterateOverDefault() ? new EntrySet() : map.entrySet();
	}
	
	public boolean allowIterateOverDefault() {
		return iterateOverDef && def instanceof DefaultFunctionWithIteration;
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		tsh.add("def", this.def);
		tsh.add("iterateOverDef", this.iterateOverDef);
		return tsh.build();
	}
	
	//DefaultFunctionWithIteration
	public interface DefaultFunctionWithIteration<K, V> extends Function<K, V> {
		
		void addKeySet(Collection<K> keySet);
		
		void addValues(Collection<V> valueSet);
		
		void addEntrySet(Collection<Entry<K, V>> entrySet);
	}
	
	//KeySet
	protected class KeySet extends AbstractSet<K> implements BaseObject {
		
		public HashSet<K> set;
		
		public KeySet() {
			if (!(def instanceof DefaultingMap.DefaultFunctionWithIteration))
				throw new IllegalStateException();
			
			set = new HashSet<>();
			set.addAll(map.keySet());
			((DefaultFunctionWithIteration<K, V>) def).addKeySet(set);
		}
		
		@Override
		public int size() {
			return set.size();
		}
		
		@Override
		public boolean isEmpty() {
			return set.isEmpty();
		}
		
		@Override
		public boolean contains(Object o) {
			return set.contains(o);
		}
		
		@Override
		public Iterator<K> iterator() {
			return new Iteratorable<K>() {
				Iterator<K> iter = set.iterator();
				K curr;
				
				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}
				
				@Override
				public K next() {
					return curr = iter.next();
				}
				
				@Override
				public void remove() {
					set.remove(curr);
					DefaultingMap.this.remove(curr);
				}
			};
		}
		
		@Override
		public Object[] toArray() {
			return set.toArray();
		}
		
		@Override
		@SuppressWarnings("SuspiciousToArrayCall")
		public <T> T[] toArray(T[] a) {
			return set.toArray(a);
		}
		
		@Override
		public boolean add(K k) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean remove(Object o) {
			return DefaultingMap.this.remove(o) != null;
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			return set.containsAll(c);
		}
		
		@Override
		public boolean addAll(Collection<? extends K> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			boolean[] ret = new boolean[1];
			replaceAll((k, v) -> {
				if (c.contains(k))
					return v;
				ret[0] = true;
				return null;
			});
			return ret[0];
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			boolean[] ret = new boolean[1];
			replaceAll((k, v) -> {
				if (!c.contains(k))
					return v;
				ret[0] = true;
				return null;
			});
			return ret[0];
		}
		
		@Override
		public void clear() {
			DefaultingMap.this.clear();
		}
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("map", DefaultingMap.this.map);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	//EntrySet
	protected class EntrySet extends AbstractSet<Entry<K, V>> implements BaseObject {
		
		public HashSet<Entry<K, V>> set;
		
		public EntrySet() {
			if (!(def instanceof DefaultFunctionWithIteration))
				throw new IllegalStateException();
			
			set = new HashSet<>();
			set.addAll(map.entrySet());
			((DefaultFunctionWithIteration<K, V>) def).addEntrySet(set);
		}
		
		@Override
		public int size() {
			return set.size();
		}
		
		@Override
		public boolean isEmpty() {
			return set.isEmpty();
		}
		
		@Override
		public boolean contains(Object o) {
			return set.contains(o);
		}
		
		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new Iteratorable<Entry<K, V>>() {
				Iterator<Entry<K, V>> iter = set.iterator();
				Entry<K, V> curr;
				
				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}
				
				@Override
				public Entry<K, V> next() {
					return curr = iter.next();
				}
				
				@Override
				public void remove() {
					DefaultingMap.this.remove(curr.getKey());
				}
			};
		}
		
		@Override
		public Object[] toArray() {
			return set.toArray();
		}
		
		@Override
		@SuppressWarnings("SuspiciousToArrayCall")
		public <T> T[] toArray(T[] a) {
			return set.toArray(a);
		}
		
		@Override
		public boolean add(Entry<K, V> k) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean remove(Object o) {
			return o instanceof Entry<?, ?> && DefaultingMap.this.remove(((Entry) o).getKey()) != null;
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			return set.containsAll(c);
		}
		
		@Override
		public boolean addAll(Collection<? extends Entry<K, V>> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			boolean[] ret = new boolean[1];
			DummyEntry<K, V> dummy = new DummyEntry<>();
			replaceAll((k, v) -> {
				dummy.set(k, v);
				if (c.contains(dummy))
					return v;
				ret[0] = true;
				return null;
			});
			return ret[0];
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			boolean[] ret = new boolean[1];
			DummyEntry<K, V> dummy = new DummyEntry<>();
			replaceAll((k, v) -> {
				dummy.set(k, v);
				if (!c.contains(dummy))
					return v;
				ret[0] = true;
				return null;
			});
			return ret[0];
		}
		
		@Override
		public void clear() {
			DefaultingMap.this.clear();
		}
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("map", DefaultingMap.this.map);
			return tsh.build();
		}
	}
	
	protected static class DummyEntry<K, V> implements BaseObject, Entry<K, V> {
		
		static {
			//noinspection unchecked
			BaseObject.initClass(DummyEntry.class, DummyEntry::new, d -> new DummyEntry(d.k, d.v));
		}
		
		public K k;
		public V v;
		
		public DummyEntry() {
		}
		
		public DummyEntry(K k, V v) {
			set(k, v);
		}
		
		public void set(K k, V v) {
			this.k = k;
			this.v = v;
		}
		
		@Override
		public K getKey() {
			return k;
		}
		
		@Override
		public V getValue() {
			return v;
		}
		
		@Override
		public V setValue(V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(k) ^ Objects.hashCode(v);
		}
		
		@Override
		public boolean equals(Object o) {
			return o == this || (o instanceof Entry<?, ?> && Objects.equals(k, ((Entry) o).getKey()) && Objects.equals(v, ((Entry) o).getValue()));
		}
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("k", this.k);
			tsh.add("v", this.v);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
