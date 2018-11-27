package space.util.key.attribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.delegate.collection.ConvertingCollection;
import space.util.event.Event;
import space.util.event.EventImpl;
import space.util.key.Key;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

public class AttributeList<TYPE> extends AbstractAttributeList<TYPE> {
	
	@NotNull
	public EventImpl<Consumer<ChangeEvent>> changeEvent = new EventImpl<>();
	
	public AttributeList(AttributeListCreator<TYPE> creator) {
		super(creator, AttributeListCreator.DEFAULT);
	}
	
	//get
	@SuppressWarnings("unchecked")
	public <V> V get(@NotNull Key<V> key) {
		creator.check(key);
		return AttributeListCreator.correctDefault((V) indexMap.get(key.getID()), key);
	}
	
	@Nullable
	@Contract("_, !null -> !null")
	@SuppressWarnings("unchecked")
	public <V> V getOrDefault(@NotNull Key<V> key, @Nullable V def) {
		creator.check(key);
		Object o = indexMap.get(key.getID());
		return o == AttributeListCreator.DEFAULT ? def : (V) o;
	}
	
	//other
	@NotNull
	public Event<Consumer<ChangeEvent>> getChangeEvent() {
		return changeEvent;
	}
	
	public synchronized void apply(AttributeListModification<TYPE> mod2) {
		//unchanged check and replacement
		AttributeListModification<TYPE> mod = creator.createModify();
		mod2.table().forEach(entry -> {
			Key<?> key = entry.getKey();
			Object value = entry.getValueDirect();
			mod.putDirect(key, Objects.equals(value, this.getDirect(key)) ? AttributeListCreator.UNCHANGED : value);
		});
		
		//trigger events
		ChangeEvent chEvent = new ChangeEvent<>(this, mod);
		changeEvent.execute(attributeListChangeEventConsumer -> attributeListChangeEventConsumer.accept(chEvent));
		
		//apply values
		mod.table().forEach(entry -> {
			Object value = entry.getValueDirect();
			if (value != AttributeListCreator.UNCHANGED)
				this.indexMap.put(entry.getKey().getID(), value);
		});
	}
	
	protected class ListEntry<V> extends AbstractEntry<V> implements space.util.key.attribute.AbstractEntry<V> {
		
		public ListEntry(Key<V> key) {
			super(key);
		}
		
		public V getValue() {
			return AttributeList.this.get(key);
		}
	}
	
	@NotNull
	public Collection<? extends ListEntry<?>> table() {
		return new ConvertingCollection.BiDirectional<>(indexMap.table(), entry -> new ListEntry<>(creator.gen.getKey(entry.getIndex())), entry -> indexMap.getEntry(entry.getKey().getID()));
	}
}
