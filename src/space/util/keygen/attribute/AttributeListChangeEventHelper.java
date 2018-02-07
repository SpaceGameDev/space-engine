package space.util.keygen.attribute;

import space.util.keygen.IKey;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListChangeEvent;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListChangeEventEntry;
import space.util.keygen.attribute.IAttributeListCreator.IAttributeListModification;
import space.util.keygen.map.KeyMapImpl;

import java.util.function.Consumer;

import static space.util.keygen.attribute.IAttributeListCreator.UNCHANGED;

public class AttributeListChangeEventHelper implements Consumer<IAttributeListChangeEvent> {
	
	public KeyMapImpl<Consumer<IAttributeListChangeEventEntry<?>>> callMap = new KeyMapImpl<>();
	
	@SuppressWarnings("unchecked")
	public <V> void put(IKey<V> key, Consumer<IAttributeListChangeEventEntry<V>> consumer) {
		callMap.put(key, (Consumer<IAttributeListChangeEventEntry<?>>) (Object) consumer);
	}
	
	@Override
	public void accept(IAttributeListChangeEvent changeEvent) {
		IAttributeListModification mod = changeEvent.getMod();
		mod.tableIterator().forEach(entry -> {
			Object value = entry.getValueDirect();
			if (value == UNCHANGED)
				return;
			
			final IKey<?> key = entry.getKey();
			callMap.get(key).accept(changeEvent.getEntry(key));
		});
	}
}
