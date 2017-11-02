package space.util.conversion;

import space.util.GetClass;

import java.util.HashMap;
import java.util.Map;

public class ConverterMap<FROM, TO> implements IConverter<FROM, TO> {
	
	//map
	public Map<Key<Class<? extends FROM>, Class<? extends TO>>, IConverter<? extends FROM, ? extends TO>> map = new HashMap<>();
	
	//access
	@SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
	public <LFROM extends FROM, LTO extends TO> IConverter<LFROM, LTO> get(Class<LFROM> fromClass, Class<LTO> toClass) {
		return (IConverter<LFROM, LTO>) map.get(new Key<>(fromClass, toClass));
	}
	
	public <LFROM extends FROM, LTO extends TO> IConverter<? extends FROM, ? extends TO> put(Class<LFROM> fromClass, Class<LTO> toClass, IConverter<LFROM, LTO> conv) {
		return map.put(new Key<>(fromClass, toClass), conv);
	}
	
	@SuppressWarnings("SuspiciousMethodCalls")
	public IConverter<? extends FROM, ? extends TO> remove(Class<? extends FROM> fromClass, Class<? extends TO> toClass) {
		return map.remove(new Key<>(fromClass, toClass));
	}
	
	public void clear() {
		map.clear();
	}
	
	//convert
	@Override
	@Deprecated
	public TO convertNew(FROM from) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public <LTO extends TO> LTO convertInstance(FROM from, LTO ret) {
		IConverter<FROM, LTO> conv = get(GetClass.gClass(from), GetClass.gClass(ret));
		return conv == null ? null : conv.convertInstance(from, ret);
	}
	
	@Override
	public TO convertType(FROM from, Class<? extends TO> type) {
		return convertType0(from, type);
	}
	
	protected <LFROM extends FROM, LTO extends TO> LTO convertType0(LFROM from, Class<LTO> type) {
		IConverter<LFROM, LTO> conv = get(GetClass.gClass(from), type);
		return conv == null ? null : conv.convertType(from, type);
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
