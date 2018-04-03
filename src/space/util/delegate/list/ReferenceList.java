package space.util.delegate.list;

import space.util.delegate.iterator.ReferenceIterator;
import space.util.delegate.list.listiterator.ReferenceListIterator;
import space.util.delegate.util.ReferenceUtil;

import java.lang.ref.Reference;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

/**
 * Remaps all Entries to a {@link Reference} of type E. These References are created by the {@link ReferenceList#refCreator Reference Creator} supplied with the Constructor or directly set.
 */
public class ReferenceList<E> extends ConvertingList.BiDirectionalSparse<Reference<? extends E>, E> {
	
	public ReferenceList(List<Reference<? extends E>> list, Function<? super E, ? extends Reference<? extends E>> refCreator) {
		super(list, ReferenceUtil::getSafe, refCreator);
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ReferenceIterator<>(list.iterator());
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return new ReferenceListIterator<>(list.listIterator(), reverseSparse);
	}
	
	@Override
	public ListIterator<E> listIterator(int index) {
		return new ReferenceListIterator<>(list.listIterator(index), reverseSparse);
	}
	
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return new ReferenceList<>(list.subList(fromIndex, toIndex), reverseSparse);
	}
}
