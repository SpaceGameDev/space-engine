package space.util.delegate.map;

import space.util.baseobject.BaseObject;
import space.util.baseobject.interfaces.ICache;
import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;
import space.util.string.toStringHelper.objects.TSHObjects.TSHObjectsInstance;

import java.util.Map;
import java.util.function.Function;

import static space.util.delegate.util.CacheUtil.*;

public class CachingMap<K, V> extends DefaultingMap<K, V> implements ICache {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(CachingMap.class, d -> new CachingMap(d.map, d.def, d.iterateOverDef));
	}
	
	//no def iteration
	public CachingMap(Map<K, V> map, Function<K, V> def) {
		super(map, def);
	}
	
	//with def iteration
	public CachingMap(Map<K, V> map, DefaultFunctionWithIteration<K, V> def) {
		super(map, def);
	}
	
	public CachingMap(Map<K, V> map, Map<K, V> def) {
		super(map, def);
	}
	
	//with boolean
	public CachingMap(Map<K, V> map, Map<K, V> def, boolean iterateOverDef) {
		super(map, def, iterateOverDef);
	}
	
	public CachingMap(Map<K, V> map, Function<K, V> def, boolean iterateOverDef) {
		super(map, def, iterateOverDef);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		V thisV = map.get(key);
		if (thisV != null)
			return fromNull(thisV);
		
		V newV = def.apply((K) key);
		//noinspection unchecked
		put((K) key, toNull(newV));
		return newV;
	}
	
	@Override
	public ToStringHelperInstance toTSH(ToStringHelperCollection api) {
		TSHObjectsInstance tsh = api.getObjectPhaser().getInstance(this);
		tsh.add("map", this.map);
		tsh.add("def", this.def);
		tsh.add("iterateOverDef", this.iterateOverDef);
		return tsh;
	}
}
