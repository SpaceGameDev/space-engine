package space.util.keygen.map;

import space.util.keygen.IKey;

import java.util.function.Supplier;

/**
 * every {@link IKey} is getting a Value, <b>DEPENDENT</b> on the generic V of the {@link IKey}
 */
public interface IKeyMapKeyGeneric<VALUE> extends IKeyMap<VALUE> {
	
	//methods
	boolean contains(IKey<?> key);
	
	<V extends VALUE> V get(IKey<V> key);
	
	<V extends VALUE> V put(IKey<V> key, V v);
	
	<V extends VALUE> V remove(IKey<V> key);
	
	<V extends VALUE> V getOrDefault(IKey<V> key, V def);
	
	<V extends VALUE> V putIfAbsent(IKey<V> key, V v);
	
	<V extends VALUE> V putIfAbsent(IKey<V> key, Supplier<? extends V> v);
	
	<V extends VALUE> boolean replace(IKey<V> key, V oldValue, V newValue);
	
	<V extends VALUE> boolean replace(IKey<V> key, V oldValue, Supplier<? extends V> newValue);
	
	<V extends VALUE> boolean remove(IKey<V> key, V v);
}
