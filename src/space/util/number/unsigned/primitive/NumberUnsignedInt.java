package space.util.number.unsigned.primitive;

import space.util.math.MathUtils;
import space.util.number.base.NumberMulDiv;
import space.util.number.unsigned.IUnsignedInteger;

public class NumberUnsignedInt extends IUnsignedInteger<NumberUnsignedInt> implements NumberMulDiv<NumberUnsignedInt> {
	
	public int i;
	
	public NumberUnsignedInt() {
	}
	
	public NumberUnsignedInt(int i) {
		this.i = i;
	}
	
	@Override
	public NumberUnsignedInt set(NumberUnsignedInt n) {
		i = n.i;
		return this;
	}
	
	@Override
	public NumberUnsignedInt make() {
		return new NumberUnsignedInt();
	}
	
	@Override
	public NumberUnsignedInt copy() {
		return new NumberUnsignedInt(i);
	}
	
	//methods with Wrapper
	@Override
	public NumberUnsignedInt add(NumberUnsignedInt numberByte) {
		return add(numberByte.i);
	}
	
	@Override
	public NumberUnsignedInt sub(NumberUnsignedInt numberByte) {
		return sub(numberByte.i);
	}
	
	@Override
	public NumberUnsignedInt add(NumberUnsignedInt numberByte, int[] overflow) {
		return add(numberByte.i, overflow);
	}
	
	@Override
	public NumberUnsignedInt sub(NumberUnsignedInt numberByte, int[] overflow) {
		return sub(numberByte.i, overflow);
	}
	
	@Override
	public NumberUnsignedInt negate() {
		return this;
	}
	
	@Override
	public NumberUnsignedInt multiply(NumberUnsignedInt numberByte) {
		return multiply(numberByte.i);
	}
	
	@Override
	public NumberUnsignedInt divide(NumberUnsignedInt numberByte) {
		return divide(numberByte.i);
	}
	
	@Override
	public NumberUnsignedInt pow2() {
		i <<= 2;
		return this;
	}
	
	@Override
	public NumberUnsignedInt pow(NumberUnsignedInt pow) {
		return pow(pow.i);
	}
	
	@Override
	public NumberUnsignedInt shiftRight(NumberUnsignedInt shift) {
		return shiftRight(shift.i);
	}
	
	@Override
	public NumberUnsignedInt shiftRightLog(NumberUnsignedInt shift) {
		return shiftRightLog(shift.i);
	}
	
	@Override
	public NumberUnsignedInt shiftLeft(NumberUnsignedInt shift) {
		return shiftLeft(shift.i);
	}
	
	//methods with primitive
	public NumberUnsignedInt add(int n) {
		i += n;
		return this;
	}
	
	public NumberUnsignedInt sub(int n) {
		i -= n;
		return this;
	}
	
	public NumberUnsignedInt add(int n, int[] overflow) {
		long i = this.i + n;
		this.i = (int) i;
		overflow[0] += i >>> 32;
		return this;
	}
	
	public NumberUnsignedInt sub(int n, int[] overflow) {
		long i = this.i - n;
		this.i = (int) i;
		overflow[0] += i >>> 32;
		return this;
	}
	
	public NumberUnsignedInt multiply(int n) {
		i *= n;
		return this;
	}
	
	public NumberUnsignedInt divide(int n) {
		i /= n;
		return this;
	}
	
	public NumberUnsignedInt pow(int pow) {
		i = MathUtils.pow(i, pow);
		return this;
	}
	
	public NumberUnsignedInt shiftRight(int shift) {
		i >>>= shift;
		return this;
	}
	
	public NumberUnsignedInt shiftRightLog(int shift) {
		i >>>= shift;
		return this;
	}
	
	public NumberUnsignedInt shiftLeft(int shift) {
		i <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return Integer.toUnsignedString(i);
	}
}
