package space.util.delegate.list.listiterator;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.delegate.util.ReferenceUtil;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.Reference;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Remaps all Entries to a {@link Reference} of type E. These References are created by the {@link ReferenceListIterator#refCreator Reference Creator} supplied with the Constructor or directly set.
 */
public class ReferenceListIterator<E> implements ToString, ListIterator<E> {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(ReferenceListIterator.class, d -> new ReferenceListIterator(Copyable.copy(d.iterator), d.refCreator));
	}
	
	public ListIterator<Reference<? extends E>> iterator;
	public Function<E, ? extends Reference<? extends E>> refCreator;
	
	public ReferenceListIterator(ListIterator<Reference<? extends E>> iterator) {
		this(iterator, ReferenceUtil.defRefCreator());
	}
	
	public ReferenceListIterator(ListIterator<Reference<? extends E>> iterator, Function<E, ? extends Reference<? extends E>> refCreator) {
		this.iterator = iterator;
		this.refCreator = refCreator;
	}
	
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}
	
	@Override
	public E next() {
		return ReferenceUtil.getSafe(iterator.next());
	}
	
	@Override
	public boolean hasPrevious() {
		return iterator.hasPrevious();
	}
	
	@Override
	public E previous() {
		return ReferenceUtil.getSafe(iterator.previous());
	}
	
	@Override
	public int nextIndex() {
		return iterator.nextIndex();
	}
	
	@Override
	public int previousIndex() {
		return iterator.previousIndex();
	}
	
	@Override
	public void remove() {
		iterator.remove();
	}
	
	@Override
	public void set(E e) {
		iterator.set(refCreator.apply(e));
	}
	
	@Override
	public void add(E e) {
		iterator.add(refCreator.apply(e));
	}
	
	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		iterator.forEachRemaining(e -> action.accept(ReferenceUtil.getSafe(e)));
	}
	
	@Override
	@SuppressWarnings("SimplifiableIfStatement")
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ReferenceListIterator))
			return false;
		return iterator.equals(((ReferenceListIterator<?>) o).iterator);
	}
	
	@Override
	public int hashCode() {
		return iterator.hashCode();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("iterator", this.iterator);
		tsh.add("refCreator", this.refCreator);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
