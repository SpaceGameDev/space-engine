package space.util.keygen.map;

import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.keygen.IKeyGenerator;

public interface IKeyMap<VALUE> {
	
	//others
	IKeyGenerator getGen();
	
	int size();
	
	void clear();
	
	Iteratorable<VALUE> iterator();
	
	Iteratorable<IndexMapEntry<VALUE>> tableIterator();
}
