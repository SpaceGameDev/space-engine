package space.util.delegate.iterator;

import space.util.baseobject.ToString;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ConvertingIterator<F, T> implements Iterator<T>, ToString {
	
	public Iterator<F> iter;
	
	public ConvertingIterator(Iterator<F> iter) {
		this.iter = iter;
	}
	
	@Override
	@SuppressWarnings("TypeParameterHidesVisibleType")
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("iter", this.iter);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static <F, T> OneDirectionalUnmodifiable<F, T> createConverterOneDirectionalUnmodifiable(Iterator<F> iter, Function<? super F, ? extends T> remap) {
		return new OneDirectionalUnmodifiable<>(iter, remap);
	}
	
	public static <F, T> OneDirectional<F, T> createConverterOneDirectional(Iterator<F> iter, Function<? super F, ? extends T> remap) {
		return new OneDirectional<>(iter, remap);
	}
	
	public static class OneDirectionalUnmodifiable<F, T> extends ConvertingIterator<F, T> {
		
		public Function<? super F, ? extends T> remap;
		
		public OneDirectionalUnmodifiable(Iterator<F> iter, Function<? super F, ? extends T> remap) {
			super(iter);
			this.remap = remap;
		}
		
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}
		
		@Override
		public T next() {
			return remap.apply(iter.next());
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void forEachRemaining(Consumer<? super T> action) {
			iter.forEachRemaining(f -> action.accept(remap.apply(f)));
		}
		
		@Override
		@SuppressWarnings("TypeParameterHidesVisibleType")
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("iter", this.iter);
			tsh.add("remap", this.remap);
			return tsh.build();
		}
	}
	
	public static class OneDirectional<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public OneDirectional(Iterator<F> iter, Function<? super F, ? extends T> remap) {
			super(iter, remap);
		}
		
		@Override
		public void remove() {
			iter.remove();
		}
	}
}
