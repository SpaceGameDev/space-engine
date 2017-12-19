package space.util.number.integer;

import space.util.math.BigMath;
import space.util.number.format.IIntegerToString;
import space.util.number.format.IntegerToString;

import java.util.function.Supplier;

public final class IntegerGeneral extends IInteger<IntegerGeneral> {
	
	public static Supplier<IIntegerToString> DEFAULT_TO_STRING = () -> IntegerToString.INSTANCE;
	
	public boolean sign;
	public int[] number;
	
	public IntegerGeneral() {
	}
	
	public IntegerGeneral(boolean sign, int[] number) {
		this.sign = sign;
		this.number = number;
	}
	
	@Override
	public IntegerGeneral set(IntegerGeneral n) {
		sign = n.sign;
		number = n.number.clone();
		return this;
	}
	
	@Override
	public IntegerGeneral make() {
		return new IntegerGeneral();
	}
	
	@Override
	public IntegerGeneral copy() {
		return new IntegerGeneral(sign, number.clone());
	}
	
	@Override
	public String toString() {
		if (number.length == 0)
			return "0";
		if (sign)
			return BigMath.toString(number);
		else
			return '-' + BigMath.toStringNegative(number);
	}
}
