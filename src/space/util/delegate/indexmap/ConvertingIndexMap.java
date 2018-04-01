package space.util.delegate.indexmap;

import space.util.baseobject.ToString;
import space.util.delegate.collection.ConvertingCollection;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.reflect.Array;
import java.util.Collection;
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
	
	public static <F, T> OneDirectionalUnmodifiable<F, T> createConvertingOneDirectionalUnmodifiable(IndexMap<F> indexMap, Function<F, T> remap) {
		return new OneDirectionalUnmodifiable<>(indexMap, remap);
	}
	
	public static <F, T> BiDirectionalUnmodifiable<F, T> createConvertingBiDirectionalUnmodifiable(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverse) {
		return new BiDirectionalUnmodifiable<>(indexMap, remap, reverse);
	}
	
	public static <F, T> BiDirectionalSparse<F, T> createConvertingBiDirectionalSparse(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverseSparse) {
		return new BiDirectionalSparse<>(indexMap, remap, reverseSparse);
	}
	
	public static <F, T> BiDirectional<F, T> createConvertingBiDirectional(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverse) {
		return new BiDirectional<>(indexMap, remap, reverse);
	}
	
	public static <F, T> BiDirectional<F, T> createConvertingBiDirectional(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverseSparse, Function<T, F> reverse) {
		return new BiDirectional<>(indexMap, remap, reverseSparse, reverse);
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
			return ConvertingCollection.createConvertingBiDirectionalUnmodifiable(indexMap.table(), entry -> entry == null ? null : new Entry(entry), entry -> entry instanceof OneDirectionalUnmodifiable.Entry ? ((Entry) entry).entry : null);
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
			
			@Override
			public T setIfAbsent(Supplier<T> v) {
				throw new UnsupportedOperationException("unmodifiable");
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
	
	public static class BiDirectionalUnmodifiable<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<T, F> reverse;
		
		public BiDirectionalUnmodifiable(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverse) {
			super(indexMap, remap);
			this.reverse = reverse;
		}
		
		@Override
		public Collection<T> values() {
			return ConvertingCollection.createConvertingBiDirectionalUnmodifiable(indexMap.values(), remap, reverse);
		}
	}
	
	public static class BiDirectionalSparse<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<T, F> reverseSparse;
		
		public BiDirectionalSparse(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverseSparse) {
			super(indexMap, remap);
			this.reverseSparse = reverseSparse;
		}
		
		@Override
		public void add(T v) {
			indexMap.add(reverseSparse.apply(v));
		}
		
		@Override
		public T put(int index, T v) {
			return remap.apply(indexMap.put(index, reverseSparse.apply(v)));
		}
		
		@Override
		public T remove(int index) {
			return remap.apply(indexMap.remove(index));
		}
		
		@Override
		public IndexMapEntry<T> getEntry(int index) {
			return new Entry(indexMap.getEntry(index));
		}
		
		@Override
		public void addAll(Collection<T> coll) {
			indexMap.addAll(ConvertingCollection.createConvertingBiDirectionalUnmodifiable(coll, reverseSparse, remap));
		}
		
		@Override
		public void putAll(IndexMap<T> indexMap) {
			this.indexMap.putAll(ConvertingIndexMap.createConvertingBiDirectionalUnmodifiable(indexMap, reverseSparse, remap));
		}
		
		@Override
		public void putAllIfAbsent(IndexMap<T> indexMap) {
			this.indexMap.putAllIfAbsent(ConvertingIndexMap.createConvertingBiDirectionalUnmodifiable(indexMap, reverseSparse, remap));
		}
		
		@Override
		public T putIfAbsent(int index, T v) {
			//noinspection unchecked
			F[] applyF = (F[]) new Object[1];
			F ret = indexMap.putIfAbsent(index, () -> applyF[0] = reverseSparse.apply(v));
			return ret == applyF[0] ? v : remap.apply(ret);
		}
		
		@Override
		public T putIfAbsent(int index, Supplier<? extends T> v) {
			//noinspection unchecked
			F[] applyF = (F[]) new Object[1];
			//noinspection unchecked
			T[] applyT = (T[]) new Object[1];
			F ret = indexMap.putIfAbsent(index, () -> applyF[0] = reverseSparse.apply(applyT[0] = v.get()));
			return ret == applyF[0] ? applyT[0] : remap.apply(ret);
		}
		
		@Override
		public boolean replace(int index, T oldValue, T newValue) {
			IndexMapEntry<F> entry = indexMap.getEntry(index);
			if (!Objects.equals(oldValue, remap.apply(entry.getValue())))
				return false;
			entry.setValue(reverseSparse.apply(newValue));
			return true;
		}
		
		@Override
		public boolean replace(int index, T oldValue, Supplier<? extends T> newValue) {
			IndexMapEntry<F> entry = indexMap.getEntry(index);
			if (!Objects.equals(oldValue, remap.apply(entry.getValue())))
				return false;
			entry.setValue(reverseSparse.apply(newValue.get()));
			return true;
		}
		
		@Override
		public boolean remove(int index, T v) {
			IndexMapEntry<F> entry = indexMap.getEntry(index);
			if (!Objects.equals(v, remap.apply(entry.getValue())))
				return false;
			entry.remove();
			return true;
		}
		
		@Override
		public void clear() {
			indexMap.clear();
		}
		
		@Override
		public Collection<IndexMapEntry<T>> table() {
			return new ConvertingCollection.BiDirectional<>(indexMap.table(), entry -> entry == null ? null : new Entry(entry), entry -> entry instanceof BiDirectionalSparse.Entry ? ((Entry) entry).entry : null);
		}
		
		public class Entry extends OneDirectionalUnmodifiable<F, T>.Entry {
			
			public Entry(IndexMapEntry<F> entry) {
				super(entry);
			}
			
			@Override
			public T setIfAbsent(Supplier<T> v) {
				//noinspection unchecked
				F[] applyF = (F[]) new Object[1];
				//noinspection unchecked
				T[] applyT = (T[]) new Object[1];
				F ret = entry.setIfAbsent(() -> applyF[0] = reverseSparse.apply(applyT[0] = v.get()));
				return ret == applyF[0] ? applyT[0] : remap.apply(ret);
			}
			
			@Override
			public void setValue(T v) {
				entry.setValue(reverseSparse.apply(v));
			}
			
			@Override
			public void remove() {
				entry.remove();
			}
		}
	}
	
	public static class BiDirectional<F, T> extends BiDirectionalSparse<F, T> {
		
		public Function<T, F> reverse;
		
		public BiDirectional(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverse) {
			this(indexMap, remap, reverse, reverse);
		}
		
		public BiDirectional(IndexMap<F> indexMap, Function<F, T> remap, Function<T, F> reverseSparse, Function<T, F> reverse) {
			super(indexMap, remap, reverseSparse);
			this.reverse = reverse;
		}
		
		@Override
		public boolean replace(int index, T oldValue, T newValue) {
			return indexMap.replace(index, reverse.apply(oldValue), () -> reverseSparse.apply(newValue));
		}
		
		@Override
		public boolean replace(int index, T oldValue, Supplier<? extends T> newValue) {
			return indexMap.replace(index, reverse.apply(oldValue), () -> reverseSparse.apply(newValue.get()));
		}
		
		@Override
		public boolean remove(int index, T v) {
			return indexMap.remove(index, reverse.apply(v));
		}
		
		@Override
		public Collection<T> values() {
			return ConvertingCollection.createConvertingBiDirectionalUnmodifiable(indexMap.values(), remap, reverse);
		}
	}
}
