package space.util.number.unsigned;

import space.util.math.BigPrimitiveMath;
import space.util.number.base.NumberAddSub;
import space.util.number.big.BigNumber;

public class NumberBigUnsignedInteger extends IUnsignedInteger<NumberBigUnsignedInteger> implements NumberAddSub<NumberBigUnsignedInteger> {
	
	public BigNumber n;
	
	//constructor
	public NumberBigUnsignedInteger() {
		this(new int[0]);
	}
	
	public NumberBigUnsignedInteger(int magnitude) {
		this(BigPrimitiveMath.intArrayFromIntUnsigned(magnitude));
	}
	
	public NumberBigUnsignedInteger(long magnitude) {
		this(BigPrimitiveMath.intArrayFromLongUnsigned(magnitude));
	}
	
	public NumberBigUnsignedInteger(int[] magnitude) {
		this(new BigNumber(magnitude));
	}
	
	public NumberBigUnsignedInteger(BigNumber number) {
		n = number;
	}
	
	public long getCapacityBits() {
		return n.getCapacityNumberBit();
	}
	
	@Override
	public NumberBigUnsignedInteger set(NumberBigUnsignedInteger n) {
		this.n = n.n.copy();
		return this;
	}
	
	@Override
	public NumberBigUnsignedInteger make() {
		return new NumberBigUnsignedInteger();
	}
	
	@Override
	public NumberBigUnsignedInteger copy() {
		return new NumberBigUnsignedInteger(n.copy());
	}
	
	//math simple
	@Override
	public NumberBigUnsignedInteger add(NumberBigUnsignedInteger b) {
		n.addUnsigned(b.n);
		return this;
	}
	
	@Override
	public NumberBigUnsignedInteger sub(NumberBigUnsignedInteger b) {
		n.subUnsigned(b.n);
		return this;
	}
	
	//can't have overflow, as it will expand automatically
	@Override
	public NumberBigUnsignedInteger add(NumberBigUnsignedInteger b, int[] overflow) {
		return add(b);
	}
	
	@Override
	public NumberBigUnsignedInteger sub(NumberBigUnsignedInteger b, int[] overflow) {
		return sub(b);
	}
	
	@Override
	public NumberBigUnsignedInteger negate() {
		return this;
	}
	
	@Override
	public String toString() {
		return n.toString();
	}
}
