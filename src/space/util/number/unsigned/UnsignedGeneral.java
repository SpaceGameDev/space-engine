package space.util.number.unsigned;

import space.util.math.BigMath;
import space.util.number.format.IIntegerToString;
import space.util.number.format.IntegerToString;

import java.util.function.Supplier;

public final class UnsignedGeneral extends IUnsignedInteger<UnsignedGeneral> {
	
	public static Supplier<IIntegerToString> DEFAULT_TO_STRING = () -> IntegerToString.INSTANCE;
	
	public int[] number;
	
	public UnsignedGeneral() {
	}
	
	public UnsignedGeneral(int[] number) {
		this.number = number;
	}
	
	public void set(int[] number) {
		this.number = number;
	}
	
	@Override
	public UnsignedGeneral set(UnsignedGeneral n) {
		number = n.number.clone();
		return this;
	}
	
	@Override
	public UnsignedGeneral make() {
		return new UnsignedGeneral();
	}
	
	@Override
	public UnsignedGeneral copy() {
		return new UnsignedGeneral(number.clone());
	}
	
	@Override
	public String toString() {
		return BigMath.toString(number);
	}
}
