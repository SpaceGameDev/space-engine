package space.util.keygen.impl;

import space.util.delegate.list.IntList;
import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;

public class DisposableKeyGenerator implements IKeyGenerator {
	
	public int counter;
	public IntList disposed;
	
	synchronized void dispose(IKey<?> key) {
		disposed.add(key.getID());
	}
	
	@Override
	public synchronized <VALUE> DisposableKey<VALUE> generate() {
		return new DisposableKey<>(disposed.size != 0 ? disposed.poll() : counter++, this);
	}
	
	/**
	 * @returns whether the key was made by this generator or false if the key is invalid
	 */
	@Override
	public boolean isKeyOf(IKey<?> key) {
		return key instanceof DisposableKey && ((DisposableKey) key).gen == this;
	}
}
