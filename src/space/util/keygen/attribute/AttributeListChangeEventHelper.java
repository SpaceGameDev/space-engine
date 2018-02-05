package space.util.keygen.attribute;

import space.util.keygen.IKey;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListChangeEvent;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListChangeEventEntry;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListModification;
import space.util.keygen.map.KeyMapImpl;

import java.util.function.Consumer;

import static space.util.keygen.attribute.IAttributeListCreator.UNCHANGED_OBJECT;

public class AttributeListChangeEventHelper<V> implements Consumer<IAttributeListChangeEvent> {
	
	public boolean filterOutUnchanged;
	public KeyMapImpl<Consumer<IAttributeListChangeEventEntry<V>>> callMap = new KeyMapImpl<>();
	
	public void put(IKey<V> key, Consumer<IAttributeListChangeEventEntry<V>> consumer) {
		callMap.put(key, consumer);
	}
	
	@Override
	public void accept(IAttributeListChangeEvent changeEvent) {
		IAttributeListModification mod = changeEvent.getMod();
		mod.tableIterator().forEach(entry -> {
			Object value = entry.getValue();
			if (filterOutUnchanged && value == UNCHANGED_OBJECT)
				return;
			
			callMap.map.get(entry.getIndex())//.accept();
		});
	}
}
