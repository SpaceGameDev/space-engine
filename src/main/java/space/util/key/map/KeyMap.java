package space.util.key.map;

import space.util.key.Key;

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
	VALUE get(Key<?> key);
	
	/**
	 * sets the VALUE for a given {@link Key} to v
	 */
	VALUE put(Key<?> key, VALUE v);
	
	/**
	 * removes (typically sets to null) the VALUE for a given {@link Key}
	 */
	VALUE remove(Key<?> key);
	
	/**
	 * gets the key or if it is null returns <code>def</code>
	 */
	VALUE getOrDefault(Key<?> key, VALUE def);
	
	/**
	 * sets the VALUE for a given {@link Key} if the {@link Key} is not yet set (typically set to null)
	 */
	VALUE putIfAbsent(Key<?> key, VALUE v);
	
	/**
	 * sets the VALUE for a given {@link Key} if the {@link Key} is not yet set (typically set to null)
	 */
	VALUE putIfAbsent(Key<?> key, Supplier<? extends VALUE> v);
	
	/**
	 * sets the VALUE for a given {@link Key} to <code>newValue</code>, if the current VALUE is equal to <code>oldValue</code>
	 */
	boolean replace(Key<?> key, VALUE oldValue, VALUE newValue);
	
	/**
	 * sets the VALUE for a given {@link Key} to <code>newValue</code>, if the current VALUE is equal to <code>oldValue</code>
	 */
	boolean replace(Key<?> key, VALUE oldValue, Supplier<? extends VALUE> newValue);
	
	/**
	 * removes (typically sets to null) the VALUE for a given {@link Key}, if the current VALUE is equal to <code>v</code>
	 */
	boolean remove(Key<?> key, VALUE v);
	
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
	Collection<VALUE> values();
	
	/**
	 * a modifiable {@link Collection} with {@link Entry} of all KeyPairs
	 */
	Collection<? extends Entry> table();
	
	interface Entry {
		
		Key<?> getKey();
		
		//value
		Object getValue();
	}
}
