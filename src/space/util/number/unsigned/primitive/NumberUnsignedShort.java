package space.util.number.unsigned.primitive;

import space.util.math.MathUtils;
import space.util.number.base.NumberMulDiv;
import space.util.number.unsigned.IUnsignedInteger;

public class NumberUnsignedShort extends IUnsignedInteger<NumberUnsignedShort> implements NumberMulDiv<NumberUnsignedShort> {
	
	public short s;
	
	public NumberUnsignedShort() {
	}
	
	public NumberUnsignedShort(short s) {
		this.s = s;
	}
	
	@Override
	public NumberUnsignedShort set(NumberUnsignedShort n) {
		s = n.s;
		return this;
	}
	
	@Override
	public NumberUnsignedShort make() {
		return new NumberUnsignedShort();
	}
	
	@Override
	public NumberUnsignedShort copy() {
		return new NumberUnsignedShort(s);
	}
	
	//methods with Wrapper
	@Override
	public NumberUnsignedShort add(NumberUnsignedShort numberByte) {
		return add(numberByte.s);
	}
	
	@Override
	public NumberUnsignedShort sub(NumberUnsignedShort numberByte) {
		return sub(numberByte.s);
	}
	
	@Override
	public NumberUnsignedShort add(NumberUnsignedShort numberByte, int[] overflow) {
		return add(numberByte.s, overflow);
	}
	
	@Override
	public NumberUnsignedShort sub(NumberUnsignedShort numberByte, int[] overflow) {
		return sub(numberByte.s, overflow);
	}
	
	@Override
	public NumberUnsignedShort negate() {
		return this;
	}
	
	@Override
	public NumberUnsignedShort multiply(NumberUnsignedShort numberByte) {
		return multiply(numberByte.s);
	}
	
	@Override
	public NumberUnsignedShort divide(NumberUnsignedShort numberByte) {
		return divide(numberByte.s);
	}
	
	@Override
	public NumberUnsignedShort pow2() {
		s <<= 2;
		return this;
	}
	
	@Override
	public NumberUnsignedShort pow(NumberUnsignedShort pow) {
		return pow(pow.s);
	}
	
	@Override
	public NumberUnsignedShort shiftRight(NumberUnsignedShort shift) {
		return shiftRight(shift.s);
	}
	
	@Override
	public NumberUnsignedShort shiftRightLog(NumberUnsignedShort shift) {
		return shiftRightLog(shift.s);
	}
	
	@Override
	public NumberUnsignedShort shiftLeft(NumberUnsignedShort shift) {
		return shiftLeft(shift.s);
	}
	
	//methods with primitive
	public NumberUnsignedShort add(short n) {
		s += n;
		return this;
	}
	
	public NumberUnsignedShort sub(short n) {
		s -= n;
		return this;
	}
	
	public NumberUnsignedShort add(short n, int[] overflow) {
		int i = (int) s + n;
		s = (short) i;
		overflow[0] += i >> 16;
		return this;
	}
	
	public NumberUnsignedShort sub(short n, int[] overflow) {
		int i = (int) s - n;
		s = (short) i;
		overflow[0] += i >> 16;
		return this;
	}
	
	public NumberUnsignedShort multiply(short n) {
		s *= n;
		return this;
	}
	
	public NumberUnsignedShort divide(short n) {
		s /= n;
		return this;
	}
	
	public NumberUnsignedShort pow(short pow) {
		s = MathUtils.pow(s, pow);
		return this;
	}
	
	public NumberUnsignedShort shiftRight(int shift) {
		s >>>= shift;
		return this;
	}
	
	public NumberUnsignedShort shiftRightLog(int shift) {
		s >>>= shift;
		return this;
	}
	
	public NumberUnsignedShort shiftLeft(int shift) {
		s <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return Integer.toUnsignedString(s);
	}
}
