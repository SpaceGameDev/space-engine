package space.util.key.attribute;

import space.util.concurrent.event.Event;
import space.util.key.Key;
import space.util.key.KeyGenerator;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface AttributeListCreator<TYPE> extends KeyGenerator {
	
	Object DEFAULT = new Object() {
		@Override
		public String toString() {
			return "DEF";
		}
	};
	Object UNCHANGED = new Object() {
		@Override
		public String toString() {
			return "UNCH";
		}
	};
	
	/**
	 * creates a new {@link IAttributeList IAttributeList}
	 */
	IAttributeList<TYPE> create();
	
	/**
	 * creates a new {@link IAttributeListModification IAttributeListModification}
	 */
	IAttributeListModification<TYPE> createModify();
	
	@Override
	<T> Key<T> generateKey();
	
	@Override
	<T> Key<T> generateKey(Supplier<T> defaultValue);
	
	@Override
	boolean isKeyOf(Key<?> key);
	
	//abstract version
	interface IAbstractAttributeList<TYPE> {
		
		//get
		
		/**
		 * gets the value for a given {@link Key} <b>without</b> checking if it's the default value.<br>
		 * Possible Values:
		 * <ul>
		 * <li>{@link IAttributeList#DEFAULT} the default object</li>
		 * <li>{@link IAttributeList#UNCHANGED} the unchanged object</li>
		 * <li>an actual value Object of type V</li>
		 * </ul>
		 */
		<V> Object getDirect(Key<V> key);
		
		default <V> boolean isDefault(Key<V> key) {
			return getDirect(key) == DEFAULT;
		}
		
		default <V> boolean isNotDefault(Key<V> key) {
			return getDirect(key) != DEFAULT;
		}
		
		//others
		
		/**
		 * returns the amount of entries
		 */
		int size();
		
		/**
		 * gets the {@link AttributeListCreator} of this AttributeList
		 */
		AttributeListCreator getCreator();
		
		/**
		 * creates a new {@link IAttributeListModification IAttributeListModification}.
		 * Calls <code>this.{@link IAbstractAttributeList#getCreator()}.{@link AttributeListCreator#createModify()}</code> by default.
		 */
		default IAttributeListModification<TYPE> createModify() {
			return getCreator().createModify();
		}
		
		/**
		 * gets an {@link java.util.Iterator} over all values
		 */
		Collection<?> values();
		
		/**
		 * gets an {@link java.util.Iterator} over all index / value pairs
		 */
		Collection<? extends AbstractEntry<?>> table();
	}
	
	interface AbstractEntry<V> {
		
		Key<V> getKey();
		
		//value
		Object getValueDirect();
	}
	
	//list itself
	
	/**
	 * An {@link IAttributeList IAttributeList} holds values to all keys generated.<br>
	 * It can be modified by creating a {@link IAttributeListModification IAttributeListModification} with {@link AttributeListCreator#createModify()},
	 * putting all the changing values there and than calling {@link IAttributeList#apply(IAttributeListModification) apply(IAttributeListModification)} to apply changes.
	 * When applying changes the {@link Event} from {@link IAttributeList#getChangeEvent()} is triggered.
	 *
	 * @see IAttributeListModification the modification AttributeList
	 */
	interface IAttributeList<TYPE> extends IAbstractAttributeList<TYPE> {
		
		//get
		
		/**
		 * Gets the value for a given {@link Key} or the default value if the value is equal to {@link IAttributeList#DEFAULT}
		 */
		<V> V get(Key<V> key);
		
		/**
		 * Gets the value for a given {@link Key} or <code>def</code> if the value is equal to {@link IAttributeList#DEFAULT}
		 */
		<V> V getOrDefault(Key<V> key, V def);
		
		//other
		
		/**
		 * Gets the {@link Event} to use {@link Event#addHook(Object)} to add Hooks.
		 * Called then a mod is applied ({@link IAttributeList#apply(IAttributeListModification) apply(IAttributeListModification)}).
		 */
		Event<Consumer<ChangeEvent>> getChangeEvent();
		
		/**
		 * Applies a certain modification.<br>
		 * It should be handled like this:
		 * <ul>
		 * <li>copy the mod</li>
		 * <li>replace all "replacements" with the same entry with {@link IAttributeList#UNCHANGED}</li>
		 * <li>trigger the {@link Event} gotten from {@link IAttributeList#getChangeEvent()}</li>
		 * <li>apply the changes to this object</li>
		 * </ul>
		 */
		void apply(IAttributeListModification<TYPE> mod);
		
		@Override
		Collection<? extends ListEntry<?>> table();
	}
	
	interface ListEntry<V> extends AbstractEntry<V> {
		
		V getValue();
	}
	
	//modification
	
	interface IAttributeListModification<TYPE> extends IAbstractAttributeList<TYPE> {
		
		//get
		default <V> boolean isUnchanged(Key<V> key) {
			return getDirect(key) == UNCHANGED;
		}
		
		default <V> boolean hasChanged(Key<V> key) {
			return getDirect(key) != UNCHANGED;
		}
		
		//put
		
		/**
		 * sets the value to v for a given {@link Key}
		 */
		<V> void put(Key<V> key, V v);
		
		/**
		 * sets the value to v for a given {@link Key}, directly so you can use {@link IAttributeList#UNCHANGED} or {@link IAttributeList#DEFAULT}
		 */
		<V> void putDirect(Key<V> key, Object v);
		
		/**
		 * sets the value for a given {@link Key} and returns the previous value
		 */
		<V> V putAndGet(Key<V> key, V v);
		
		/**
		 * sets the value to {@link IAttributeList#UNCHANGED} for a given {@link Key}
		 */
		<V> void reset(Key<V> key);
		
		/**
		 * sets the value to {@link IAttributeList#UNCHANGED} for a given {@link Key} if the current value is equal to v
		 */
		<V> boolean reset(Key<V> key, V v);
		
		/**
		 * sets the value to {@link IAttributeList#DEFAULT} for a given {@link Key}
		 */
		<V> void setDefault(Key<V> key);
		
		/**
		 * sets the value to {@link IAttributeList#DEFAULT} for a given {@link Key} if the current value is equal to v
		 */
		<V> boolean setDefault(Key<V> key, V v);
		
		/**
		 * sets the value for a given {@link Key} if the current value is equal to the old value
		 */
		<V> boolean replace(Key<V> key, V oldValue, V newValue);
		
		/**
		 * sets the value for a given {@link Key} if the current value is equal to the old value
		 */
		<V> boolean replace(Key<V> key, V oldValue, Supplier<? extends V> newValue);
		
		/**
		 * copies from either an {@link IAttributeList} or {@link IAttributeListModification} all the {@link Key IKeys} over
		 */
		void copyOver(IAbstractAttributeList list, Key<?>... keys);
		
		//other
		
		/**
		 * resets all entries to {@link IAttributeList#UNCHANGED}
		 */
		void clear();
		
		IAttributeList<TYPE> createNewList();
		
		@Override
		Collection<? extends ListModificationEntry<?>> table();
	}
	
	interface ListModificationEntry<V> extends AbstractEntry<V> {
		
		void put(V v);
		
		void putDirect(Object v);
		
		void setDefault();
		
		void reset();
		
		default boolean isUnchanged() {
			return getValueDirect() == UNCHANGED;
		}
		
		default boolean hasChanged() {
			return getValueDirect() != UNCHANGED;
		}
	}
	
	//change event
	
	/**
	 * Contains information about ANY change of an {@link IAttributeList IAttributeList}.
	 * It is used in the {@link Event} gotten from {@link IAttributeList#getChangeEvent()}.
	 * Use {@link ChangeEvent#getEntry(Key)} to get the Entry of one {@link Key}.
	 */
	interface ChangeEvent<TYPE> {
		
		//get lists
		IAttributeList<TYPE> getOldList();
		
		IAttributeListModification<TYPE> getMod();
		
		<V> ChangeEventEntry<V> getEntry(Key<V> key);
	}
	
	/**
	 * Contains information about A SINGLE change of an {@link IAttributeList IAttributeList}.
	 * You can get the old state, the mod and calculate the new state it will be in.
	 * "Normal" Methods will calculate the default Value, "Direct" Methods will not and can return {@link IAttributeList#DEFAULT DEFAULT} or {@link IAttributeList#UNCHANGED UNCHANGED}.
	 * You can also set the mod to something else with {@link ChangeEventEntry#setMod(Object)}.
	 */
	interface ChangeEventEntry<V> {
		
		//get
		Key<V> getKey();
		
		Object getOldDirect();
		
		V getOld();
		
		Object getMod();
		
		Object getNewDirect();
		
		V getNew();
		
		//set
		void setMod(Object newmod);
		
		default boolean isUnchanged() {
			return getNewDirect() == UNCHANGED;
		}
		
		default boolean hasChanged() {
			return getNewDirect() != UNCHANGED;
		}
	}
}
