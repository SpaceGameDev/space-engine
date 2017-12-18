package space.util.number.integer.nativeType;

import space.util.math.MathUtils;
import space.util.number.base.NumberMulDiv;
import space.util.number.integer.IInteger;

public class NumberShort extends IInteger<NumberShort> implements NumberMulDiv<NumberShort> {
	
	public short s;
	
	public NumberShort() {
	}
	
	public NumberShort(short s) {
		this.s = s;
	}
	
	@Override
	public NumberShort set(NumberShort n) {
		s = n.s;
		return this;
	}
	
	@Override
	public NumberShort make() {
		return new NumberShort();
	}
	
	@Override
	public NumberShort copy() {
		return new NumberShort(s);
	}
	
	//methods with Wrapper
	@Override
	public NumberShort add(NumberShort numberByte) {
		return add(numberByte.s);
	}
	
	@Override
	public NumberShort sub(NumberShort numberByte) {
		return sub(numberByte.s);
	}
	
	@Override
	public NumberShort add(NumberShort numberByte, int[] overflow) {
		return add(numberByte.s, overflow);
	}
	
	@Override
	public NumberShort sub(NumberShort numberByte, int[] overflow) {
		return sub(numberByte.s, overflow);
	}
	
	@Override
	public NumberShort negate() {
		s ^= 0x8000;
		return this;
	}
	
	@Override
	public NumberShort multiply(NumberShort numberByte) {
		return multiply(numberByte.s);
	}
	
	@Override
	public NumberShort divide(NumberShort numberByte) {
		return divide(numberByte.s);
	}
	
	@Override
	public NumberShort pow2() {
		s <<= 2;
		return this;
	}
	
	@Override
	public NumberShort pow(NumberShort pow) {
		return pow(pow.s);
	}
	
	@Override
	public NumberShort shiftRight(NumberShort shift) {
		return shiftRight(shift.s);
	}
	
	@Override
	public NumberShort shiftRightLog(NumberShort shift) {
		return shiftRightLog(shift.s);
	}
	
	@Override
	public NumberShort shiftLeft(NumberShort shift) {
		return shiftLeft(shift.s);
	}
	
	//methods with primitive
	public NumberShort add(short n) {
		s += n;
		return this;
	}
	
	public NumberShort sub(short n) {
		s -= n;
		return this;
	}
	
	public NumberShort add(short n, int[] overflow) {
		int i = (int) s + n;
		s = (short) i;
		overflow[0] += i >> 16;
		return this;
	}
	
	public NumberShort sub(short n, int[] overflow) {
		int i = (int) s - n;
		s = (short) i;
		overflow[0] += i >> 16;
		return this;
	}
	
	public NumberShort multiply(short n) {
		s *= n;
		return this;
	}
	
	public NumberShort divide(short n) {
		s /= n;
		return this;
	}
	
	public NumberShort pow(short pow) {
		s = MathUtils.pow(s, pow);
		return this;
	}
	
	public NumberShort shiftRight(int shift) {
		s >>= shift;
		return this;
	}
	
	public NumberShort shiftRightLog(int shift) {
		s >>>= shift;
		return this;
	}
	
	public NumberShort shiftLeft(int shift) {
		s <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return Short.toString(s);
	}
}
