package space.util.delegate.indexmap;

import space.util.baseobject.Cache;
import space.util.baseobject.ToString;
import space.util.delegate.map.CachingMap;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.function.IntFunction;

import static space.util.delegate.util.CacheUtil.*;

/**
 * The {@link CachingMap} tries to get a value from the {@link CachingMap#map}, and when no value has been found, it will get the value from the {@link CachingMap#def}, write it into the local map and return it;
 * <p>
 * {@link CachingMap} is threadsafe, if the internal {@link CachingMap#map} is threadsafe.
 */
public class CachingIndexMap<VALUE> extends DefaultingIndexMap<VALUE> implements ToString, Cache {
	
	//no def iteration
	public CachingIndexMap(IndexMap<VALUE> indexMap, IntFunction<VALUE> def) {
		super(indexMap, def);
	}
	
	public CachingIndexMap(IndexMap<VALUE> indexMap, DefaultFunction<VALUE> def) {
		super(indexMap, def);
	}
	
	//with def iteration
	public CachingIndexMap(IndexMap<VALUE> indexMap, DefaultFunctionWithIteration<VALUE> def) {
		super(indexMap, def);
	}
	
	public CachingIndexMap(IndexMap<VALUE> indexMap, IndexMap<VALUE> def) {
		super(indexMap, def);
	}
	
	//with boolean
	public CachingIndexMap(IndexMap<VALUE> indexMap, IndexMap<VALUE> def, boolean iterateOverDef) {
		super(indexMap, def, iterateOverDef);
	}
	
	public CachingIndexMap(IndexMap<VALUE> indexMap, DefaultFunction<VALUE> def, boolean iterateOverDef) {
		super(indexMap, def, iterateOverDef);
	}
	
	//get
	@Override
	public VALUE get(int index) {
		VALUE thisV = indexMap.get(index);
		if (thisV != null)
			return fromNullToObject(thisV);
		
		VALUE newV = def.get(index);
		put(index, fromObjectToNull(newV));
		return newV;
	}
	
	@Override
	public void clearCache() {
		indexMap.clear();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("def", this.def);
		tsh.add("iterateOverDef", this.iterateOverDef);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
