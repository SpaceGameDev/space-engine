package space.engine.key.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.delegate.collection.ConvertingCollection;
import space.engine.key.IllegalKeyException;
import space.engine.key.Key;

import java.util.Collection;
import java.util.function.Supplier;

public class AttributeListModification<TYPE> extends AbstractAttributeList<TYPE> {
	
	public AttributeListModification(AttributeListCreator<TYPE> creator) {
		super(creator, AttributeListCreator.UNCHANGED);
	}
	
	//put
	public <V> void put(@NotNull Key<V> key, @Nullable V v) {
		creator.check(key);
		indexMap.put(key.getID(), v);
	}
	
	public <V> void putDirect(@NotNull Key<V> key, @Nullable Object v) {
		creator.check(key);
		indexMap.put(key.getID(), v);
	}
	
	//putAndGet
	@Nullable
	@SuppressWarnings("unchecked")
	public <V> V putAndGet(@NotNull Key<V> key, @Nullable V v) {
		creator.check(key);
		return AttributeListCreator.correctDefault((V) indexMap.put(key.getID(), v), key);
	}
	
	//set to UNCHANGED
	public <V> void reset(@NotNull Key<V> key) {
		creator.check(key);
		indexMap.put(key.getID(), AttributeListCreator.UNCHANGED);
	}
	
	public <V> boolean reset(@NotNull Key<V> key, @Nullable V v) {
		creator.check(key);
		return indexMap.replace(key.getID(), v, AttributeListCreator.UNCHANGED);
	}
	
	//set to DEFAULT
	public <V> void setDefault(@NotNull Key<V> key) {
		creator.check(key);
		indexMap.put(key.getID(), AttributeListCreator.DEFAULT);
	}
	
	public <V> boolean setDefault(@NotNull Key<V> key, @Nullable V v) {
		creator.check(key);
		return indexMap.replace(key.getID(), v, AttributeListCreator.DEFAULT);
	}
	
	//replace
	public <V> boolean replace(@NotNull Key<V> key, @Nullable V oldValue, @Nullable V newValue) {
		creator.check(key);
		return indexMap.replace(key.getID(), oldValue, newValue);
	}
	
	public <V> boolean replace(@NotNull Key<V> key, @Nullable V oldValue, @NotNull Supplier<? extends V> newValue) {
		creator.check(key);
		return indexMap.replace(key.getID(), oldValue, newValue);
	}
	
	public void copyOver(AttributeList list, @NotNull Key<?>... keys) {
		if (list.getCreator() != creator)
			throw new IllegalKeyException("List not of same generator type");
		
		for (Key<?> key : keys) {
			creator.check(key);
			indexMap.put(key.getID(), list.getDirect(key));
		}
	}
	
	//clear
	public void clear() {
		indexMap.clear();
	}
	
	public AttributeList<TYPE> createNewList() {
		AttributeList<TYPE> list = new AttributeList<>(creator);
		this.table().forEach(entry -> {
			Object value = entry.getValueDirect();
			if (value != AttributeListCreator.UNCHANGED)
				list.indexMap.put(entry.getKey().getID(), value);
		});
		return list;
	}
	
	//get
	public <V> boolean isUnchanged(@NotNull Key<V> key) {
		return getDirect(key) == AttributeListCreator.UNCHANGED;
	}
	
	public <V> boolean hasChanged(@NotNull Key<V> key) {
		return getDirect(key) != AttributeListCreator.UNCHANGED;
	}
	
	public class ListModificationEntry<V> extends AbstractEntry<V> {
		
		public ListModificationEntry(Key<V> key) {
			super(key);
		}
		
		public void put(@Nullable V v) {
			AttributeListModification.this.put(key, v);
		}
		
		public void putDirect(@Nullable Object v) {
			AttributeListModification.this.putDirect(key, v);
		}
		
		public void setDefault() {
			AttributeListModification.this.setDefault(key);
		}
		
		public void reset() {
			AttributeListModification.this.reset(key);
		}
		
		public boolean isUnchanged() {
			return getValueDirect() == AttributeListCreator.UNCHANGED;
		}
		
		public boolean hasChanged() {
			return getValueDirect() != AttributeListCreator.UNCHANGED;
		}
	}
	
	@NotNull
	public Collection<? extends ListModificationEntry<?>> table() {
		return new ConvertingCollection.BiDirectional<>(indexMap.table(), entry -> new ListModificationEntry<>(creator.gen.getKey(entry.getIndex())), entry -> indexMap.getEntry(entry.getKey().getID()));
	}
}
