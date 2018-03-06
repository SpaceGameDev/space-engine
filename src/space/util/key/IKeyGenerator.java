package space.util.key;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IKeyGenerator {
	
	//generate
	
	/**
	 * generates a new {@link IKey}
	 *
	 * @param <T> the {@link IKey IKey's} generic
	 * @return the new {@link IKey}
	 */
	<T> IKey<T> generateKey();
	
	/**
	 * generates a new {@link IKey}
	 *
	 * @param <T> the {@link IKey IKey's} generic
	 * @param def the default value
	 * @return the new {@link IKey}
	 */
	default <T> IKey<T> generateKey(T def) {
		return generateKey(() -> def);
	}
	
	/**
	 * generates a new {@link IKey}
	 *
	 * @param <T> the {@link IKey IKey's} generic
	 * @param def the Supplier of the default value
	 * @return the new {@link IKey}
	 */
	<T> IKey<T> generateKey(Supplier<T> def);
	
	//key
	
	/**
	 * @param id the id of the {@link IKey}
	 * @return the {@link IKey} associated with the id
	 */
	IKey<?> getKey(int id);
	
	/**
	 * @return true if the key was made by this generator and is valid
	 */
	boolean isKeyOf(IKey<?> key);
	
	/**
	 * @return all {@link IKey IKeys} available
	 */
	Collection<IKey<?>> getKeys();
	
	/**
	 * every key generated will be submitted to the Consumer
	 *
	 * @param onGen the Consumer accepting the {@link IKey IKeys}
	 * @return a new {@link IKeyGenerator} with the described functionality
	 */
	default IKeyGenerator whenGenerated(Consumer<IKey<?>> onGen) {
		return new IKeyGenerator() {
			@Override
			public <T> IKey<T> generateKey() {
				IKey<T> key = IKeyGenerator.this.generateKey();
				onGen.accept(key);
				return key;
			}
			
			@Override
			public <T> IKey<T> generateKey(T def) {
				IKey<T> key = IKeyGenerator.this.generateKey(def);
				onGen.accept(key);
				return key;
			}
			
			@Override
			public <T> IKey<T> generateKey(Supplier<T> def) {
				IKey<T> key = IKeyGenerator.this.generateKey(def);
				onGen.accept(key);
				return key;
			}
			
			@Override
			public IKey<?> getKey(int id) {
				return IKeyGenerator.this.getKey(id);
			}
			
			@Override
			public boolean isKeyOf(IKey<?> key) {
				return IKeyGenerator.this.isKeyOf(key);
			}
			
			@Override
			public Collection<IKey<?>> getKeys() {
				return IKeyGenerator.this.getKeys();
			}
		};
	}
}
