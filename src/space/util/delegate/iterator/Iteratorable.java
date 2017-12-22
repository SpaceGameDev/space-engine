package space.util.delegate.iterator;

import java.util.Iterator;

/**
 * {@link Iteratorable} allows you to put {@link Iterator Iterators} (like the {@link java.util.ListIterator}) inside a iterating for-each loop (with ':').
 * The {@link Iteratorable#iterator()} just returns itself.
 */
public interface Iteratorable<T> extends Iterator<T>, Iterable<T> {
	
	//static
	Iteratorable<Object> EMPTY = new Iteratorable<Object>() {
		
		@Override
		public boolean hasNext() {
			return false;
		}
		
		@Override
		public Object next() {
			return null;
		}
	};
	
	static <E> Iteratorable<E> toIteratorable(Iterator<E> iter) {
		return iter instanceof Iteratorable ? (Iteratorable<E>) iter : new Iteratorable<E>() {
			
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}
			
			@Override
			public E next() {
				return iter.next();
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	static <E> Iteratorable<E> empty() {
		return (Iteratorable<E>) EMPTY;
	}
	
	static <E> Iteratorable<E> single(E elem) {
		return new Iteratorable<E>() {
			boolean gotten = false;
			
			@Override
			public boolean hasNext() {
				return gotten;
			}
			
			@Override
			public E next() {
				if (gotten)
					return null;
				gotten = true;
				return elem;
			}
		};
	}
	
	//this is the Magic!     - and it is so simple
	@Override
	default Iterator<T> iterator() {
		return this;
	}
}
