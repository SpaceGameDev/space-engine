package space.util.string.toStringHelper;

import space.util.indexmap.multi.IndexMultiMap;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperTable;

public abstract class AbstractToStringHelperTable<T> implements ToStringHelperTable<T> {
	
	public IndexMultiMap<T> map;
	
	public AbstractToStringHelperTable(int dimensions) {
		map = ToStringHelper.getOptimalMultiMap(dimensions);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T put(int[] pos, Object object) {
		return map.put(pos, (T) object);
	}
	
	@Override
	public abstract T build();
}
