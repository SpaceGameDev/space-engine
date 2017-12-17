package space.util.number.integer;

import space.util.baseobject.ToString;
import space.util.math.BigMath;
import space.util.string.toStringHelper.ToStringHelper;

public final class IntegerGeneral extends IInteger<IntegerGeneral> implements ToString {
	
	public byte sign;
	public int[] number;
	
	public IntegerGeneral() {
	}
	
	public IntegerGeneral(byte sign, int[] number) {
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
	public <T> T toTSH(ToStringHelper<T> api) {
		if (number.length == 0)
			return api.toString("0");
		if (sign == 0)
			return api.toString(BigMath.toString(number));
		else
			return api.toString('-' + BigMath.toStringNegative(number));
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
