package space.util.delegate.indexmap;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.delegate.iterator.Iteratorable;
import space.util.delegate.iterator.ReferenceIterator;
import space.util.delegate.util.ReferenceUtil;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;
import space.util.string.toStringHelper.objects.TSHObjects.TSHObjectsInstance;

import java.lang.ref.Reference;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReferenceIndexMap<VALUE> implements BaseObject, IndexMap<VALUE> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(ReferenceIndexMap.class, d -> new ReferenceIndexMap(Copyable.copy(d.indexMap), d.refCreator));
	}
	
	public IndexMap<Reference<? extends VALUE>> indexMap;
	public Function<VALUE, ? extends Reference<? extends VALUE>> refCreator;
	
	public ReferenceIndexMap(IndexMap<Reference<? extends VALUE>> indexMap) {
		this(indexMap, ReferenceUtil.defRefCreator());
	}
	
	public ReferenceIndexMap(IndexMap<Reference<? extends VALUE>> indexMap, Function<VALUE, Reference<? extends VALUE>> refCreator) {
		this.indexMap = indexMap;
		this.refCreator = refCreator;
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
	public boolean contains(int index) {
		return indexMap.contains(index);
	}
	
	@Override
	public void add(VALUE v) {
		indexMap.add(refCreator.apply(v));
	}
	
	@Override
	public VALUE get(int index) {
		return ReferenceUtil.getSafe(indexMap.get(index));
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		return ReferenceUtil.getSafe(indexMap.put(index, refCreator.apply(v)));
	}
	
	@Override
	public VALUE remove(int index) {
		return ReferenceUtil.getSafe(indexMap.remove(index));
	}
	
	@Override
	public boolean contains(VALUE v) {
		return indexMap.contains(refCreator.apply(v));
	}
	
	@Override
	public int indexOf(VALUE v) {
		return indexMap.indexOf(refCreator.apply(v));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE[] toArray() {
		Reference<? extends VALUE>[] array = indexMap.toArray();
		Object[] ret = new Object[array.length];
		for (int i = 0; i < array.length; i++)
			ret[i] = ReferenceUtil.getSafe(array[i]);
		return (VALUE[]) ret;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE[] toArray(VALUE[] ret) {
		Reference<? extends VALUE>[] array = indexMap.toArray();
		if (ret.length < array.length)
			ret = (VALUE[]) new Object[array.length];
		for (int i = 0; i < array.length; i++)
			ret[i] = ReferenceUtil.getSafe(array[i]);
		return ret;
	}
	
	@Override
	public VALUE getOrDefault(int index, VALUE def) {
		Reference<? extends VALUE> ret = indexMap.get(index);
		return ret == null ? def : ret.get();
	}
	
	@Override
	public VALUE putIfAbsent(int index, VALUE v) {
		return ReferenceUtil.getSafe(indexMap.putIfAbsent(index, () -> refCreator.apply(v)));
	}
	
	@Override
	public VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		return ReferenceUtil.getSafe(indexMap.putIfAbsent(index, () -> refCreator.apply(v.get())));
	}
	
	@Override
	public VALUE replace(int index, VALUE newValue) {
		return contains(index) ? put(index, newValue) : null;
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, VALUE newValue) {
		if (Objects.equals(get(index), oldValue)) {
			put(index, newValue);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		if (Objects.equals(get(index), oldValue)) {
			put(index, newValue.get());
			return true;
		}
		return false;
	}
	
	@Override
	public VALUE replace(int index, Supplier<? extends VALUE> newValue) {
		return contains(index) ? put(index, newValue.get()) : null;
	}
	
	@Override
	public boolean remove(int index, VALUE v) {
		if (Objects.equals(get(index), v)) {
			remove(index);
			return true;
		}
		return false;
	}
	
	@Override
	public void clear() {
		indexMap.clear();
	}
	
	@Override
	public Iteratorable<VALUE> iterator() {
		return new ReferenceIterator<>(indexMap.iterator());
	}
	
	@Override
	public Iteratorable<IndexMapEntry<VALUE>> tableIterator() {
		return new Iteratorable<IndexMapEntry<VALUE>>() {
			Iteratorable<IndexMapEntry<Reference<? extends VALUE>>> i = indexMap.tableIterator();
			
			@Override
			public boolean hasNext() {
				return i.hasNext();
			}
			
			@Override
			public IndexMapEntry<VALUE> next() {
				IndexMapEntry<Reference<? extends VALUE>> entry = i.next();
				return new IndexMapEntry<VALUE>() {
					VALUE v = ReferenceUtil.getSafe(entry.getValue());
					
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
						entry.setValue(refCreator.apply(v));
					}
				};
			}
			
			@Override
			public void remove() {
				i.remove();
			}
		};
	}
	
	@Override
	public int hashCode() {
		return indexMap.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof IndexMap<?>))
			return false;
		
		IndexMap<?> other = (IndexMap<?>) obj;
		int index = 0;
		int max = Math.max(other.size(), size());
		for (int i = 0; i < max; i++) {
			Object ent = get(i);
			Object entO = other.get(i);
			if (!(Objects.equals(entO, ent) || (entO instanceof Reference<?> && Objects.equals(((Reference) entO).get(), ent))))
				return false;
		}
		return true;
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("refCreator", this.refCreator);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
