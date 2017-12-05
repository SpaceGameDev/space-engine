package space.util.conversion.impl;

import space.util.conversion.Converter;
import space.util.conversion.ConverterMap;

import java.util.Map;

/**
 * maps a {@link Key}[from-class, to-class] to a {@link Converter}[from, to].
 * Is threadsafe if the internal {@link Map} is threadsafe.
 */
public class ConverterMapImpl<MINFROM, MINTO> implements ConverterMap<MINFROM, MINTO> {
	
	public Map<Key<Class<?>, Class<?>>, Converter<?, ?>> map;
	
	public ConverterMapImpl(Map<Key<Class<?>, Class<?>>, Converter<?, ?>> map) {
		this.map = map;
	}
	
	//put
	public <FROM extends MINFROM, TO extends MINTO> Converter<?, ?> putConverter(Class<FROM> fromClass, Class<TO> toClass, Converter<FROM, TO> converter) {
		return map.put(new Key<>(fromClass, toClass), converter);
	}
	
	//get
	@Override
	@SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
	public <FROM extends MINFROM, TO extends MINTO> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		if (fromClass.equals(toClass))
			return Converter.identity();
		return (Converter<FROM, TO>) map.get(new Key<>(fromClass, toClass));
	}
	
	public static class Key<KEY1, KEY2> {
		
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
	}
}
