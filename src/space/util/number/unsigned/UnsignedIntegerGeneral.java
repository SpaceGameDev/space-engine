package space.util.number.unsigned;

import space.util.math.BigMath;

public final class UnsignedIntegerGeneral extends IUnsignedInteger<UnsignedIntegerGeneral> {
	
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
