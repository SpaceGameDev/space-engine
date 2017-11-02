package space.util.string.toStringHelper.nativeType;

import space.util.string.toStringHelper.ToStringHelperCollection;

import java.util.Objects;

public class TSHNativeTypeDefault implements TSHNativeType {
	
	public static final TSHNativeTypeDefault INSTANCE = new TSHNativeTypeDefault();
	
	@Override
	public void setToStringHelperCollection(ToStringHelperCollection coll) {
	
	}
	
	@Override
	public String toString(byte b) {
		return Byte.toString(b);
	}
	
	@Override
	public String toString(short s) {
		return Short.toString(s);
	}
	
	@Override
	public String toString(int i) {
		return Integer.toString(i);
	}
	
	@Override
	public String toString(long l) {
		return Long.toString(l);
	}
	
	@Override
	public String toString(float f) {
		return Float.toString(f);
	}
	
	@Override
	public String toString(double d) {
		return Double.toString(d);
	}
	
	@Override
	public String toString(boolean b) {
		return Boolean.toString(b);
	}
	
	@Override
	public String toString(char c) {
		return Character.toString(c);
	}
	
	@Override
	public String toString(Object o) {
		return Objects.toString(o);
	}
}
