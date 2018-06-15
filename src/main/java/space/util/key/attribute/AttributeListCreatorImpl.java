package space.util.key.attribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.baseobject.ToString;
import space.util.concurrent.event.Event;
import space.util.concurrent.event.SimpleEvent;
import space.util.delegate.collection.ConvertingCollection;
import space.util.indexmap.IndexMapArrayWithDefault;
import space.util.key.IllegalKeyException;
import space.util.key.Key;
import space.util.key.KeyGenerator;
import space.util.key.impl.DisposableKeyGenerator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AttributeListCreatorImpl<TYPE> implements AttributeListCreator<TYPE>, ToString {
	
	/**
	 * <p><b>NO NULLABLE!</b></p>
	 * It can be null if the Default Value is null, but it's not possible to define that.
	 * We are expecting the Developer to know whether the default can be null or not.
	 */
	@SuppressWarnings("ConstantConditions")
	protected static <V> V correctDefault(V v, Key<V> key) {
		return v != DEFAULT ? v : key.getDefaultValue();
	}
	
	public final KeyGenerator gen;
	
	public AttributeListCreatorImpl() {
		this(new DisposableKeyGenerator(false));
	}
	
	public AttributeListCreatorImpl(KeyGenerator gen) {
		this.gen = gen;
	}
	
	public void check(Key<?> key) {
		if (!gen.isKeyOf(key))
			throw new IllegalKeyException(key);
	}
	
	protected int getInitialIndexMapCapacity() {
		int i = gen.estimateKeyPoolMax();
		return i < 16 ? 16 : i;
	}
	
	//delegate to gen
	@NotNull
	@Override
	public <T> Key<T> generateKey() {
		return gen.generateKey();
	}
	
	@NotNull
	@Override
	public <T> Key<T> generateKey(@NotNull Supplier<T> def) {
		return gen.generateKey(def);
	}
	
	@Override
	public boolean isKeyOf(@NotNull Key<?> key) {
		return gen.isKeyOf(key);
	}
	
	@NotNull
	@Override
	public <T> Key<T> generateKey(T def) {
		return gen.generateKey(def);
	}
	
	@Override
	public Key<?> getKey(int id) {
		return gen.getKey(id);
	}
	
	@NotNull
	@Override
	public Collection<Key<?>> getKeys() {
		return gen.getKeys();
	}
	
	@Override
	public int estimateKeyPoolMax() {
		return gen.estimateKeyPoolMax();
	}
	
	//create
	@NotNull
	@Override
	public AttributeListImpl create() {
		return new AttributeListImpl();
	}
	
	@NotNull
	@Override
	public AttributeListModificationImpl createModify() {
		return new AttributeListModificationImpl();
	}
	
	//toString
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("gen", this.gen);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public abstract class AbstractAttributeListImpl implements IAbstractAttributeList<TYPE>, ToString {
		
		public IndexMapArrayWithDefault<Object> indexMap;
		
		protected AbstractAttributeListImpl(Object defaultObject) {
			this.indexMap = new IndexMapArrayWithDefault<>(getInitialIndexMapCapacity(), defaultObject);
		}
		
		//get
		@Override
		@SuppressWarnings("unchecked")
		public <V> Object getDirect(@NotNull Key<V> key) {
			check(key);
			return indexMap.get(key.getID());
		}
		
		//other
		@Override
		public int size() {
			return indexMap.size();
		}
		
		@NotNull
		@Override
		public AttributeListCreator<TYPE> getCreator() {
			return AttributeListCreatorImpl.this;
		}
		
		@NotNull
		@Override
		public Collection<Object> values() {
			return indexMap.values();
		}
		
		//toString
		@NotNull
		@Override
		public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("indexMap", indexMap);
			tsh.add("creator", AttributeListCreatorImpl.this);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
		
		protected abstract class AbstractEntry<V> implements AttributeListCreator.AbstractEntry<V> {
			
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
				return AbstractAttributeListImpl.this.getDirect(key);
			}
		}
	}
	
	public class AttributeListImpl extends AbstractAttributeListImpl implements AttributeList<TYPE> {
		
		@NotNull
		public SimpleEvent<Consumer<ChangeEvent<?>>> changeEvent = new SimpleEvent<>();
		
		public AttributeListImpl() {
			super(DEFAULT);
		}
		
		//get
		@Override
		@SuppressWarnings("unchecked")
		public <V> V get(@NotNull Key<V> key) {
			check(key);
			return correctDefault((V) indexMap.get(key.getID()), key);
		}
		
		@Override
		@Nullable
		@Contract("_, !null -> !null")
		@SuppressWarnings("unchecked")
		public <V> V getOrDefault(@NotNull Key<V> key, @Nullable V def) {
			check(key);
			Object o = indexMap.get(key.getID());
			return o == DEFAULT ? def : (V) o;
		}
		
		//other
		@NotNull
		@Override
		public Event<Consumer<ChangeEvent<?>>> getChangeEvent() {
			return changeEvent;
		}
		
		@Override
		public synchronized void apply(@NotNull AttributeListCreator.AttributeListModification<TYPE> mod2) {
			//unchanged check and replacement
			AttributeListModificationImpl mod = AttributeListCreatorImpl.this.createModify();
			mod2.table().forEach(entry -> {
				Key<?> key = entry.getKey();
				Object value = entry.getValueDirect();
				mod.putDirect(key, Objects.equals(value, this.getDirect(key)) ? UNCHANGED : value);
			});
			
			//trigger events
			ChangeEvent chEvent = new AttributeListChangeEvent(this, mod);
			changeEvent.run(attributeListChangeEventConsumer -> attributeListChangeEventConsumer.accept(chEvent));
			
			//apply values
			mod.table().forEach(entry -> {
				Object value = entry.getValueDirect();
				if (value != UNCHANGED)
					this.indexMap.put(entry.getKey().getID(), value);
			});
		}
		
		protected class ListEntry<V> extends AbstractEntry<V> implements AttributeListCreator.ListEntry<V> {
			
			public ListEntry(Key<V> key) {
				super(key);
			}
			
			@Override
			public V getValue() {
				return AttributeListImpl.this.get(key);
			}
		}
		
		@NotNull
		@Override
		public Collection<? extends AttributeListCreator.ListEntry<?>> table() {
			return new ConvertingCollection.BiDirectional<>(indexMap.table(), entry -> new ListEntry<>(gen.getKey(entry.getIndex())), entry -> indexMap.getEntry(entry.getKey().getID()));
		}
	}
	
	public class AttributeListModificationImpl extends AbstractAttributeListImpl implements AttributeListModification<TYPE> {
		
		public AttributeListModificationImpl() {
			super(UNCHANGED);
		}
		
		//put
		@Override
		@SuppressWarnings("unchecked")
		public <V> void put(@NotNull Key<V> key, @Nullable V v) {
			check(key);
			indexMap.put(key.getID(), v);
		}
		
		@Override
		public <V> void putDirect(@NotNull Key<V> key, @Nullable Object v) {
			check(key);
			indexMap.put(key.getID(), v);
		}
		
		//putAndGet
		@Override
		@SuppressWarnings("unchecked")
		public <V> V putAndGet(@NotNull Key<V> key, @Nullable V v) {
			check(key);
			return correctDefault((V) indexMap.put(key.getID(), v), key);
		}
		
		//set to UNCHANGED
		@Override
		@SuppressWarnings("unchecked")
		public <V> void reset(@NotNull Key<V> key) {
			check(key);
			indexMap.put(key.getID(), UNCHANGED);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean reset(@NotNull Key<V> key, @Nullable V v) {
			check(key);
			return indexMap.replace(key.getID(), v, UNCHANGED);
		}
		
		//set to DEFAULT
		@Override
		public <V> void setDefault(@NotNull Key<V> key) {
			check(key);
			indexMap.put(key.getID(), DEFAULT);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean setDefault(@NotNull Key<V> key, @Nullable V v) {
			check(key);
			return indexMap.replace(key.getID(), v, DEFAULT);
		}
		
		//replace
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean replace(@NotNull Key<V> key, @Nullable V oldValue, @Nullable V newValue) {
			check(key);
			return indexMap.replace(key.getID(), oldValue, newValue);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean replace(@NotNull Key<V> key, @Nullable V oldValue, @NotNull Supplier<? extends V> newValue) {
			check(key);
			return indexMap.replace(key.getID(), oldValue, newValue);
		}
		
		@Override
		public void copyOver(@NotNull IAbstractAttributeList list, @NotNull Key<?>... keys) {
			if (list.getCreator() != AttributeListCreatorImpl.this)
				throw new IllegalKeyException("List not of same generator type");
			
			for (Key<?> key : keys) {
				check(key);
				indexMap.put(key.getID(), list.getDirect(key));
			}
		}
		
		//clear
		@Override
		public void clear() {
			indexMap.clear();
		}
		
		@NotNull
		@Override
		public AttributeListCreator.AttributeList<TYPE> createNewList() {
			AttributeListImpl list = new AttributeListImpl();
			this.table().forEach(entry -> {
				Object value = entry.getValueDirect();
				if (value != UNCHANGED)
					list.indexMap.put(entry.getKey().getID(), value);
			});
			return list;
		}
		
		protected class ListModificationEntry<V> extends AbstractEntry<V> implements AttributeListCreator.ListModificationEntry<V> {
			
			public ListModificationEntry(Key<V> key) {
				super(key);
			}
			
			@Override
			public void put(@Nullable V v) {
				AttributeListModificationImpl.this.put(key, v);
			}
			
			@Override
			public void putDirect(@Nullable Object v) {
				AttributeListModificationImpl.this.putDirect(key, v);
			}
			
			@Override
			public void setDefault() {
				AttributeListModificationImpl.this.setDefault(key);
			}
			
			@Override
			public void reset() {
				AttributeListModificationImpl.this.reset(key);
			}
		}
		
		@NotNull
		@Override
		public Collection<? extends AttributeListCreator.ListModificationEntry<?>> table() {
			return new ConvertingCollection.BiDirectional<>(indexMap.table(), entry -> new ListModificationEntry<>(gen.getKey(entry.getIndex())), entry -> indexMap.getEntry(entry.getKey().getID()));
		}
	}
	
	class AttributeListChangeEvent implements ChangeEvent<TYPE> {
		
		public final AttributeList<TYPE> oldList;
		public final AttributeListModification<TYPE> mod;
		
		public AttributeListChangeEvent(AttributeList<TYPE> oldList, AttributeListModification<TYPE> mod) {
			this.oldList = oldList;
			this.mod = mod;
		}
		
		@NotNull
		@Override
		public AttributeListCreator.AttributeList<TYPE> getOldList() {
			return oldList;
		}
		
		@NotNull
		@Override
		public AttributeListCreator.AttributeListModification<TYPE> getMod() {
			return mod;
		}
		
		@NotNull
		@Override
		public <V> ChangeEventEntry<V> getEntry(@NotNull Key<V> key) {
			return new ChangeEventEntry<>() {
				
				//get
				@NotNull
				@Override
				public Key<V> getKey() {
					return key;
				}
				
				@Override
				@Nullable
				public Object getOldDirect() {
					return oldList.getDirect(key);
				}
				
				@Override
				@Nullable
				public V getOld() {
					return oldList.get(key);
				}
				
				@Override
				@Nullable
				public Object getMod() {
					return mod.getDirect(key);
				}
				
				@Override
				@Nullable
				public Object getNewDirect() {
					Object v = mod.getDirect(key);
					return v == UNCHANGED ? oldList.getDirect(key) : v;
				}
				
				@Override
				@Nullable
				public V getNew() {
					Object v = mod.getDirect(key);
					if (v == UNCHANGED)
						return oldList.get(key);
					if (v == DEFAULT)
						return key.getDefaultValue();
					//noinspection unchecked
					return (V) v;
				}
				
				//set
				@Override
				public void setMod(@Nullable Object newmod) {
					mod.putDirect(key, newmod);
				}
			};
		}
	}
}
