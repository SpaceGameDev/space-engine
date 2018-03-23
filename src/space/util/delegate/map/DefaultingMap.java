package space.util.delegate.map;

import space.util.baseobject.ToString;
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
import java.util.Set;
import java.util.function.Function;

/**
 * The {@link DefaultingMap} tries to get a value from the {@link DefaultingMap#map}, and when no value has been found, it will return the value from the {@link DefaultingMap#def};
 * <p>
 * {@link CachingMap} is threadsafe, if the internal {@link CachingMap#map} is threadsafe.
 */
public class DefaultingMap<K, V> extends GetOverrideMap<K, V> {
	
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
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean containsKey(Object key) {
		return map.containsKey(key) || def.apply((K) key) != null;
	}
	
	//get
	@Override
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		V thisV = map.get(key);
		return thisV != null ? thisV : def.apply((K) key);
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
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		tsh.add("def", this.def);
		tsh.add("iterateOverDef", this.iterateOverDef);
		return tsh.build();
	}
	
	public boolean allowIterateOverDefault() {
		return iterateOverDef && def instanceof DefaultFunctionWithIteration;
	}
	
	public static <K, V> DefaultFunctionWithIteration<K, V> makeDefaultFunctionFromMap(Map<K, V> map) {
		return new DefaultFunctionWithIteration<>() {
			
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
	
	//DefaultFunctionWithIteration
	public interface DefaultFunctionWithIteration<K, V> extends Function<K, V> {
		
		@Override
		V apply(K k);
		
		void addKeySet(Collection<K> keySet);
		
		void addValues(Collection<V> valueSet);
		
		void addEntrySet(Collection<Entry<K, V>> entrySet);
	}
	
	//KeySet
	protected class KeySet extends AbstractSet<K> implements ToString {
		
		public HashSet<K> set;
		
		public KeySet() {
			if (!(def instanceof DefaultingMap.DefaultFunctionWithIteration))
				throw new IllegalStateException();
			
			set = new HashSet<>();
			set.addAll(map.keySet());
			((DefaultFunctionWithIteration<K, V>) def).addKeySet(set);
		}
		
		@Override
		public Iterator<K> iterator() {
			return new Iteratorable<>() {
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
		public int size() {
			return set.size();
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("map", DefaultingMap.this.map);
			return tsh.build();
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
		public void clear() {
			DefaultingMap.this.clear();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	//EntrySet
	protected class EntrySet extends AbstractSet<Entry<K, V>> implements ToString {
		
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
			return new Iteratorable<>() {
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
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("map", DefaultingMap.this.map);
			return tsh.build();
		}
	}
	
	protected static class DummyEntry<K, V> implements ToString, Entry<K, V> {
		
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
		public String toString() {
			return toString0();
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("k", this.k);
			tsh.add("v", this.v);
			return tsh.build();
		}
	}
}
