package space.util.string.toStringHelper;

import space.util.indexmap.multi.IndexMultiMap;
import space.util.indexmap.multi.IndexMultiMap2D;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperTable;

import java.util.Arrays;

public abstract class AbstractToStringHelperMapper<T> implements ToStringHelperTable<T> {
	
	public IndexMultiMap<T> map = new IndexMultiMap2D<>(IndexMultiMap2D.DEFAULT_HEIGHT, 2);
	
	@Override
	@SuppressWarnings("unchecked")
	public T put(int[] pos, Object object) {
		if (pos.length != 2 || (pos[1] == 0 || pos[1] == 1))
			throw new IllegalArgumentException(Arrays.toString(pos));
		return map.put(pos, (T) object);
	}
	
	@Override
	public abstract T build();
}
