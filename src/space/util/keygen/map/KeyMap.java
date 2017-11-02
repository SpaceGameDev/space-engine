package space.util.keygen.map;

import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.indexmap.IndexMapArray;
import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;
import space.util.keygen.IllegalKeyException;

public class KeyMap<VALUE> implements IKeyMap<VALUE> {
	
	public IndexMap<VALUE> map;
	public IKeyGenerator gen;
	
	public KeyMap() {
		this(new IndexMapArray<>());
	}
	
	public KeyMap(IndexMap<VALUE> map) {
		this.map = map;
	}
	
	public KeyMap(IKeyGenerator gen) {
		this(new IndexMapArray<>(), gen);
	}
	
	public KeyMap(IndexMap<VALUE> map, IKeyGenerator gen) {
		this.map = map;
		this.gen = gen;
	}
	
	public void check(IKey<?> key) {
		if (gen != null && !gen.isKeyOf(key))
			throw new IllegalKeyException();
	}
	
	@Override
	public IKeyGenerator getGen() {
		return gen;
	}
	
	@Override
	public int size() {
		return map.size();
	}
	
	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public Iteratorable<VALUE> iterator() {
		return map.iterator();
	}
	
	@Override
	public Iteratorable<IndexMapEntry<VALUE>> tableIterator() {
		return map.tableIterator();
	}
}
