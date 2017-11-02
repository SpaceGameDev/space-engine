package space.engine.number.fixedpoint;

import space.util.creatorOld.Creator;
import spaceOld.util.math.MathUtils;
import spaceOld.util.math.number.UnsignedMathUtils;
import spaceOld.util.math.number.fixedpoint.primitive.NumberFixedInt;

public final class FixedPointGeneral extends IFixedPoint<FixedPointGeneral> {
	
	public static final Creator<FixedPointGeneral> CREATOR = new Creator<FixedPointGeneral>() {
		@Override
		public FixedPointGeneral create() {
			return new FixedPointGeneral();
		}
		
		@Override
		public FixedPointGeneral[] createArray(int size) {
			return new FixedPointGeneral[size];
		}
	};
	public long bits;
	public int[] number;
	
	public FixedPointGeneral() {
	}
	
	public FixedPointGeneral(long bits, int[] number) {
		this.bits = bits;
		this.number = number;
	}
	
	@Override
	public Creator<FixedPointGeneral> creator() {
		return CREATOR;
	}
	
	@Override
	public FixedPointGeneral set(FixedPointGeneral n) {
		bits = n.bits;
		number = n.number.clone();
		return this;
	}
	
	@Override
	public FixedPointGeneral make() {
		return new FixedPointGeneral();
	}
	
	@Override
	public FixedPointGeneral copy() {
		return new FixedPointGeneral(bits, number.clone());
	}
	
	@Override
	public String toString() {
		return number.length == 0 ? "0" : MathUtils.fpFormatterAcc.toStringDouble(UnsignedMathUtils.toDoubleUnsigned(number[0]) / NumberFixedInt.maxd);
	}
}
