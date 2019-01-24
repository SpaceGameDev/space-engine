package space.engine.key.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.key.Key;

public class ChangeEvent<TYPE> {
	
	public final AttributeList<TYPE> oldList;
	public final AttributeListModification<TYPE> mod;
	
	public ChangeEvent(AttributeList<TYPE> oldList, AttributeListModification<TYPE> mod) {
		this.oldList = oldList;
		this.mod = mod;
	}
	
	public AttributeList getOldList() {
		return oldList;
	}
	
	public AttributeListModification getMod() {
		return mod;
	}
	
	@NotNull
	public <V> ChangeEventEntry<V> getEntry(@NotNull Key<V> key) {
		return new ChangeEventEntry<>(key);
	}
	
	public class ChangeEventEntry<V> {
		
		private final @NotNull Key<V> key;
		
		public ChangeEventEntry(@NotNull Key<V> key) {
			this.key = key;
		}
		
		//get
		@NotNull
		public Key<V> getKey() {
			return key;
		}
		
		@Nullable
		public Object getOldDirect() {
			return oldList.getDirect(key);
		}
		
		@Nullable
		public V getOld() {
			return oldList.get(key);
		}
		
		@Nullable
		public Object getMod() {
			return mod.getDirect(key);
		}
		
		@Nullable
		public Object getNewDirect() {
			Object v = mod.getDirect(key);
			return v == AttributeListCreator.UNCHANGED ? oldList.getDirect(key) : v;
		}
		
		@Nullable
		public V getNew() {
			Object v = mod.getDirect(key);
			if (v == AttributeListCreator.UNCHANGED)
				return oldList.get(key);
			if (v == AttributeListCreator.DEFAULT)
				return key.getDefaultValue();
			//noinspection unchecked
			return (V) v;
		}
		
		//set
		public void setMod(@Nullable Object newmod) {
			mod.putDirect(key, newmod);
		}
		
		public boolean isUnchanged() {
			return getNewDirect() == AttributeListCreator.UNCHANGED;
		}
		
		public boolean hasChanged() {
			return getNewDirect() != AttributeListCreator.UNCHANGED;
		}
	}
}
