package space.util.key.attribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.event.Event;
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
	 * creates a new {@link AttributeList AttributeList}
	 */
	@NotNull AttributeListCreator.AttributeList<TYPE> create();
	
	/**
	 * creates a new {@link AttributeListModification AttributeListModification}
	 */
	@NotNull AttributeListCreator.AttributeListModification<TYPE> createModify();
	
	@NotNull
	@Override
	<T> Key<T> generateKey();
	
	@NotNull
	@Override
	<T> Key<T> generateKey(@NotNull Supplier<T> defaultValue);
	
	@Override
	boolean isKeyOf(@NotNull Key<?> key);
	
	//abstract version
	interface IAbstractAttributeList<TYPE> {
		
		//get
		
		/**
		 * gets the value for a given {@link Key} <b>without</b> checking if it's the default value.<br>
		 * Possible Values:
		 * <ul>
		 * <li>{@link AttributeList#DEFAULT} the default object</li>
		 * <li>{@link AttributeList#UNCHANGED} the unchanged object</li>
		 * <li>an actual value Object of type V</li>
		 * </ul>
		 */
		@Nullable <V> Object getDirect(@NotNull Key<V> key);
		
		default <V> boolean isDefault(@NotNull Key<V> key) {
			return getDirect(key) == DEFAULT;
		}
		
		default <V> boolean isNotDefault(@NotNull Key<V> key) {
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
		@NotNull AttributeListCreator<TYPE> getCreator();
		
		/**
		 * creates a new {@link AttributeListModification AttributeListModification}.
		 * Calls <code>this.{@link IAbstractAttributeList#getCreator()}.{@link AttributeListCreator#createModify()}</code> by default.
		 */
		@NotNull
		default AttributeListCreator.AttributeListModification<TYPE> createModify() {
			return getCreator().createModify();
		}
		
		/**
		 * gets an {@link java.util.Iterator} over all values
		 */
		@NotNull Collection<?> values();
		
		/**
		 * gets an {@link java.util.Iterator} over all index / value pairs
		 */
		@NotNull Collection<? extends AbstractEntry<?>> table();
	}
	
	interface AbstractEntry<V> {
		
		@NotNull Key<V> getKey();
		
		//value
		@Nullable Object getValueDirect();
	}
	
	//list itself
	
	/**
	 * An {@link AttributeList AttributeList} holds values to all keys generated.<br>
	 * It can be modified by creating a {@link AttributeListModification AttributeListModification} with {@link AttributeListCreator#createModify()},
	 * putting all the changing values there and than calling {@link AttributeList#apply(AttributeListModification) apply(AttributeListModification)} to apply changes.
	 * When applying changes the {@link Event} from {@link AttributeList#getChangeEvent()} is triggered.
	 *
	 * @see AttributeListModification the modification AttributeList
	 */
	interface AttributeList<TYPE> extends IAbstractAttributeList<TYPE> {
		
		//get
		
		/**
		 * Gets the value for a given {@link Key} or the default value if the value is equal to {@link AttributeList#DEFAULT}.
		 *
		 * @implNote This Method is NOT {@link Nullable @Nullable} and also not definable as a {@link Contract @Contract}. <br>
		 * <code>if(default != null) -> return notnull.</code><br>
		 * <code>if(default == null) -> return nullable.</code>
		 */
		<V> V get(@NotNull Key<V> key);
		
		/**
		 * Gets the value for a given {@link Key} or <code>def</code> if the value is equal to {@link AttributeList#DEFAULT}
		 */
		@Nullable
		@Contract("_, !null -> !null")
		<V> V getOrDefault(@NotNull Key<V> key, @Nullable V def);
		
		//other
		
		/**
		 * Gets the {@link Event} to use {@link Event#addHook(Object)} to add Hooks.
		 * Called then a mod is applied ({@link AttributeList#apply(AttributeListModification) apply(AttributeListModification)}).
		 */
		@NotNull Event<Consumer<ChangeEvent<?>>> getChangeEvent();
		
		/**
		 * Applies a certain modification.<br>
		 * It should be handled like this:
		 * <ul>
		 * <li>copy the mod</li>
		 * <li>replace all "replacements" with the same entry with {@link AttributeList#UNCHANGED}</li>
		 * <li>trigger the {@link Event} gotten from {@link AttributeList#getChangeEvent()}</li>
		 * <li>apply the changes to this object</li>
		 * </ul>
		 */
		void apply(@NotNull AttributeListCreator.AttributeListModification<TYPE> mod);
		
		@Override
		@NotNull Collection<? extends ListEntry<?>> table();
	}
	
	interface ListEntry<V> extends AbstractEntry<V> {
		
		@Nullable V getValue();
	}
	
	//modification
	
	interface AttributeListModification<TYPE> extends IAbstractAttributeList<TYPE> {
		
		//get
		default <V> boolean isUnchanged(@NotNull Key<V> key) {
			return getDirect(key) == UNCHANGED;
		}
		
		default <V> boolean hasChanged(@NotNull Key<V> key) {
			return getDirect(key) != UNCHANGED;
		}
		
		//put
		
		/**
		 * sets the value to v for a given {@link Key}
		 */
		<V> void put(@NotNull Key<V> key, @Nullable V v);
		
		/**
		 * sets the value to v for a given {@link Key}, directly so you can use {@link AttributeList#UNCHANGED} or {@link AttributeList#DEFAULT}
		 */
		<V> void putDirect(@NotNull Key<V> key, @Nullable Object v);
		
		/**
		 * sets the value for a given {@link Key} and returns the previous value
		 */
		@Nullable <V> V putAndGet(@NotNull Key<V> key, @Nullable V v);
		
		/**
		 * sets the value to {@link AttributeList#UNCHANGED} for a given {@link Key}
		 */
		<V> void reset(@NotNull Key<V> key);
		
		/**
		 * sets the value to {@link AttributeList#UNCHANGED} for a given {@link Key} if the current value is equal to v
		 */
		<V> boolean reset(@NotNull Key<V> key, @Nullable V v);
		
		/**
		 * sets the value to {@link AttributeList#DEFAULT} for a given {@link Key}
		 */
		<V> void setDefault(@NotNull Key<V> key);
		
		/**
		 * sets the value to {@link AttributeList#DEFAULT} for a given {@link Key} if the current value is equal to v
		 */
		<V> boolean setDefault(@NotNull Key<V> key, @Nullable V v);
		
		/**
		 * sets the value for a given {@link Key} if the current value is equal to the old value
		 */
		<V> boolean replace(@NotNull Key<V> key, @Nullable V oldValue, @Nullable V newValue);
		
		/**
		 * sets the value for a given {@link Key} if the current value is equal to the old value
		 */
		<V> boolean replace(@NotNull Key<V> key, @Nullable V oldValue, @NotNull Supplier<? extends V> newValue);
		
		/**
		 * copies from either an {@link AttributeList} or {@link AttributeListModification} all the {@link Key IKeys} over
		 */
		void copyOver(@NotNull IAbstractAttributeList list, @NotNull Key<?>... keys);
		
		//other
		
		/**
		 * resets all entries to {@link AttributeList#UNCHANGED}
		 */
		void clear();
		
		@NotNull AttributeListCreator.AttributeList<TYPE> createNewList();
		
		@NotNull
		@Override
		Collection<? extends ListModificationEntry<?>> table();
	}
	
	interface ListModificationEntry<V> extends AbstractEntry<V> {
		
		void put(@Nullable V v);
		
		void putDirect(@Nullable Object v);
		
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
	 * Contains information about ANY change of an {@link AttributeList AttributeList}.
	 * It is used in the {@link Event} gotten from {@link AttributeList#getChangeEvent()}.
	 * Use {@link ChangeEvent#getEntry(Key)} to get the Entry of one {@link Key}.
	 */
	interface ChangeEvent<TYPE> {
		
		@NotNull AttributeListCreator.AttributeList<TYPE> getOldList();
		
		@NotNull AttributeListCreator.AttributeListModification<TYPE> getMod();
		
		@NotNull <V> ChangeEventEntry<V> getEntry(@NotNull Key<V> key);
	}
	
	/**
	 * Contains information about A SINGLE change of an {@link AttributeList AttributeList}.
	 * You can get the old state, the mod and calculate the new state it will be in.
	 * "Normal" Methods will calculate the default Value, "Direct" Methods will not and can return {@link AttributeList#DEFAULT DEFAULT} or {@link AttributeList#UNCHANGED UNCHANGED}.
	 * You can also set the mod to something else with {@link ChangeEventEntry#setMod(Object)}.
	 */
	interface ChangeEventEntry<V> {
		
		//get
		@NotNull Key<V> getKey();
		
		@Nullable Object getOldDirect();
		
		@Nullable V getOld();
		
		@Nullable Object getMod();
		
		@Nullable Object getNewDirect();
		
		@Nullable V getNew();
		
		//set
		void setMod(@Nullable Object newmod);
		
		default boolean isUnchanged() {
			return getNewDirect() == UNCHANGED;
		}
		
		default boolean hasChanged() {
			return getNewDirect() != UNCHANGED;
		}
	}
}
