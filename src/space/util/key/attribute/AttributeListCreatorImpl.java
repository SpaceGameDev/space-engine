package space.util.key.attribute;

import space.util.baseobject.ToString;
import space.util.concurrent.event.Event;
import space.util.concurrent.event.SimpleEvent;
import space.util.delegate.collection.ConvertingCollection;
import space.util.indexmap.IndexMap;
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
	
	//delegate to gen
	@Override
	public <T> Key<T> generateKey() {
		return gen.generateKey();
	}
	
	@Override
	public <T> Key<T> generateKey(Supplier<T> def) {
		return gen.generateKey(def);
	}
	
	@Override
	public boolean isKeyOf(Key<?> key) {
		return gen.isKeyOf(key);
	}
	
	@Override
	public <T> Key<T> generateKey(T def) {
		return gen.generateKey(def);
	}
	
	@Override
	public Key<?> getKey(int id) {
		return gen.getKey(id);
	}
	
	@Override
	public Collection<Key<?>> getKeys() {
		return gen.getKeys();
	}
	
	public static <V> V correctDefault(V v, Key<V> key) {
		return v != DEFAULT ? v : key.getDefaultValue();
	}
	
	//create
	@Override
	public AttributeList create() {
		return new AttributeList();
	}
	
	@Override
	public AttributeListModification createModify() {
		return new AttributeListModification();
	}
	
	//toString
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("gen", this.gen);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public abstract class AbstractAttributeList implements IAbstractAttributeList<TYPE>, ToString {
		
		public IndexMapArrayWithDefault<Object> indexMap;
		
		protected AbstractAttributeList(Object defaultObject) {
			this(gen instanceof DisposableKeyGenerator ? new IndexMapArrayWithDefault<>(((DisposableKeyGenerator) gen).counter, defaultObject) : new IndexMapArrayWithDefault<>(defaultObject));
		}
		
		private AbstractAttributeList(IndexMapArrayWithDefault<Object> indexMap) {
			this.indexMap = indexMap;
		}
		
		//get
		@Override
		@SuppressWarnings("unchecked")
		public <V> Object getDirect(Key<V> key) {
			check(key);
			return indexMap.get(key.getID());
		}
		
		//other
		@Override
		public int size() {
			return indexMap.size();
		}
		
		@Override
		public AttributeListCreator getCreator() {
			return AttributeListCreatorImpl.this;
		}
		
		@Override
		public Collection<Object> values() {
			return indexMap.values();
		}
		
		//toString
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
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
	
	public class AttributeList extends AbstractAttributeList implements IAttributeList<TYPE> {
		
		public SimpleEvent<Consumer<ChangeEvent>> changeEvent = new SimpleEvent<>();
		
		public AttributeList() {
			super(DEFAULT);
		}
		
		//get
		@Override
		@SuppressWarnings("unchecked")
		public <V> V get(Key<V> key) {
			check(key);
			return correctDefault((V) indexMap.get(key.getID()), key);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> V getOrDefault(Key<V> key, V def) {
			check(key);
			Object o = indexMap.get(key.getID());
			return o == DEFAULT ? def : (V) o;
		}
		
		//other
		@Override
		public Event<Consumer<ChangeEvent>> getChangeEvent() {
			return changeEvent;
		}
		
		@Override
		public synchronized void apply(IAttributeListModification<TYPE> mod2) {
			//same check
			AttributeListModification mod = AttributeListCreatorImpl.this.createModify();
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
				return AttributeList.this.get(key);
			}
		}
		
		@Override
		public Collection<? extends AttributeListCreator.ListEntry<?>> table() {
			return new ConvertingCollection.BiDirectional<>(indexMap.table(), entry -> new ListEntry<>(gen.getKey(entry.getIndex())), entry -> indexMap.getEntry(entry.getKey().getID()));
		}
		

	}
	
	public class AttributeListModification extends AbstractAttributeList implements IAttributeListModification<TYPE> {
		
		public AttributeListModification() {
			super(UNCHANGED);
		}
		
		private AttributeListModification(IndexMap<Object> indexMap) {
			super(indexMap);
		}
		
		private AttributeListModification copy() {
			return new AttributeListModification(new IndexMapArrayWithDefault<>(indexMap.toArray(), UNCHANGED));
		}
		
		//put
		@Override
		@SuppressWarnings("unchecked")
		public <V> void put(Key<V> key, V v) {
			check(key);
			indexMap.put(key.getID(), v);
		}
		
		@Override
		public <V> void putDirect(Key<V> key, Object v) {
			check(key);
			indexMap.put(key.getID(), v);
		}
		
		//putAndGet
		@Override
		@SuppressWarnings("unchecked")
		public <V> V putAndGet(Key<V> key, V v) {
			check(key);
			return correctDefault((V) indexMap.put(key.getID(), v), key);
		}
		
		//set to UNCHANGED
		@Override
		@SuppressWarnings("unchecked")
		public <V> void reset(Key<V> key) {
			check(key);
			indexMap.put(key.getID(), UNCHANGED);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean reset(Key<V> key, V v) {
			check(key);
			return indexMap.replace(key.getID(), v, UNCHANGED);
		}
		
		//set to DEFAULT
		@Override
		public <V> void setDefault(Key<V> key) {
			check(key);
			indexMap.put(key.getID(), DEFAULT);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean setDefault(Key<V> key, V v) {
			check(key);
			return indexMap.replace(key.getID(), v, DEFAULT);
		}
		
		//replace
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean replace(Key<V> key, V oldValue, V newValue) {
			check(key);
			return indexMap.replace(key.getID(), oldValue, newValue);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <V> boolean replace(Key<V> key, V oldValue, Supplier<? extends V> newValue) {
			check(key);
			return indexMap.replace(key.getID(), oldValue, newValue);
		}
		
		@Override
		public void copyOver(IAbstractAttributeList list, Key<?>... keys) {
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
		
		@Override
		public IAttributeList<TYPE> createNewList() {
			AttributeList list = new AttributeList();
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
			public void put(V v) {
				AttributeListModification.this.put(key, v);
			}
			
			@Override
			public void putDirect(Object v) {
				AttributeListModification.this.putDirect(key, v);
			}
			
			@Override
			public void setDefault() {
				AttributeListModification.this.setDefault(key);
			}
			
			@Override
			public void reset() {
				AttributeListModification.this.reset(key);
			}
		}
		
		@Override
		public Collection<? extends AttributeListCreator.ListModificationEntry<?>> table() {
			return new ConvertingCollection.BiDirectional<>(indexMap.table(), entry -> new ListModificationEntry<>(gen.getKey(entry.getIndex())), entry -> indexMap.getEntry(entry.getKey().getID()));
		}
		

	}
	
	class AttributeListChangeEvent implements ChangeEvent<TYPE> {
		
		public final IAttributeList<TYPE> oldList;
		public final IAttributeListModification<TYPE> mod;
		
		public AttributeListChangeEvent(IAttributeList<TYPE> oldList, IAttributeListModification<TYPE> mod) {
			this.oldList = oldList;
			this.mod = mod;
		}
		
		@Override
		public IAttributeList<TYPE> getOldList() {
			return oldList;
		}
		
		@Override
		public IAttributeListModification<TYPE> getMod() {
			return mod;
		}
		
		@Override
		public <V> ChangeEventEntry<V> getEntry(Key<V> key) {
			return new ChangeEventEntry<>() {
				
				//get
				@Override
				public Key<V> getKey() {
					return key;
				}
				
				@Override
				public Object getOldDirect() {
					return oldList.getDirect(key);
				}
				
				@Override
				public V getOld() {
					return oldList.get(key);
				}
				
				@Override
				public Object getMod() {
					return mod.getDirect(key);
				}
				
				@Override
				public Object getNewDirect() {
					Object v = mod.getDirect(key);
					return v == UNCHANGED ? oldList.getDirect(key) : v;
				}
				
				@Override
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
				public void setMod(Object newmod) {
					mod.putDirect(key, newmod);
				}
			};
		}
	}
}
