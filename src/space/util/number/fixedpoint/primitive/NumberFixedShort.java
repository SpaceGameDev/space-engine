package space.util.number.fixedpoint.primitive;

import space.util.math.UnsignedMath;
import space.util.number.base.NumberMulDiv;
import space.util.number.fixedpoint.IFixedPoint;
import spaceOld.util.math.MathUtils;

public class NumberFixedShort extends IFixedPoint<NumberFixedShort> implements NumberMulDiv<NumberFixedShort> {
	
	public short s;
	
	public NumberFixedShort() {
	}
	
	public NumberFixedShort(short s) {
		this.s = s;
	}
	
	@Override
	public NumberFixedShort set(NumberFixedShort n) {
		s = n.s;
		return this;
	}
	
	@Override
	public NumberFixedShort make() {
		return new NumberFixedShort();
	}
	
	@Override
	public NumberFixedShort copy() {
		return new NumberFixedShort(s);
	}
	
	//methods with Wrapper
	@Override
	public NumberFixedShort add(NumberFixedShort numberByte) {
		return add(numberByte.s);
	}
	
	@Override
	public NumberFixedShort sub(NumberFixedShort numberByte) {
		return sub(numberByte.s);
	}
	
	@Override
	public NumberFixedShort add(NumberFixedShort numberByte, int[] overflow) {
		return add(numberByte.s, overflow);
	}
	
	@Override
	public NumberFixedShort sub(NumberFixedShort numberByte, int[] overflow) {
		return sub(numberByte.s, overflow);
	}
	
	@Override
	public NumberFixedShort negate() {
		return this;
	}
	
	@Override
	public NumberFixedShort multiply(NumberFixedShort numberByte) {
		return multiply(numberByte.s);
	}
	
	@Override
	public NumberFixedShort divide(NumberFixedShort numberByte) {
		return divide(numberByte.s);
	}
	
	@Override
	public NumberFixedShort shiftRight(NumberFixedShort shift) {
		return shiftRight(shift.s);
	}
	
	@Override
	public NumberFixedShort shiftRightLog(NumberFixedShort shift) {
		return shiftRightLog(shift.s);
	}
	
	@Override
	public NumberFixedShort shiftLeft(NumberFixedShort shift) {
		return shiftLeft(shift.s);
	}
	
	//methods with primitive
	public NumberFixedShort add(short n) {
		s += n;
		return this;
	}
	
	public NumberFixedShort sub(short n) {
		s -= n;
		return this;
	}
	
	public NumberFixedShort add(short n, int[] overflow) {
		int i = (int) s + n;
		s = (short) i;
		overflow[0] += i >> 16;
		return this;
	}
	
	public NumberFixedShort sub(short n, int[] overflow) {
		int i = (int) s - n;
		s = (short) i;
		overflow[0] += i >> 16;
		return this;
	}
	
	public NumberFixedShort multiply(short n) {
		s *= n;
		return this;
	}
	
	public NumberFixedShort divide(short n) {
		s /= n;
		return this;
	}
	
	public NumberFixedShort shiftRight(int shift) {
		s >>>= shift;
		return this;
	}
	
	public NumberFixedShort shiftRightLog(int shift) {
		s >>>= shift;
		return this;
	}
	
	public NumberFixedShort shiftLeft(int shift) {
		s <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return MathUtils.fpFormatterAcc.toStringFloat(UnsignedMath.toFloatUnsigned(s) / maxf);
	}
}
