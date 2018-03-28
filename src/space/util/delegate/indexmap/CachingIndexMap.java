package space.util.delegate.indexmap;

import space.util.baseobject.Cache;
import space.util.baseobject.ToString;
import space.util.delegate.map.CachingMap;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import static space.util.delegate.util.CacheUtil.*;

/**
 * The {@link CachingMap} tries to get a value from the {@link CachingMap#map}, and when no value has been found, it will get the value from the {@link CachingMap#def}, write it into the local map and return it;
 * <p>
 * {@link CachingMap} is threadsafe, if the internal {@link CachingMap#map} is threadsafe.
 */
public class CachingIndexMap<VALUE> implements IndexMap<VALUE>, ToString, Cache {
	
	public IndexMap<VALUE> indexMap;
	public IntFunction<VALUE> def;
	
	public CachingIndexMap(IndexMap<VALUE> indexMap, IntFunction<VALUE> def) {
		this.indexMap = indexMap;
		this.def = def;
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
		return indexMap.putIfAbsent(index, () -> toCache(def.apply(index))) != null;
	}
	
	@Override
	@Deprecated
	public boolean contains(VALUE v) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void add(VALUE v) {
		indexMap.add(toCache(v));
	}
	
	@Override
	public VALUE get(int index) {
		return fromCache(indexMap.putIfAbsent(index, () -> toCache(def.apply(index))));
	}
	
	@Override
	public IndexMapEntry<VALUE> getEntry(int index) {
		IndexMapEntry<VALUE> entry = indexMap.getEntry(index);
		entry.setIfAbsent(() -> def.apply(index));
		return entry;
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		return indexMap.put(index, v);
	}
	
	@Override
	@Deprecated
	public int indexOf(VALUE v) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public VALUE remove(int index) {
		return indexMap.remove(index);
	}
	
	@Override
	@Deprecated
	public VALUE[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	@Deprecated
	public VALUE[] toArray(VALUE[] array) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void addAll(Collection<VALUE> coll) {
		indexMap.addAll(coll);
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		this.indexMap.putAll(indexMap);
	}
	
	@Override
	public void putAllIfAbsent(IndexMap<VALUE> indexMap) {
		indexMap.table().forEach(entry -> putIfAbsent(entry.getIndex(), entry::getValue));
	}
	
	@Override
	public VALUE getOrDefault(int index, VALUE def) {
		VALUE value = indexMap.putIfAbsent(index, def);
		return value == null ? def : value;
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		indexMap.putIfAbsent(index, () -> def.apply(index));
		return indexMap.putIfAbsent(index, v);
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		return indexMap.putIfAbsent(index, v);
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
		return indexMap.values();
	}
	
	@Override
	public Collection<IndexMapEntry<VALUE>> table() {
		return indexMap.table();
	}
//	//get
//	@Override
//	public VALUE get(int index) {
//		VALUE thisV = indexMap.get(index);
//		if (thisV != null)
//			return fromCache(thisV);
//
//		VALUE newV = def.apply(index);
//		put(index, toCache(newV));
//		return newV;
//	}
//
//	@Override
//	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
//		IndexMapEntry<VALUE> entry = indexMap.getEntry(index);
//		VALUE value = entry.getValue();
//		boolean useDef = value == null;
//
//		if (useDef)
//			value = this.def.apply(index);
//		if (Objects.equals(oldValue, value)) {
//			entry.setValue(newValue);
//			return true;
//		}
//		if (useDef)
//			entry.setValue(value);
//		return false;
//	}
	
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
	
	@Override
	public void clearCache() {
		indexMap.clear();
	}
}
