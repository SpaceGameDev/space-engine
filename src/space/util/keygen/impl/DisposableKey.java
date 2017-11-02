package space.util.keygen.impl;

import space.util.keygen.IKey;
import space.util.resource.IResource;

public class DisposableKey<T> implements IKey<T>, IResource {
	
	public final int id;
	public DisposableKeyGenerator gen;
	
	public DisposableKey(int id, DisposableKeyGenerator gen) {
		this.id = id;
		this.gen = gen;
	}
	
	@Override
	public int getID() {
		if (gen == null)
			throw new IllegalStateException("Key disposed!");
		return id;
	}
	
	@Override
	public synchronized void release() {
		if (gen == null)
			return;
		gen.dispose(this);
		gen = null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			release();
		} finally {
			super.finalize();
		}
	}
}
