package space.util.keygen.impl;

import space.util.baseobject.ToString;
import space.util.baseobject.additional.Freeable.FreeableWithStorage;
import space.util.keygen.IKey;
import space.util.ref.freeable.FreeableStorage;
import space.util.ref.freeable.IFreeableStorage;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Objects;
import java.util.function.Supplier;

public class DisposableKey<T> implements IKey<T>, FreeableWithStorage, ToString {
	
	public final Storage storage;
	public Supplier<T> def;
	
	public DisposableKey(int id, DisposableKeyGenerator gen) {
		this(id, gen, () -> null);
	}
	
	public DisposableKey(int id, DisposableKeyGenerator gen, T def) {
		this(id, gen, () -> def);
	}
	
	public DisposableKey(int id, DisposableKeyGenerator gen, Supplier<T> def) {
		if (id < 0)
			throw new IllegalArgumentException("id cannot be negative, possible overflow?");
		
		this.storage = new Storage(this, id, gen);
		this.def = def;
		gen.allKeys.put(id, this);
	}
	
	//storage
	public static class Storage extends FreeableStorage {
		
		private final int id;
		public DisposableKeyGenerator gen;
		
		public Storage(Object referent, int id, DisposableKeyGenerator gen, IFreeableStorage... lists) {
			super(referent, lists);
			this.id = id;
			this.gen = gen;
		}
		
		public int getID() {
			if (isFreed())
				throw new IllegalStateException("Key disposed!");
			return id;
		}
		
		@Override
		protected void handleFree() {
			gen.dispose(id);
		}
	}
	
	@Override
	public IFreeableStorage getStorage() {
		return storage;
	}
	
	//id
	@Override
	public int getID() {
		return storage.getID();
	}
	
	@Override
	public T getDefaultValue() {
		return def.get();
	}
	
	//equals and hashcode
	@Override
	public int hashCode() {
		return Integer.hashCode(getID());
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DisposableKey))
			return false;
		DisposableKey<?> that = (DisposableKey<?>) o;
		return storage.getID() == that.storage.getID() && Objects.equals(storage.gen, that.storage.gen);
	}
	
	//toString
	@Override
	@SuppressWarnings("TypeParameterHidesVisibleType")
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("id", storage.getID());
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
