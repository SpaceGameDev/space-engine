package space.util.key.impl;

import space.util.baseobject.Freeable.FreeableWithStorage;
import space.util.baseobject.ToString;
import space.util.freeableStorage.FreeableStorage;
import space.util.freeableStorage.FreeableStorageImpl;
import space.util.key.Key;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Objects;
import java.util.function.Supplier;

public class DisposableKey<T> implements Key<T>, FreeableWithStorage, ToString {
	
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
	}
	
	@Override
	public FreeableStorage getStorage() {
		return storage;
	}
	
	//storage
	public static class Storage extends FreeableStorageImpl {
		
		private final int id;
		public DisposableKeyGenerator gen;
		
		public Storage(Object referent, int id, DisposableKeyGenerator gen, FreeableStorage... lists) {
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
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("id", storage.getID());
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
