package space.util.keygen;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IKeyGenerator {
	
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
	
	/**
	 * @return true if the key was made by this generator and is valid
	 */
	boolean isKeyOf(IKey<?> key);
	
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
			public boolean isKeyOf(IKey<?> key) {
				return IKeyGenerator.this.isKeyOf(key);
			}
		};
	}
}
