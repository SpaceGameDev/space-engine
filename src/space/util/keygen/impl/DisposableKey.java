package space.util.keygen.impl;

import space.util.baseobject.ToString;
import space.util.baseobject.additional.Freeable;
import space.util.keygen.IKey;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Objects;

public class DisposableKey<T> implements IKey<T>, Freeable, ToString {
	
	public final int id;
	public DisposableKeyGenerator gen;
	
	public DisposableKey(int id, DisposableKeyGenerator gen) {
		if (id < 0)
			throw new IllegalArgumentException("id cannot be negative, possible overflow?");
		
		this.id = id;
		this.gen = gen;
	}
	
	//id
	@Override
	public int getID() {
		if (gen == null)
			throw new IllegalStateException("Key disposed!");
		return id;
	}
	
	//free
	@Override
	public synchronized void free() {
		if (gen == null)
			return;
		gen.dispose(this);
		gen = null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			free();
		} finally {
			super.finalize();
		}
	}
	
	//equals and hashcode
	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DisposableKey))
			return false;
		DisposableKey<?> that = (DisposableKey<?>) o;
		return id == that.id && Objects.equals(gen, that.gen);
	}
	
	//toString
	@Override
	@SuppressWarnings("TypeParameterHidesVisibleType")
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("id", this.id);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
