package space.util.delegate.iterator;

import space.util.baseobjectOld.BaseObject;
import space.util.baseobjectOld.Copyable;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Iterator;

public class ModificationAwareIterator<E> extends DelegatingIterator<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(ModificationAwareIterator.class, d -> new ModificationAwareIterator(Copyable.copy(d.i), d.onModification));
	}
	
	public Runnable onModification;
	
	public ModificationAwareIterator(Iterator<E> i, Runnable onModification) {
		super(i);
		this.onModification = onModification;
	}
	
	@Override
	public void remove() {
		super.remove();
		onModification.run();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("i", this.i);
		tsh.add("onModification", this.onModification);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
