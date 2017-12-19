package space.util.number.integer;

import space.util.number.base.NumberAddSub;
import space.util.number.big.BigNumberSigned;

public class NumberBigInteger extends IInteger<NumberBigInteger> implements NumberAddSub<NumberBigInteger> {
	
	public BigNumberSigned n;
	
	//constructor
	public NumberBigInteger() {
		this(new int[0], true);
	}
	
	public NumberBigInteger(int[] magnitude, boolean sign) {
		this(new BigNumberSigned(magnitude, sign));
	}
	
	public NumberBigInteger(BigNumberSigned number) {
		n = number;
	}
	
	public NumberBigInteger(int magnitude) {
		this(new BigNumberSigned(magnitude));
	}
	
	public NumberBigInteger(long magnitude) {
		this(new BigNumberSigned(magnitude));
	}
	
	public long getCapacityBits() {
		return n.getCapacityNumberBit();
	}
	
	public void set(boolean sign, int[] number) {
		n.sign = sign;
		n.magnitude = number;
	}
	
	@Override
	public NumberBigInteger set(NumberBigInteger n) {
		this.n = n.n.copy();
		return this;
	}
	
	@Override
	public NumberBigInteger make() {
		return new NumberBigInteger();
	}
	
	@Override
	public NumberBigInteger copy() {
		return new NumberBigInteger(n.copy());
	}
	
	//math simple
	@Override
	public NumberBigInteger add(NumberBigInteger b) {
		n.addSigned(b.n);
		return this;
	}
	
	@Override
	public NumberBigInteger sub(NumberBigInteger b) {
		n.subSigned(b.n);
		return this;
	}
	
	//can't have overflow, as it will expand automatically
	@Override
	public NumberBigInteger add(NumberBigInteger b, int[] overflow) {
		return add(b);
	}
	
	@Override
	public NumberBigInteger sub(NumberBigInteger b, int[] overflow) {
		return sub(b);
	}
	
	@Override
	public NumberBigInteger negate() {
		n.negate();
		return this;
	}
	
	public NumberBigInteger trim() {
		n.trim();
		return this;
	}
	
	@Override
	public String toString() {
		return n.toString();
	}
}
