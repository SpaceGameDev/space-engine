package space.util.indexmap;

import space.util.delegate.iterator.Iteratorable;
import space.util.delegate.iterator.UnmodifiableIterator;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class IndexMapFromList<VALUE> implements IndexMap<VALUE> {
	
	public List<VALUE> list;
	public boolean modifiable;
	
	public IndexMapFromList(List<VALUE> list) {
		this(list, false);
	}
	
	public IndexMapFromList(List<VALUE> list, boolean modifiable) {
		this.list = list;
		this.modifiable = modifiable;
	}
	
	//capacity
	@Override
	public boolean isExpandable() {
		return modifiable;
	}
	
	@Override
	public int size() {
		return list.size();
	}
	
	//access
	@Override
	public VALUE get(int index) {
		return list.get(index);
	}
	
	@Override
	public VALUE put(int index, VALUE v) {
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		return list.set(index, v);
	}
	
	@Override
	public VALUE remove(int index) {
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		return list.remove(index);
	}
	
	@Override
	public void add(VALUE v) {
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		list.add(v);
	}
	
	@Override
	public int indexOf(VALUE v) {
		return list.indexOf(v);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public VALUE[] toArray() {
		return (VALUE[]) list.toArray();
	}
	
	@Override
	public VALUE[] toArray(VALUE[] array) {
		return list.toArray(array);
	}
	
	//addAll
	@Override
	public void addAll(Collection<VALUE> coll) {
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		list.addAll(coll);
	}
	
	@Override
	public void putAll(IndexMap<VALUE> indexMap) {
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		indexMap.tableIterator().forEach(entry -> list.set(entry.getIndex(), entry.getValue()));
	}
	
	//other
	@Override
	public void clear() {
		if (!modifiable)
			throw new UnsupportedOperationException("not modifiable!");
		list.clear();
	}
	
	@Override
	public Iteratorable<VALUE> iterator() {
		return modifiable ? Iteratorable.toIteratorable(list.iterator()) : new UnmodifiableIterator<>(list.iterator());
	}
	
	@Override
	public Iteratorable<IndexMapEntry<VALUE>> tableIterator() {
		return new Iteratorable<IndexMapEntry<VALUE>>() {
			
			ListIterator<VALUE> iter = list.listIterator();
			
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}
			
			@Override
			public IndexMapEntry<VALUE> next() {
				return new IndexMapEntry<VALUE>() {
					VALUE next = iter.next();
					
					@Override
					public int getIndex() {
						return iter.previousIndex();
					}
					
					@Override
					public VALUE getValue() {
						return next;
					}
					
					@Override
					public void setValue(VALUE v) {
						next = v;
						iter.set(v);
					}
				};
			}
		};
	}
}
