package space.util.delegate.indexmap;

import space.util.baseobject.ToString;
import space.util.delegate.collection.ConvertingCollection;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ConvertingIndexMap<F, T> implements IndexMap<T>, ToString {
	
	public IndexMap<F> indexMap;
	
	public ConvertingIndexMap(IndexMap<F> indexMap) {
		this.indexMap = indexMap;
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("indexMap", this.indexMap);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static class OneDirectionalUnmodifiable<F, T> extends ConvertingIndexMap<F, T> {
		
		public Function<F, T> remap;
		
		public OneDirectionalUnmodifiable(IndexMap<F> indexMap, Function<F, T> remap) {
			super(indexMap);
			this.remap = remap;
		}
		
		@Override
		public boolean isExpandable() {
			return false;
		}
		
		@Override
		public int size() {
			return indexMap.size();
		}
		
		@Override
		public boolean contains(int index) {
			return indexMap.contains(index);
		}
		
		@Override
		public boolean contains(T v) {
			for (F f : indexMap.values())
				if (Objects.equals(remap.apply(f), v))
					return true;
			return false;
		}
		
		@Override
		public void add(T v) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T get(int index) {
			return remap.apply(indexMap.get(index));
		}
		
		@Override
		public boolean isEmpty() {
			return indexMap.isEmpty();
		}
		
		@Override
		public T put(int index, T v) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public int indexOf(T v) {
			Iterator<F> iter = indexMap.values().iterator();
			for (int i = 0; iter.hasNext(); i++)
				if (Objects.equals(remap.apply(iter.next()), v))
					return i;
			return -1;
		}
		
		@Override
		public T remove(int index) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T[] toArray() {
			F[] org = indexMap.toArray();
			//noinspection unchecked
			T[] ret = (T[]) new Object[org.length];
			for (int i = 0; i < org.length; i++)
				ret[i] = remap.apply(org[i]);
			return ret;
		}
		
		@Override
		public IndexMapEntry<T> getEntry(int index) {
			return new Entry(indexMap.getEntry(index));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public T[] toArray(T[] a) {
			F[] org = indexMap.toArray();
			if (a.length < org.length) {
				//new instance
				T[] ret = (T[]) Array.newInstance(a.getClass(), org.length);
				for (int i = 0; i < org.length; i++)
					ret[i] = remap.apply((org[i]));
				return ret;
			}
			
			//use existing
			for (int i = 0; i < org.length; i++)
				a[i] = remap.apply((org[i]));
			a[org.length] = null;
			return a;
		}
		
		@Override
		public void addAll(Collection<T> coll) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public void putAll(IndexMap<T> indexMap) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public void putAllIfAbsent(IndexMap<T> indexMap) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T getOrDefault(int index, T def) {
			T apply = remap.apply(indexMap.get(index));
			return apply == null ? def : apply;
		}
		
		@Override
		public T putIfAbsent(int index, T v) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T putIfAbsent(int index, Supplier<? extends T> v) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public boolean replace(int index, T oldValue, T newValue) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public boolean replace(int index, T oldValue, Supplier<? extends T> newValue) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public boolean remove(T v) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public boolean remove(int index, T v) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public void clear() {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public Collection<T> values() {
			return ConvertingCollection.createConvertingOneDirectionalUnmodifiable(indexMap.values(), remap);
		}
		
		@Override
		public Collection<IndexMapEntry<T>> table() {
			return ConvertingCollection.createConvertingOneDirectionalUnmodifiable(indexMap.table(), Entry::new);
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("indexMap", this.indexMap);
			tsh.add("remap", this.remap);
			return tsh.build();
		}
		
		public class Entry implements IndexMap.IndexMapEntry<T> {
			
			public IndexMapEntry<F> entry;
			
			public Entry(IndexMapEntry<F> entry) {
				this.entry = entry;
			}
			
			@Override
			public int getIndex() {
				return entry.getIndex();
			}
			
			@Override
			public T getValue() {
				return remap.apply(entry.getValue());
			}
			
			public void setValue(T v) {
				throw new UnsupportedOperationException("unmodifiable");
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException("unmodifiable");
			}
		}
	}
	
	public static class BiDirectionalUnmodifiable {
	
	}
	
	public static class BiDirectional {
	
	}
	
	public static class BiDirectionalSpareReverse {
	
	}

//	public Function<F, T> remap;
//	public Function<T, F> reverse;
//	public PutFunction<T> put;
//	public boolean modifiable;
//	/**
//	 * By default this is true. It causes null-values to be returned directly instead of remapping it first. Disable if you remap null to something else.
//	 */
//	public boolean fastNullReturn;
//
//	public ConvertingIndexMap(IndexMap<F> indexMap, Function<F, T> remap) {
//		this(indexMap, remap, true);
//	}
//
//	public ConvertingIndexMap(IndexMap<F> indexMap, Function<F, T> remap, boolean fastNullReturn) {
//		this(indexMap, remap, null, new PutFunction<T>() {
//			@Override
//			public T put(int index, T t) {
//				throw new UnsupportedOperationException("unmodifiable");
//			}
//
//			@Override
//			public void add(T t) {
//				throw new UnsupportedOperationException("unmodifiable");
//			}
//
//			@Override
//			public T putIfAbsent(int index, T t) {
//				throw new UnsupportedOperationException("unmodifiable");
//			}
//
//			@Override
//			public T putIfAbsent(int index, Supplier<? extends T> t) {
//				throw new UnsupportedOperationException("unmodifiable");
//			}
//
//			@Override
//			public boolean replace(int index, T oldValue, T newValue) {
//				throw new UnsupportedOperationException("unmodifiable");
//			}
//
//			@Override
//			public boolean replace(int index, T oldValue, Supplier<? extends T> newValue) {
//				throw new UnsupportedOperationException("unmodifiable");
//			}
//		}, false, fastNullReturn);
//	}
//
//	public ConvertingIndexMap(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverse) {
//		this(indexMap, remap, reverse, true);
//	}
//
//	public ConvertingIndexMap(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverse, boolean fastNullReturn) {
//		this(indexMap, remap, reverse, new PutFunction<>() {
//			@Override
//			public T put(int index, T t) {
//				return remap.apply(indexMap.put(index, reverse.apply(t)));
//			}
//
//			@Override
//			public void add(T t) {
//				indexMap.add(reverse.apply(t));
//			}
//
//			@Override
//			public T putIfAbsent(int index, T t) {
//				Object[] callbackF = new Object[1];
//				F ret = indexMap.putIfAbsent(index, () -> {
//					F localF = reverse.apply(t);
//					callbackF[0] = localF;
//					return localF;
//				});
//				return callbackF[0] == ret ? t : remap.apply(ret);
//			}
//
//			@Override
//			public T putIfAbsent(int index, Supplier<? extends T> t) {
//				Object[] callbackF = new Object[1];
//				Object[] callbackL = new Object[1];
//				F ret = indexMap.putIfAbsent(index, () -> {
//					T localT = t.get();
//					F localF = reverse.apply(localT);
//					callbackL[0] = localT;
//					callbackF[0] = localF;
//					return localF;
//				});
//				//noinspection unchecked
//				return callbackF[0] == ret ? (T) callbackL[0] : remap.apply(ret);
//			}
//
//			@Override
//			public boolean replace(int index, T oldValue, T newValue) {
//				return indexMap.replace(index, reverse.apply(oldValue), reverse.apply(newValue));
//			}
//
//			@Override
//			public boolean replace(int index, T oldValue, Supplier<? extends T> newValue) {
//				return indexMap.replace(index, reverse.apply(oldValue), () -> reverse.apply(newValue.get()));
//			}
//		}, true, fastNullReturn);
//	}
//
//	public ConvertingIndexMap(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverse, PutFunction<T> put, boolean modifiable, boolean fastNullReturn) {
//		this.indexMap = indexMap;
//		this.remap = remap;
//		this.reverse = reverse;
//		this.put = put;
//		this.modifiable = modifiable;
//		this.fastNullReturn = fastNullReturn;
//	}
//
//	public T remap(F f) {
//		return fastNullReturn && f == null ? null : remap.apply(f);
//	}
//
//	public void checkModifiable() {
//		if (!modifiable)
//			throwNotModifiable();
//	}
//
//	public static void throwNotModifiable() {
//		throw new UnsupportedOperationException("unmodifiable");
//	}
//
//	@Override
//	public boolean isExpandable() {
//		return indexMap.isExpandable();
//	}
//
//	@Override
//	public int size() {
//		return indexMap.size();
//	}
//
//	@Override
//	public boolean contains(int index) {
//		return indexMap.contains(index);
//	}
//
//	@Override
//	public boolean contains(T index) {
//		if (reverse != null)
//			return indexMap.contains(reverse.apply(index));
//
//		for (T t : values())
//			if (t == index)
//				return true;
//		return false;
//	}
//
//	@Override
//	public void add(T v) {
//		put.add(v);
//	}
//
//	@Override
//	public T get(int index) {
//		return remap(indexMap.get(index));
//	}
//
//	@Override
//	public IndexMapEntry<T> getEntry(int index) {
//		return new IndexMapEntry<>() {
//			IndexMapEntry<F> entry = indexMap.getEntry(index);
//
//			@Override
//			public int getIndex() {
//				return index;
//			}
//
//			@Override
//			public T getValue() {
//				return remap(entry.getValue());
//			}
//
//			@Override
//			public void setValue(T v) {
//				put.put(index, v);
//			}
//		};
//	}
//
//	@Override
//	public T put(int index, T v) {
//		return put.put(index, v);
//	}
//
//	@Override
//	public int indexOf(T v) {
//		if (reverse != null)
//			return indexMap.indexOf(reverse.apply(v));
//
//		int i = 0;
//		for (Iterator<T> iterator = values().iterator(); iterator.hasNext(); i++)
//			if (iterator.next() == v)
//				return i;
//		return -1;
//	}
//
//	@Override
//	public T remove(int index) {
//		checkModifiable();
//		return remap(indexMap.remove(index));
//	}
//
//	@Override
//	public T[] toArray() {
//		F[] org = indexMap.toArray();
//		//noinspection unchecked
//		T[] ret = (T[]) new Object[org.length];
//		for (int i = 0; i < org.length; i++)
//			ret[i] = remap(org[i]);
//		return ret;
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public T[] toArray(T[] a) {
//		F[] org = indexMap.toArray();
//		if (a.length < org.length) {
//			//new instance
//			T[] ret = (T[]) Array.newInstance(a.getClass(), org.length);
//			for (int i = 0; i < org.length; i++)
//				ret[i] = remap((org[i]));
//			return ret;
//		}
//
//		//use existing
//		for (int i = 0; i < org.length; i++)
//			a[i] = remap((org[i]));
//		a[org.length] = null;
//		return a;
//	}
//
//	@Override
//	public void addAll(Collection<T> coll) {
//		put.addAll(coll);
//	}
//
//	@Override
//	public void putAll(IndexMap<T> indexMap) {
//		put.putAll(indexMap);
//	}
//
//	@Override
//	public void putAllIfAbsent(IndexMap<T> indexMap) {
//		put.putAllIfAbsent(indexMap);
//	}
//
//	@Override
//	public T getOrDefault(int index, T def) {
//		F ret = indexMap.get(index);
//		if (fastNullReturn && ret == null)
//			return def;
//		T apply = remap.apply(ret);
//		return apply == null ? def : apply;
//	}
//
//	@Override
//	public T putIfAbsent(int index, T v) {
//		return put.putIfAbsent(index, v);
//	}
//
//	@Override
//	public T putIfAbsent(int index, Supplier<? extends T> v) {
//		return put.putIfAbsent(index, v);
//	}
//
//	@Override
//	public boolean replace(int index, T oldValue, T newValue) {
//		return put.replace(index, oldValue, newValue);
//	}
//
//	@Override
//	public boolean replace(int index, T oldValue, Supplier<? extends T> newValue) {
//		return put.replace(index, oldValue, newValue);
//	}
//
//	@Override
//	public boolean remove(T v) {
//		checkModifiable();
//		if (reverse != null)
//			return indexMap.remove(reverse.apply(v));
//
//		return indexMap.remove(indexOf(v)) != null;
//	}
//
//	@Override
//	public boolean remove(int index, T v) {
//		checkModifiable();
//		if (Objects.equals(get(index), v)) {
//			indexMap.remove(index);
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public void clear() {
//		checkModifiable();
//		indexMap.clear();
//	}
//
//	@Override
//	public Collection<T> values() {
//		return null;
//	}
//
//	@Override
//	public Collection<IndexMapEntry<T>> table() {
//		return null;
//	}
//
//	public interface PutFunction<T> {
//
//		T put(int index, T t);
//
//		void add(T t);
//
//		default void addAll(Collection<T> coll) {
//			coll.forEach(this::add);
//		}
//
//		default void putAll(IndexMap<T> indexMap) {
//			for (IndexMapEntry<T> entry : indexMap.table()) {
//				T value = entry.getValue();
//				if (value != null)
//					put(entry.getIndex(), value);
//			}
//		}
//
//		default void putAllIfAbsent(IndexMap<T> indexMap) {
//			for (IndexMapEntry<T> entry : indexMap.table())
//				putIfAbsent(entry.getIndex(), entry::getValue);
//		}
//
//		T putIfAbsent(int index, T t);
//
//		T putIfAbsent(int index, Supplier<? extends T> t);
//
//		boolean replace(int index, T oldValue, T newValue);
//
//		boolean replace(int index, T oldValue, Supplier<? extends T> newValue);
//	}
}
