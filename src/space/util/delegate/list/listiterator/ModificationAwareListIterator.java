package space.util.delegate.list.listiterator;

import space.util.baseobjectOld.BaseObject;
import space.util.baseobjectOld.Copyable;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.ListIterator;

public class ModificationAwareListIterator<E> extends DelegatingListIterator<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(ModificationAwareListIterator.class, d -> new ModificationAwareListIterator(Copyable.copy(d.iterator), d.onModification));
	}
	
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
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("iterator", this.iterator);
		tsh.add("onModification", this.onModification);
		return tsh.build();
	}
}
