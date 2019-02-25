package space.engine.key.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.baseobject.ToString;
import space.engine.indexmap.IndexMap;
import space.engine.indexmap.IndexMapArray;
import space.engine.key.Key;
import space.engine.string.toStringHelper.ToStringHelper;
import space.engine.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;

public abstract class AbstractAttributeList<TYPE> implements ToString {
	
	protected AttributeListCreator<TYPE> creator;
	protected IndexMap<Object> indexMap;
	
	protected AbstractAttributeList(AttributeListCreator<TYPE> creator, Object defaultObject) {
		this.creator = creator;
		this.indexMap = new IndexMapArray<>(defaultObject, creator.getInitialIndexMapCapacity());
	}
	
	//get
	public <V> @Nullable Object getDirect(@NotNull Key<V> key) {
		creator.check(key);
		return indexMap.get(key.getID());
	}
	
	//other
	public int size() {
		return indexMap.size();
	}
	
	public AttributeListCreator<TYPE> getCreator() {
		return creator;
	}
	
	@NotNull
	public Collection<Object> values() {
		return indexMap.values();
	}
	
	//toString
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("indexMap", indexMap);
		tsh.add("creator", creator);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public <V> boolean isDefault(@NotNull Key<V> key) {
		return getDirect(key) == AttributeListCreator.DEFAULT;
	}
	
	public <V> boolean isNotDefault(@NotNull Key<V> key) {
		return getDirect(key) != AttributeListCreator.DEFAULT;
	}
	
	/**
	 * creates a new {@link AttributeListModification AttributeListModification}.
	 * Calls <code>this.{@link AbstractAttributeList#getCreator()}.{@link AttributeListCreator#createModify()}</code> by default.
	 */
	public @NotNull AttributeListModification<TYPE> createModify() {
		return getCreator().createModify();
	}
	
	/**
	 * gets an {@link java.util.Iterator} over all index / value pairs
	 */
	@NotNull
	public abstract Collection<? extends space.engine.key.attribute.AbstractEntry<?>> table();
	
	protected abstract class AbstractEntry<V> implements space.engine.key.attribute.AbstractEntry<V> {
		
		protected Key<V> key;
		
		public AbstractEntry(Key<V> key) {
			this.key = key;
		}
		
		@NotNull
		@Override
		public Key<V> getKey() {
			return key;
		}
		
		@Override
		public Object getValueDirect() {
			return AbstractAttributeList.this.getDirect(key);
		}
	}
}
