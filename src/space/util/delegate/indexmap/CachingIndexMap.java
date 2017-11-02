package space.util.delegate.indexmap;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.baseobject.interfaces.ICache;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;
import space.util.string.toStringHelper.objects.TSHObjects.TSHObjectsInstance;

import java.util.function.IntFunction;

import static space.util.delegate.util.CacheUtil.*;

public class CachingIndexMap<VALUE> extends DefaultingIndexMap<VALUE> implements BaseObject, ICache {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(CachingIndexMap.class, d -> new CachingIndexMap(Copyable.copy(d.indexMap), d.def, d.iterateOverDef));
	}
	
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
			return fromNull(thisV);
		
		VALUE newV = def.get(index);
		put(index, toNull(newV));
		return newV;
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("def", this.def);
		tsh.add("iterateOverDef", this.iterateOverDef);
		return tsh;
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
