package space.util.key.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.util.baseobject.ToString;
import space.util.delegate.indexmap.ReferenceIndexMap;
import space.util.delegate.specific.IntList;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;
import space.util.key.Key;
import space.util.key.KeyGenerator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.function.Supplier;

public class DisposableKeyGenerator implements KeyGenerator, ToString {
	
	protected int counter;
	
	/**
	 * null -> no reuse allowed
	 */
	@Nullable
	protected IntList disposed;
	@NotNull
	protected IndexMap<Key<?>> allKeys = new ReferenceIndexMap<>(new IndexMapArray<>(), WeakReference::new);
	
	public DisposableKeyGenerator(boolean allowReuse) {
		if (allowReuse)
			disposed = new IntList(0);
	}
	
	//generate
	@NotNull
	@Override
	public synchronized <T> DisposableKey<T> generateKey() {
		return generateKey(() -> null);
	}
	
	@NotNull
	@Override
	public synchronized <T> DisposableKey<T> generateKey(@NotNull Supplier<T> def) {
		DisposableKey<T> key = new DisposableKey<>(disposed != null && !disposed.isEmpty() ? disposed.poll() : counter++, this, def);
		allKeys.put(key.getID(), key);
		return key;
	}
	
	//key
	@Override
	public synchronized Key<?> getKey(int id) {
		return allKeys.get(id);
	}
	
	@Override
	public boolean isKeyOf(Key<?> key) {
		return key instanceof DisposableKey && ((DisposableKey) key).storage.gen == this;
	}
	
	@NotNull
	@Override
	public Collection<Key<?>> getKeys() {
		return allKeys.values();
	}
	
	@Override
	public int estimateKeyPoolMax() {
		return counter;
	}
	
	//dispose
	protected synchronized void dispose(int id) {
		allKeys.remove(id);
		if (disposed != null)
			disposed.add(id);
	}
	
	//toString
	@NotNull
	@Override
	public <TSHTYPE> TSHTYPE toTSH(@NotNull ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("counter", this.counter);
		tsh.add("disposed", this.disposed == null ? "disabled" : this.disposed.size() + " Entries");
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
