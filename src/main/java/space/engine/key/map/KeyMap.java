package space.engine.key.map;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.engine.key.Key;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * every {@link Key} is getting a VALUE, which is <b>INDEPENDENT</b> to the generic V of the {@link Key}
 */
public interface KeyMap<VALUE> {
	
	//methods
	
	/**
	 * gets the VALUE for a given {@link Key}
	 */
	@Nullable VALUE get(@NotNull Key<?> key);
	
	/**
	 * sets the VALUE for a given {@link Key} to v
	 */
	@Nullable VALUE put(@NotNull Key<?> key, @Nullable VALUE v);
	
	/**
	 * removes (typically sets to null) the VALUE for a given {@link Key}
	 */
	@Nullable VALUE remove(@NotNull Key<?> key);
	
	/**
	 * gets the key or if it is null returns <code>def</code>
	 */
	@Contract("_,!null->!null")
	VALUE getOrDefault(@NotNull Key<?> key, @Nullable VALUE def);
	
	/**
	 * sets the VALUE for a given {@link Key} if the {@link Key} is not yet set (typically set to null)
	 */
	@Contract("_,!null->!null")
	VALUE putIfAbsent(@NotNull Key<?> key, @Nullable VALUE v);
	
	/**
	 * sets the VALUE for a given {@link Key} if the {@link Key} is not yet set (typically set to null)
	 */
	VALUE putIfAbsent(@NotNull Key<?> key, @NotNull Supplier<? extends VALUE> v);
	
	/**
	 * sets the VALUE for a given {@link Key} to <code>newValue</code>, if the current VALUE is equal to <code>oldValue</code>
	 */
	boolean replace(@NotNull Key<?> key, @Nullable VALUE oldValue, @Nullable VALUE newValue);
	
	/**
	 * sets the VALUE for a given {@link Key} to <code>newValue</code>, if the current VALUE is equal to <code>oldValue</code>
	 */
	boolean replace(@NotNull Key<?> key, @Nullable VALUE oldValue, @NotNull Supplier<? extends VALUE> newValue);
	
	/**
	 * removes (typically sets to null) the VALUE for a given {@link Key}, if the current VALUE is equal to <code>v</code>
	 */
	boolean remove(@NotNull Key<?> key, @Nullable VALUE v);
	
	//others
	
	/**
	 * gets the size of this map
	 */
	int size();
	
	/**
	 * clears the map
	 */
	void clear();
	
	/**
	 * a modifiable {@link Collection} containing all VALUEs
	 */
	@NotNull Collection<VALUE> values();
	
	/**
	 * a modifiable {@link Collection} with {@link Entry} of all KeyPairs
	 */
	@NotNull Collection<? extends Entry> table();
	
	interface Entry {
		
		@NotNull Key<?> getKey();
		
		//value
		@Nullable Object getValue();
	}
}
