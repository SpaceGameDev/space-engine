package space.util.delegate.map;

import space.util.baseobject.Cache;
import space.util.delegate.util.CacheUtil;
import space.util.indexmap.IndexMap;

import java.util.function.IntFunction;

/**
 * The {@link CachingMap} tries to get a value from the {@link CachingMap#map}, and when no value has been found, it will get the value from the {@link CachingMap#def}, write it into the local map and return it;
 * <p>
 * {@link CachingMap} is threadsafe, if the internal {@link CachingMap#map} is threadsafe.
 */
public class CachingMap<VALUE> extends ConvertingMap.BiDirectional<VALUE, VALUE> implements Cache {
	
	public IntFunction<VALUE> def;
	public boolean allowIterateOverExisting;
	
	public CachingMap(IndexMap<VALUE> indexMap, IntFunction<VALUE> def) {
		this(indexMap, def, true);
	}
	
	public CachingMap(IndexMap<VALUE> indexMap, IntFunction<VALUE> def, boolean allowIterateOverExisting) {
		super(indexMap, CacheUtil::fromCache, CacheUtil::toCache);
		this.def = def;
		this.allowIterateOverExisting = allowIterateOverExisting;
	}
	
	@Override
	public void clearCache() {
	
	}
}
