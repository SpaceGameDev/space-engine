package space.util.keygen;

import java.util.function.Consumer;

public interface IKeyGenerator {
	
	<VALUE> IKey<VALUE> generate();
	
	boolean isKeyOf(IKey<?> key);
	
	default IKeyGenerator whenGenerated(Consumer<IKey<?>> onGen) {
		return new IKeyGenerator() {
			@Override
			public <VALUE> IKey<VALUE> generate() {
				IKey<VALUE> key = IKeyGenerator.this.generate();
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
