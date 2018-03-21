package space.util.delegate.list.listiterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ConvertingListIterator<F, T> implements ListIterator<T>, ToString {
	
	public ListIterator<F> listIterator;
	
	public ConvertingListIterator(ListIterator<F> listIterator) {
		this.listIterator = listIterator;
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("listIterator", this.listIterator);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static <F, T> OneDirectionalUnmodifiable<F, T> createConvertingOneDirectionalUnmodifiable(ListIterator<F> listIterator, Function<F, T> remap) {
		return new OneDirectionalUnmodifiable<>(listIterator, remap);
	}
	
	public static <F, T> BiDirectional<F, T> createConvertingBiDirectional(ListIterator<F> listIterator, Function<F, T> remap, Function<T, F> reverse) {
		return new BiDirectional<>(listIterator, remap, reverse);
	}
	
	public static class OneDirectionalUnmodifiable<F, T> extends ConvertingListIterator<F, T> {
		
		public Function<F, T> remap;
		
		public OneDirectionalUnmodifiable(ListIterator<F> listIterator, Function<F, T> remap) {
			super(listIterator);
			this.remap = remap;
		}
		
		@Override
		public boolean hasNext() {
			return listIterator.hasNext();
		}
		
		@Override
		public T next() {
			return remap.apply(listIterator.next());
		}
		
		@Override
		public boolean hasPrevious() {
			return listIterator.hasPrevious();
		}
		
		@Override
		public T previous() {
			return remap.apply(listIterator.previous());
		}
		
		@Override
		public int nextIndex() {
			return listIterator.nextIndex();
		}
		
		@Override
		public int previousIndex() {
			return listIterator.previousIndex();
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void set(T t) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void add(T t) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void forEachRemaining(Consumer<? super T> action) {
			listIterator.forEachRemaining(f -> action.accept(remap.apply(f)));
		}
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("listIterator", this.listIterator);
			tsh.add("remap", this.remap);
			return tsh.build();
		}
	}
	
	public static class BiDirectional<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<T, F> reverse;
		
		public BiDirectional(ListIterator<F> listIterator, Function<F, T> remap, Function<T, F> reverse) {
			super(listIterator, remap);
			this.reverse = reverse;
		}
		
		@Override
		public void remove() {
			listIterator.remove();
		}
		
		@Override
		public void set(T t) {
			listIterator.set(reverse.apply(t));
		}
		
		@Override
		public void add(T t) {
			listIterator.add(reverse.apply(t));
		}
	}
}
