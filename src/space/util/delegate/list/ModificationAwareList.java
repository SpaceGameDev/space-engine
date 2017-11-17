package space.util.delegate.list;

import space.util.baseobjectOld.BaseObject;
import space.util.baseobjectOld.Copyable;
import space.util.delegate.iterator.ModificationAwareIterator;
import space.util.delegate.list.listiterator.ModificationAwareListIterator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
public class ModificationAwareList<E> extends DelegatingList<E> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(ModificationAwareList.class, d -> new ModificationAwareList(Copyable.copy(d.list), d.onModification));
	}
	
	public Runnable onModification;
	
	public ModificationAwareList(List<E> list, Runnable onModification) {
		super(list);
		this.onModification = onModification;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ModificationAwareIterator<>(list.iterator(), onModification);
	}
	
	@Override
	public boolean add(E e) {
		boolean ret = list.add(e);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean ret = list.remove(o);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean ret = list.addAll(c);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean ret = list.addAll(index, c);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		onModification.run();
		return list.removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean ret = list.retainAll(c);
		if (ret)
			onModification.run();
		return ret;
	}
	
	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		boolean[] mod = new boolean[1];
		list.replaceAll(e -> {
			E ret = operator.apply(e);
			if (ret != e)
				mod[0] = true;
			return ret;
		});
		if (mod[0])
			onModification.run();
	}
	
	@Override
	public void sort(Comparator<? super E> c) {
		list.sort(c);
		onModification.run();
	}
	
	@Override
	public void clear() {
		list.clear();
		if (list.size() != 0)
			onModification.run();
	}
	
	@Override
	public E set(int index, E element) {
		E ret = list.set(index, element);
		onModification.run();
		return ret;
	}
	
	@Override
	public void add(int index, E element) {
		list.add(index, element);
		onModification.run();
	}
	
	@Override
	public E remove(int index) {
		E ret = list.remove(index);
		onModification.run();
		return ret;
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return new ModificationAwareListIterator<>(list.listIterator(), onModification);
	}
	
	@Override
	public ListIterator<E> listIterator(int index) {
		return new ModificationAwareListIterator<>(list.listIterator(), onModification);
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
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("list", this.list);
		tsh.add("onModification", this.onModification);
		return tsh.build();
	}
}
