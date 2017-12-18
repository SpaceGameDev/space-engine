package space.util.number.fixedpoint;

import space.util.math.BigMath;
import space.util.number.base.NumberAddSub;
import space.util.number.big.BigNumber;

//FIXME: I don't think this actually works...
public class NumberBigFixedPoint extends IFixedPoint<NumberBigFixedPoint> implements NumberAddSub<NumberBigFixedPoint> {
	
	public BigNumber n;
	
	//constructor
	public NumberBigFixedPoint() {
		this(new int[0]);
	}
	
	public NumberBigFixedPoint(int magnitude) {
		this(new int[] {magnitude});
	}
	
	public NumberBigFixedPoint(long magnitude) {
		this(intArrayFromLong(magnitude));
	}
	
	public NumberBigFixedPoint(int[] magnitude) {
		this(new BigNumber(magnitude));
	}
	
	public NumberBigFixedPoint(BigNumber number) {
		n = number;
	}
	
	private static int[] intArrayFromLong(long l) {
		if (l <= 0xFFFFFFFFL)
			return new int[] {BigMath.getLower(l)};
		return new int[] {BigMath.getLower(l), BigMath.getUpper(l)};
	}
	
	public long getCapacityBits() {
		return n.getCapacityNumberBit();
	}
	
	@Override
	public NumberBigFixedPoint set(NumberBigFixedPoint n) {
		this.n = n.n.copy();
		return this;
	}
	
	@Override
	public NumberBigFixedPoint make() {
		return new NumberBigFixedPoint();
	}
	
	@Override
	public NumberBigFixedPoint copy() {
		return new NumberBigFixedPoint(n.copy());
	}
	
	//math simple
	@Override
	public NumberBigFixedPoint add(NumberBigFixedPoint b) {
		n.addUnsigned(b.n);
		return this;
	}
	
	@Override
	public NumberBigFixedPoint sub(NumberBigFixedPoint b) {
		n.subUnsigned(b.n);
		return this;
	}
	
	@Override
	public NumberBigFixedPoint negate() {
		return this;
	}
	
	@Override
	public String toString() {
		return "1/" + n.toString();
	}
}
