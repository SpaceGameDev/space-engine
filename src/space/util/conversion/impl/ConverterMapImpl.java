package space.util.conversion.impl;

import space.util.baseobject.ToString;
import space.util.conversion.Converter;
import space.util.conversion.ConverterMap.ConverterMapAdvanced;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * maps a {@link Key}[from-class, to-class] to a {@link Converter}[from, to].
 * Is threadsafe if the internal {@link Map} is threadsafe.
 */
public class ConverterMapImpl<MINFROM, MINTO> implements ConverterMapAdvanced<MINFROM, MINTO>, ToString {
	
	public Map<Key<Class<? extends MINFROM>, Class<? extends MINTO>>, Converter<? extends MINFROM, ? extends MINTO>> map;
	
	public ConverterMapImpl(Map<Key<Class<? extends MINFROM>, Class<? extends MINTO>>, Converter<? extends MINFROM, ? extends MINTO>> map) {
		this.map = map;
	}
	
	//get
	@Override
	@SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		if (fromClass.equals(toClass))
			return Converter.identity();
		return (Converter<FROM, TO>) map.get(new Key<>(fromClass, toClass));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> computeIfAbsent(Class<FROM> fromClass, Class<TO> toClass, BiFunction<Class<? extends MINFROM>, Class<? extends MINTO>, Converter<? extends MINFROM, ? extends MINTO>> function) {
		if (fromClass.equals(toClass))
			return Converter.identity();
		return (Converter<FROM, TO>) map.computeIfAbsent(new Key<>(fromClass, toClass), key -> function.apply(key.key1, key.key2));
	}
	
	//put
	@Override
	public <FROM extends MINFROM, TO extends MINTO> void putConverter(Class<FROM> fromClass, Class<TO> toClass, Converter<FROM, TO> converter) {
		map.put(new Key<>(fromClass, toClass), converter);
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static class Key<KEY1, KEY2> implements ToString {
		
		public final KEY1 key1;
		public final KEY2 key2;
		
		public Key(KEY1 key1, KEY2 key2) {
			this.key1 = key1;
			this.key2 = key2;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof Key))
				return false;
			
			Key<?, ?> key = (Key<?, ?>) o;
			return key1.equals(key.key1) && key2.equals(key.key2);
		}
		
		@Override
		public int hashCode() {
			int result = key1.hashCode();
			result = 31 * result + key2.hashCode();
			return result;
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("key1", this.key1);
			tsh.add("key2", this.key2);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
}
