package space.util.number.unsigned;

import space.util.math.BigPrimitiveMath;
import space.util.number.base.NumberAddSub;
import space.util.number.big.BigNumber;

public class NumberUnsignedBigInteger extends IUnsignedInteger<NumberUnsignedBigInteger> implements NumberAddSub<NumberUnsignedBigInteger> {
	
	public BigNumber n;
	
	//constructor
	public NumberUnsignedBigInteger() {
		this(new int[0]);
	}
	
	public NumberUnsignedBigInteger(int magnitude) {
		this(BigPrimitiveMath.intArrayFromIntUnsigned(magnitude));
	}
	
	public NumberUnsignedBigInteger(long magnitude) {
		this(BigPrimitiveMath.intArrayFromLongUnsigned(magnitude));
	}
	
	public NumberUnsignedBigInteger(int[] magnitude) {
		this(new BigNumber(magnitude));
	}
	
	public NumberUnsignedBigInteger(BigNumber number) {
		n = number;
	}
	
	public long getCapacityBits() {
		return n.getCapacityNumberBit();
	}
	
	public void set(int[] number) {
		n.magnitude = number;
	}
	
	@Override
	public NumberUnsignedBigInteger set(NumberUnsignedBigInteger n) {
		this.n = n.n.copy();
		return this;
	}
	
	@Override
	public NumberUnsignedBigInteger make() {
		return new NumberUnsignedBigInteger();
	}
	
	@Override
	public NumberUnsignedBigInteger copy() {
		return new NumberUnsignedBigInteger(n.copy());
	}
	
	//math simple
	@Override
	public NumberUnsignedBigInteger add(NumberUnsignedBigInteger b) {
		n.addUnsigned(b.n);
		return this;
	}
	
	@Override
	public NumberUnsignedBigInteger sub(NumberUnsignedBigInteger b) {
		n.subUnsigned(b.n);
		return this;
	}
	
	//can't have overflow, as it will expand automatically
	@Override
	public NumberUnsignedBigInteger add(NumberUnsignedBigInteger b, int[] overflow) {
		return add(b);
	}
	
	@Override
	public NumberUnsignedBigInteger sub(NumberUnsignedBigInteger b, int[] overflow) {
		return sub(b);
	}
	
	@Override
	public NumberUnsignedBigInteger negate() {
		return this;
	}
	
	@Override
	public String toString() {
		return n.toString();
	}
}
