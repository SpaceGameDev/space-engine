package space.util.key.impl;

import org.jetbrains.annotations.NotNull;
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
	
	public int counter;
	public IntList disposed;
	public IndexMap<Key<?>> allKeys = new ReferenceIndexMap<>(new IndexMapArray<>(), WeakReference::new);
	
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
	public synchronized <T> DisposableKey<T> generateKey(Supplier<T> def) {
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
		tsh.add("disposed", this.disposed == null ? "disabled" : Integer.toString(this.disposed.size()) + " Entries");
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
