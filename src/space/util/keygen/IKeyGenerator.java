package space.util.keygen;

import java.util.function.Consumer;

public interface IKeyGenerator {
	
	/**
	 * generates a new {@link IKey}
	 *
	 * @param <T> the {@link IKey IKey's} generic
	 * @return the new {@link IKey}
	 */
	<T> IKey<T> generateKey();
	
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
			public <VALUE> IKey<VALUE> generateKey() {
				IKey<VALUE> key = IKeyGenerator.this.generateKey();
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
