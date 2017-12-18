package space.util.number.fixedpoint.primitive;

import space.util.math.UnsignedMath;
import space.util.number.base.NumberMulDiv;
import space.util.number.fixedpoint.IFixedPoint;
import spaceOld.util.math.MathUtils;

public class NumberFixedInt extends IFixedPoint<NumberFixedInt> implements NumberMulDiv<NumberFixedInt> {
	
	public int i;
	
	public NumberFixedInt() {
	}
	
	public NumberFixedInt(int i) {
		this.i = i;
	}
	
	@Override
	public NumberFixedInt set(NumberFixedInt n) {
		i = n.i;
		return this;
	}
	
	@Override
	public NumberFixedInt make() {
		return new NumberFixedInt();
	}
	
	@Override
	public NumberFixedInt copy() {
		return new NumberFixedInt(i);
	}
	
	//methods with Wrapper
	@Override
	public NumberFixedInt add(NumberFixedInt numberByte) {
		return add(numberByte.i);
	}
	
	@Override
	public NumberFixedInt sub(NumberFixedInt numberByte) {
		return sub(numberByte.i);
	}
	
	@Override
	public NumberFixedInt add(NumberFixedInt numberByte, int[] overflow) {
		return add(numberByte.i, overflow);
	}
	
	@Override
	public NumberFixedInt sub(NumberFixedInt numberByte, int[] overflow) {
		return sub(numberByte.i, overflow);
	}
	
	@Override
	public NumberFixedInt negate() {
		return this;
	}
	
	@Override
	public NumberFixedInt multiply(NumberFixedInt numberByte) {
		return multiply(numberByte.i);
	}
	
	@Override
	public NumberFixedInt divide(NumberFixedInt numberByte) {
		return divide(numberByte.i);
	}
	
	@Override
	public NumberFixedInt shiftRight(NumberFixedInt shift) {
		return shiftRight(shift.i);
	}
	
	@Override
	public NumberFixedInt shiftRightLog(NumberFixedInt shift) {
		return shiftRightLog(shift.i);
	}
	
	@Override
	public NumberFixedInt shiftLeft(NumberFixedInt shift) {
		return shiftLeft(shift.i);
	}
	
	//methods with primitive
	public NumberFixedInt add(int n) {
		i += n;
		return this;
	}
	
	public NumberFixedInt sub(int n) {
		i -= n;
		return this;
	}
	
	public NumberFixedInt add(int n, int[] overflow) {
		long i = this.i + n;
		this.i = (int) i;
		overflow[0] += i >> 32;
		return this;
	}
	
	public NumberFixedInt sub(int n, int[] overflow) {
		long i = this.i - n;
		this.i = (int) i;
		overflow[0] += i >> 32;
		return this;
	}
	
	public NumberFixedInt multiply(int n) {
		i *= n;
		return this;
	}
	
	public NumberFixedInt divide(int n) {
		i /= n;
		return this;
	}
	
	public NumberFixedInt shiftRight(int shift) {
		i >>>= shift;
		return this;
	}
	
	public NumberFixedInt shiftRightLog(int shift) {
		i >>>= shift;
		return this;
	}
	
	public NumberFixedInt shiftLeft(int shift) {
		i <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return MathUtils.fpFormatterAcc.toStringDouble(UnsignedMath.toDoubleUnsigned(i) / maxd);
	}
}
