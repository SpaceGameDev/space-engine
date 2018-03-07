package space.util.key.attribute;

import space.util.delegate.list.IntList;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;
import space.util.key.IKey;
import space.util.key.attribute.IAttributeListCreator.ChangeEvent;
import space.util.key.attribute.IAttributeListCreator.ChangeEventEntry;
import space.util.key.map.KeyMapImpl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class AttributeListChangeEventHelper implements Consumer<ChangeEvent<?>> {
	
	public final AtomicInteger callbackGen = new AtomicInteger();
	public final IndexMap<Consumer<ChangeEvent<?>>> callbackList = new IndexMapArray<>();
	public final KeyMapImpl<IntList> keysToCallback = new KeyMapImpl<>();
	
	//put
	public <V> void putEntry(Consumer<ChangeEventEntry<V>> consumer, IKey<V> key) {
		put(event -> consumer.accept(event.getEntry(key)), key);
	}
	
	public void put(Consumer<ChangeEvent<?>> consumer, IKey<?>... key) {
		int cbIndex = callbackGen.getAndIncrement();
		callbackList.put(cbIndex, consumer);
		for (IKey<?> k : key)
			keysToCallback.putIfAbsent(k, () -> new IntList(1)).add(cbIndex);
	}
	
	//accept
	@Override
	public void accept(ChangeEvent<?> changeEvent) {
		IndexMap<Boolean> result = new IndexMapArray<>();
		changeEvent.getMod().table().forEach(entry -> {
			if (entry.isUnchanged())
				return;
			IntList intList = keysToCallback.get(entry.getKey());
			if (intList != null)
				for (int i : intList.toArray())
					result.put(i, Boolean.TRUE);
		});
		
		result.table().forEach(entry -> {
			//null -> false
			if (entry.getValue() == Boolean.TRUE)
				callbackList.get(entry.getIndex()).accept(changeEvent);
		});
	}
}
