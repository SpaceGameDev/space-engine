package space.util.delegate.map.specific;

import space.util.delegate.map.GetOverrideMap;

import java.util.Map;

/**
 * if you get() a Class, and the class does not exist, it will try to get the superclass, until a value is found or no superclass exists
 */
public class GetClassOrSuperMap<K extends Class<?>, V> extends GetOverrideMap<K, V> {
	
	public GetClassOrSuperMap(Map<K, V> map) {
		super(map);
	}
	
	@Override
	public boolean containsKey(Object key) {
		if (!(key instanceof Class<?>))
			return false;
		
		for (Class<?> clazz = (Class<?>) key; clazz != null; clazz = clazz.getSuperclass())
			if (map.containsKey(clazz))
				return true;
		return false;
	}
	
	@Override
	public V get(Object key) {
		if (!(key instanceof Class<?>))
			return null;
		
		for (Class<?> clazz = (Class<?>) key; clazz != null; clazz = clazz.getSuperclass()) {
			V ret = map.get(clazz);
			if (ret != null)
				return ret;
		}
		return null;
	}
}
