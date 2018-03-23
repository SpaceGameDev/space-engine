package space.util.delegate.list.listiterator;

import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.ListIterator;

/**
 * The {@link ModificationAwareListIterator} will call the {@link ModificationAwareListIterator#onModification} {@link Runnable} when the {@link ListIterator} is modified.
 */
public class ModificationAwareListIterator<E> extends DelegatingListIterator<E> {
	
	public Runnable onModification;
	
	public ModificationAwareListIterator(ListIterator<E> iterator, Runnable onModification) {
		super(iterator);
		this.onModification = onModification;
	}
	
	@Override
	public void remove() {
		super.remove();
		onModification.run();
	}
	
	@Override
	public void set(E e) {
		super.set(e);
		onModification.run();
	}
	
	@Override
	public void add(E e) {
		super.add(e);
		onModification.run();
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("iterator", this.iterator);
		tsh.add("onModification", this.onModification);
		return tsh.build();
	}
}
