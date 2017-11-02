package space.engine.number.fixedpoint;

import space.engine.number.base.BigNumber;
import space.engine.number.base.INumberSimple;
import space.util.creatorOld.Creator;
import space.util.math.BigMath;
import space.util.string.builder.IStringBuilder;
import spaceOld.util.string.builder.LayeredToString;

public class NumberBigFixedPoint extends IFixedPoint<NumberBigFixedPoint> implements INumberSimple<NumberBigFixedPoint>, LayeredToString {
	
	public static final Creator<NumberBigFixedPoint> CREATOR = new Creator<NumberBigFixedPoint>() {
		@Override
		public NumberBigFixedPoint create() {
			return new NumberBigFixedPoint();
		}
		
		@Override
		public NumberBigFixedPoint[] createArray(int size) {
			return new NumberBigFixedPoint[size];
		}
	};
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
	
	@Override
	public Creator<NumberBigFixedPoint> creator() {
		return CREATOR;
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
	
	//toString
	@Override
	public void toStringLayered(IStringBuilder<?> b) {
		b.append("1/");
		n.toStringLayered(b);
	}
	
	@Override
	public String toString() {
		return n.toString0();
	}
}
