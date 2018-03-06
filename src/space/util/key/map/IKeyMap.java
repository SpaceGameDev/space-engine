package space.util.key.map;

import space.util.delegate.iterator.Iteratorable;
import space.util.key.IKey;

import java.util.function.Supplier;

/**
 * every {@link IKey} is getting a VALUE, which is <b>INDEPENDENT</b> to the generic V of the {@link IKey}
 */
public interface IKeyMap<VALUE> {
	
	//methods
	
	/**
	 * gets the VALUE for a given {@link IKey}
	 */
	VALUE get(IKey<?> key);
	
	/**
	 * sets the VALUE for a given {@link IKey} to v
	 */
	VALUE put(IKey<?> key, VALUE v);
	
	/**
	 * removes (typically sets to null) the VALUE for a given {@link IKey}
	 */
	VALUE remove(IKey<?> key);
	
	/**
	 * gets the key or if it is null returns <code>def</code>
	 */
	VALUE getOrDefault(IKey<?> key, VALUE def);
	
	/**
	 * sets the VALUE for a given {@link IKey} if the {@link IKey} is not yet set (typically set to null)
	 */
	VALUE putIfAbsent(IKey<?> key, VALUE v);
	
	/**
	 * sets the VALUE for a given {@link IKey} if the {@link IKey} is not yet set (typically set to null)
	 */
	VALUE putIfAbsent(IKey<?> key, Supplier<? extends VALUE> v);
	
	/**
	 * sets the VALUE for a given {@link IKey} to <code>newValue</code>, if the current VALUE is equal to <code>oldValue</code>
	 */
	boolean replace(IKey<?> key, VALUE oldValue, VALUE newValue);
	
	/**
	 * sets the VALUE for a given {@link IKey} to <code>newValue</code>, if the current VALUE is equal to <code>oldValue</code>
	 */
	boolean replace(IKey<?> key, VALUE oldValue, Supplier<? extends VALUE> newValue);
	
	/**
	 * removes (typically sets to null) the VALUE for a given {@link IKey}, if the current VALUE is equal to <code>v</code>
	 */
	boolean remove(IKey<?> key, VALUE v);
	
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
	 * creates a {@link java.util.Iterator} over all VALUEs
	 */
	Iteratorable<VALUE> iterator();
	
	/**
	 * creates a {@link java.util.Iterator} over all index / VALUE pairs
	 */
	Iteratorable<? extends Entry> tableIterator();
	
	interface Entry {
		
		IKey<?> getKey();
		
		//value
		Object getValue();
	}
}
