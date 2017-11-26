package space.util.string.toStringHelper;

import space.util.indexmap.multi.IndexMultiMap;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperTable;

public abstract class AbstractToStringHelperTable<T> implements ToStringHelperTable<T> {
	
	public IndexMultiMap<T> map;
	
	public AbstractToStringHelperTable(int dimensions) {
		map = ToStringHelper.getOptimalMultiMap(dimensions);
	}
	
	@Override
	public void put(int[] pos, T object) {
		map.put(pos, object);
	}
	
	@Override
	public abstract T build();
}
