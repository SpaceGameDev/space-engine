package space.util.keygen.attribute;

import space.util.concurrent.event.IEvent;
import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap.IndexMapEntry;
import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;

import java.util.function.Supplier;

public interface IAttributeListCreator extends IKeyGenerator {
	
	Object DEFAULT_OBJECT = new Object() {
		@Override
		public String toString() {
			return "DEF";
		}
	};
	Object UNCHANGED_OBJECT = new Object() {
		@Override
		public String toString() {
			return "UNCH";
		}
	};
	
	/**
	 * creates a new {@link IAttributeList}
	 */
	IAttributeList create();
	
	/**
	 * creates a new {@link IAttributeListModification}
	 */
	IAttributeListModification createModify();
	
	@Override
	<T> IKey<T> generateKey();
	
	@Override
	<T> IKey<T> generateKey(Supplier<T> defaultValue);
	
	@Override
	boolean isKeyOf(IKey<?> key);
	
	interface IAbstractAttributeList {
		
		//get
		
		/**
		 * gets the value for a given {@link IKey} <b>without</b> checking if it's the default value
		 */
		<V> V getDirect(IKey<V> key);
		
		/**
		 * gets the value for a given {@link IKey} or the default value if the value is equal to {@link IAttributeList#DEFAULT_OBJECT}
		 */
		<V> V get(IKey<V> key);
		
		/**
		 * gets the value for a given {@link IKey} or <code>def</code> if the value is equal to {@link IAttributeList#DEFAULT_OBJECT}
		 */
		<V> V getOrDefault(IKey<V> key, V def);
		
		//others
		
		/**
		 * returns the amount of entries
		 */
		int size();
		
		/**
		 * gets an {@link java.util.Iterator} over all values
		 */
		Iteratorable<Object> iterator();
		
		/**
		 * gets an {@link java.util.Iterator} over all index / value pairs
		 */
		Iteratorable<IndexMapEntry<Object>> tableIterator();
	}
	
	/**
	 * An {@link IAttributeList IAttributeList} holds values to all keys generated.<br>
	 * It can be modified by creating a {@link IAttributeListModification IAttributeListModification} with {@link IAttributeListCreator#createModify()},
	 * putting all the changing values there and than calling {@link IAttributeList#apply(IAttributeListModification) apply(IAttributeListModification)} to apply changes.
	 * When applying changes the {@link IEvent} from {@link IAttributeList#getChangeEvent()} is triggered.
	 *
	 * @see IAttributeListModification the modification AttributeList
	 */
	interface IAttributeList extends IAbstractAttributeList {
		
		/**
		 * @return
		 */
		IAttributeListModifyEvent getChangeEvent();
		
		void apply(IAttributeListModification modification);
	}
	
	interface IAttributeListModification extends IAbstractAttributeList {
		
		//put
		
		/**
		 * sets the value to v for a given {@link IKey}
		 */
		<V> void put(IKey<V> key, V v);
		
		/**
		 * sets the value for a given {@link IKey} and returns the previous value
		 */
		<V> V replace(IKey<V> key, V v);
		
		/**
		 * sets the value for a given {@link IKey} if the current value is equal to the old value
		 */
		<V> boolean replace(IKey<V> key, V oldValue, V newValue);
		
		/**
		 * sets the value for a given {@link IKey} if the current value is equal to the old value
		 */
		<V> boolean replace(IKey<V> key, V oldValue, Supplier<? extends V> newValue);
		
		/**
		 * sets the value to {@link IAttributeList#DEFAULT_OBJECT} for a given {@link IKey}
		 */
		<V> void reset(IKey<V> key);
		
		/**
		 * sets the value to {@link IAttributeList#DEFAULT_OBJECT} for a given {@link IKey} if the current value is equal to v
		 */
		<V> boolean reset(IKey<V> key, V v);
		
		/**
		 * sets the value for a given {@link IKey} if the current value is equal to {@link IAttributeList#DEFAULT_OBJECT}
		 */
		<V> boolean putIfDefault(IKey<V> key, V v);
		
		/**
		 * sets the value for a given {@link IKey} if the current value is equal to {@link IAttributeList#DEFAULT_OBJECT}
		 */
		<V> boolean putIfDefault(IKey<V> key, Supplier<? extends V> v);
		
		//other
		
		/**
		 * clears all entries
		 */
		void clear();
		
		/**
		 * resets all entries
		 */
		void resetAll();
	}
	
	interface IAttributeListModifyEvent extends IEvent {
		
		IAttributeList getAttributeList();
		
		IAttributeListModification getModification();
	}
}
