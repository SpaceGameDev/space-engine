package space.util.string.toStringHelper.nativeType;

import space.util.string.toStringHelper.ToStringHelper;

public interface TSHNativeType extends ToStringHelper {
	
	String toString(byte b);
	
	String toString(short s);
	
	String toString(int i);
	
	String toString(long l);
	
	String toString(float f);
	
	String toString(double d);
	
	String toString(boolean b);
	
	String toString(char c);
	
	String toString(Object o);
}
