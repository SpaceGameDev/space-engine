package space.util.number.unsigned;

import space.util.math.BigMath;
import space.util.number.format.IIntegerToString;
import space.util.number.format.IntegerToString;

import java.util.function.Supplier;

public final class UnsignedIntegerGeneral extends IUnsignedInteger<UnsignedIntegerGeneral> {
	
	public static Supplier<IIntegerToString> DEFAULT_TO_STRING = () -> IntegerToString.INSTANCE;
	
	public int[] number;
	
	public UnsignedIntegerGeneral() {
	}
	
	public UnsignedIntegerGeneral(int[] number) {
		this.number = number;
	}
	
	@Override
	public UnsignedIntegerGeneral set(UnsignedIntegerGeneral n) {
		number = n.number.clone();
		return this;
	}
	
	@Override
	public UnsignedIntegerGeneral make() {
		return new UnsignedIntegerGeneral();
	}
	
	@Override
	public UnsignedIntegerGeneral copy() {
		return new UnsignedIntegerGeneral(number.clone());
	}
	
	@Override
	public String toString() {
		return BigMath.toString(number);
	}
}
