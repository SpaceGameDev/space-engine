package space.util.key.attribute;

import org.jetbrains.annotations.NotNull;
import space.util.delegate.specific.IntList;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;
import space.util.key.Key;
import space.util.key.attribute.AttributeListCreator.ChangeEvent;
import space.util.key.attribute.AttributeListCreator.ChangeEventEntry;
import space.util.key.map.KeyMapImpl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Allows for easy filtering of {@link ChangeEvent ChangeEvents}.<br>
 * You can add Callbacks via {@link AttributeListChangeEventHelper#putEntry(Consumer, Key)} for single Keys
 * and {@link AttributeListChangeEventHelper#put(Consumer, Key[])} for multiple Keys. Note that their Consumer changes.
 */
public class AttributeListChangeEventHelper implements Consumer<ChangeEvent<?>> {
	
	@NotNull
	public final AtomicInteger callbackGen = new AtomicInteger();
	@NotNull
	public final IndexMap<Consumer<ChangeEvent<?>>> callbackList = new IndexMapArray<>();
	@NotNull
	public final KeyMapImpl<IntList> keysToCallback = new KeyMapImpl<>();
	
	//put
	public <V> void putEntry(Consumer<ChangeEventEntry<V>> consumer, Key<V> key) {
		put(event -> consumer.accept(event.getEntry(key)), key);
	}
	
	public void put(Consumer<ChangeEvent<?>> consumer, Key<?>... key) {
		int cbIndex = callbackGen.getAndIncrement();
		callbackList.put(cbIndex, consumer);
		for (Key<?> k : key)
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
