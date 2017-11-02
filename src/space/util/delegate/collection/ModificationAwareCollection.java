package space.util.delegate.collection;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.delegate.iterator.ModificationAwareIterator;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;
import space.util.string.toStringHelper.objects.TSHObjects.TSHObjectsInstance;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
public class ModificationAwareCollection<E> extends DelegatingCollection<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(ModificationAwareCollection.class, d -> new ModificationAwareCollection(Copyable.copy(d.coll), d.onModification));
	}
	
	public Runnable onModification;
	
	public ModificationAwareCollection(Collection<E> coll, Runnable onModification) {
		super(coll);
		this.onModification = onModification;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ModificationAwareIterator<>(coll.iterator(), onModification);
	}
	
	@Override
	public boolean add(E e) {
		boolean ret = coll.add(e);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean ret = coll.remove(o);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean ret = coll.addAll(c);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		onModification.run();
		return coll.removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean ret = coll.retainAll(c);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public void clear() {
		coll.clear();
		if (coll.size() != 0)
			onModification.run();
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		boolean ret = super.removeIf(filter);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public Spliterator<E> spliterator() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public Stream<E> stream() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public Stream<E> parallelStream() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("coll", this.coll);
		tsh.add("onModification", this.onModification);
		return tsh;
	}
}
