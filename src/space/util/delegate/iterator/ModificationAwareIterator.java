package space.util.delegate.iterator;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;
import space.util.string.toStringHelper.objects.TSHObjects.TSHObjectsInstance;

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
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("i", this.i);
		tsh.add("onModification", this.onModification);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
