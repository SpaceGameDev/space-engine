package space.util.key;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface KeyGenerator {
	
	//generate
	
	/**
	 * generates a new {@link Key}
	 *
	 * @param <T> the {@link Key Key's} generic
	 * @return the new {@link Key}
	 */
	@NotNull <T> Key<T> generateKey();
	
	/**
	 * generates a new {@link Key}
	 *
	 * @param <T> the {@link Key Key's} generic
	 * @param def the default value
	 * @return the new {@link Key}
	 */
	@NotNull
	default <T> Key<T> generateKey(T def) {
		return generateKey(() -> def);
	}
	
	/**
	 * generates a new {@link Key}
	 *
	 * @param <T> the {@link Key Key's} generic
	 * @param def the Supplier of the default value
	 * @return the new {@link Key}
	 */
	@NotNull <T> Key<T> generateKey(Supplier<T> def);
	
	//key
	
	/**
	 * gets the key of an id
	 *
	 * @param id the id of the {@link Key}
	 * @return the {@link Key} associated with the id
	 */
	@Nullable Key<?> getKey(int id);
	
	/**
	 * checks weather the key is from this generator
	 *
	 * @return true if the key was made by this generator and is valid
	 */
	boolean isKeyOf(Key<?> key);
	
	/**
	 * gets all keys
	 *
	 * @return all {@link Key IKeys} available
	 */
	@NotNull Collection<Key<?>> getKeys();
	
	/**
	 * every key generated will be submitted to the Consumer
	 *
	 * @param onGen the Consumer accepting the {@link Key IKeys}
	 * @return a new {@link KeyGenerator} with the described functionality
	 */
	default KeyGenerator whenGenerated(Consumer<Key<?>> onGen) {
		return new KeyGenerator() {
			@NotNull
			@Override
			public <T> Key<T> generateKey() {
				Key<T> key = KeyGenerator.this.generateKey();
				onGen.accept(key);
				return key;
			}
			
			@NotNull
			@Override
			public <T> Key<T> generateKey(T def) {
				Key<T> key = KeyGenerator.this.generateKey(def);
				onGen.accept(key);
				return key;
			}
			
			@NotNull
			@Override
			public <T> Key<T> generateKey(Supplier<T> def) {
				Key<T> key = KeyGenerator.this.generateKey(def);
				onGen.accept(key);
				return key;
			}
			
			@Override
			public Key<?> getKey(int id) {
				return KeyGenerator.this.getKey(id);
			}
			
			@Override
			public boolean isKeyOf(Key<?> key) {
				return KeyGenerator.this.isKeyOf(key);
			}
			
			@NotNull
			@Override
			public Collection<Key<?>> getKeys() {
				return KeyGenerator.this.getKeys();
			}
		};
	}
}
