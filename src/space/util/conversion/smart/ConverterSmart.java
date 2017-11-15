package space.util.conversion.smart;

import space.util.baseobject.BaseObject;
import space.util.conversion.Converter;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.HashMap;

public class ConverterSmart<MIN> implements IConverterSmart<MIN> {
	
	@Override
	public <FROM extends MIN, TO extends MIN> Converter<FROM, TO> getConverter(Class<FROM> fromClass, Class<TO> toClass) {
		return null;
	}
	
	//classes
	interface Entry<FROM, TO> extends Converter<FROM, TO> {
		
		@Override
		TO convertNew(FROM from) throws UnsupportedOperationException;
		
		@Override
		<LTO extends TO> LTO convertInstance(FROM from, LTO ret);
		
		boolean isLossless();
	}
	
	public class EntryWrapper<FROM, TO> implements Entry<FROM, TO>, BaseObject {
		
		public Converter<FROM, TO> conv;
		public boolean isLossless;
		
		public EntryWrapper(Converter<FROM, TO> conv, boolean isLossless) {
			this.conv = conv;
			this.isLossless = isLossless;
		}
		
		@Override
		public TO convertNew(FROM from) throws UnsupportedOperationException {
			return conv.convertNew(from);
		}
		
		@Override
		public <LTO extends TO> LTO convertInstance(FROM from, LTO ret) {
			return conv.convertInstance(from, ret);
		}
		
		@Override
		public boolean isLossless() {
			return isLossless;
		}
		
		@Override
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("conv", this.conv);
			tsh.add("isLossless", this.isLossless);
			return tsh.build();
		}
		
		@Override
		public String toString() {
			return toString0();
		}
	}
	
	public class Node<NODE> {
		
		public final Class<NODE> clazzNode;
		public HashMap<Class<? extends MIN>, Entry<? extends MIN, NODE>> mapConvertFrom = new HashMap<>();
		public HashMap<Class<? extends MIN>, Entry<NODE, ? extends MIN>> mapConvertTo = new HashMap<>();
		
		public Node(Class<NODE> clazzNode) {
			this.clazzNode = clazzNode;
		}
		
		//convertFrom
		@SuppressWarnings("unchecked")
		public <CLAZZ extends MIN> Entry<CLAZZ, NODE> getConvertFrom(Class<CLAZZ> clazz) {
			return (Entry<CLAZZ, NODE>) mapConvertFrom.get(clazz);
		}
		
		public <CLAZZ extends MIN> Converter<? extends MIN, NODE> putConvertFrom(Class<CLAZZ> clazz, Entry<CLAZZ, NODE> node) {
			return mapConvertFrom.put(clazz, node);
		}
		
		//convertTo
		@SuppressWarnings("unchecked")
		public <CLAZZ extends MIN> Entry<NODE, CLAZZ> getConvertTo(Class<CLAZZ> clazz) {
			return (Entry<NODE, CLAZZ>) mapConvertTo.get(clazz);
		}
		
		public <CLAZZ extends MIN> Converter<NODE, ? extends MIN> putConvertTo(Class<CLAZZ> clazz, Entry<NODE, CLAZZ> node) {
			return mapConvertTo.put(clazz, node);
		}
		
		@Override
		public String toString() {
			return "Node " + clazzNode.getName();
		}
	}
}
