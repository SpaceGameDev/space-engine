package space.util.delegate.collection;

import space.util.delegate.iterator.UnmodifiableIterator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * The {@link UnmodifiableCollection} will throw {@link UnsupportedOperationException} when trying to change the {@link Collection}
 */
@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
public class UnmodifiableCollection<E> extends DelegatingCollection<E> {
	
	public UnmodifiableCollection(Collection<E> coll) {
		super(coll);
	}
	
	@Override
	public Iterator<E> iterator() {
		return new UnmodifiableIterator<>(coll.iterator());
	}
	
	//throw
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("coll", this.coll);
		return tsh.build();
	}
}
