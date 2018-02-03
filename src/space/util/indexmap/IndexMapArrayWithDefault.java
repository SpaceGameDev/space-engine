package space.util.indexmap;

import space.util.ArrayUtils;

import java.util.Arrays;

public class IndexMapArrayWithDefault<VALUE> extends IndexMapArray<VALUE> {
	
	public VALUE defaultFiller;
	
	public IndexMapArrayWithDefault(VALUE defaultFiller) {
		this(DEFAULT_CAPACITY, defaultFiller);
	}
	
	public IndexMapArrayWithDefault(int initCapacity, VALUE defaultFiller) {
		super(initCapacity);
		Arrays.fill(array, defaultFiller);
		this.defaultFiller = defaultFiller;
	}
	
	public IndexMapArrayWithDefault(VALUE[] array, VALUE defaultFiller) {
		super(array);
		this.defaultFiller = defaultFiller;
	}
	
	@Override
	public boolean ensureCapacity(int capa) {
		int oldl = array.length;
		if (oldl < capa) {
			array = Arrays.copyOf(array, ArrayUtils.getOptimalArraySizeExpansion(oldl, capa, EXPAND_SHIFT));
			Arrays.fill(array, oldl, array.length, defaultFiller);
			return true;
		}
		return false;
	}
	
	@Override
	public void clear() {
		Arrays.fill(array, 0, length, defaultFiller);
	}
}
