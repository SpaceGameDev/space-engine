package space.util.keygen.impl;

import space.util.baseobject.ToString;
import space.util.delegate.list.IntList;
import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class DisposableKeyGenerator implements IKeyGenerator, ToString {
	
	public int counter;
	public IntList disposed = new IntList();
	
	protected synchronized void dispose(IKey<?> key) {
		disposed.add(key.getID());
	}
	
	@Override
	public synchronized <VALUE> DisposableKey<VALUE> generateKey() {
		return new DisposableKey<>(disposed.isEmpty() ? counter++ : disposed.poll(), this);
	}
	
	@Override
	public boolean isKeyOf(IKey<?> key) {
		return key instanceof DisposableKey && ((DisposableKey) key).gen == this;
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("counter", this.counter);
		tsh.add("disposed", Integer.toString(this.disposed.size()) + " Entries");
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
