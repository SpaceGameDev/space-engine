package space.util.keygen.map;

import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap.IndexMapEntry;

public interface IKeyMap<VALUE> {
	
	//others
	int size();
	
	void clear();
	
	Iteratorable<VALUE> iterator();
	
	Iteratorable<IndexMapEntry<VALUE>> tableIterator();
}
