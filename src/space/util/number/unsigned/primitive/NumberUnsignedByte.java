package space.util.number.unsigned.primitive;

import space.util.math.MathUtils;
import space.util.number.base.NumberMulDiv;
import space.util.number.unsigned.IUnsignedInteger;

public class NumberUnsignedByte extends IUnsignedInteger<NumberUnsignedByte> implements NumberMulDiv<NumberUnsignedByte> {
	
	public byte b;
	
	public NumberUnsignedByte() {
	}
	
	public NumberUnsignedByte(byte b) {
		this.b = b;
	}
	
	@Override
	public NumberUnsignedByte set(NumberUnsignedByte n) {
		b = n.b;
		return this;
	}
	
	@Override
	public NumberUnsignedByte make() {
		return new NumberUnsignedByte();
	}
	
	@Override
	public NumberUnsignedByte copy() {
		return new NumberUnsignedByte(b);
	}
	
	//methods with Wrapper
	@Override
	public NumberUnsignedByte add(NumberUnsignedByte numberByte) {
		return add(numberByte.b);
	}
	
	@Override
	public NumberUnsignedByte sub(NumberUnsignedByte numberByte) {
		return sub(numberByte.b);
	}
	
	@Override
	public NumberUnsignedByte add(NumberUnsignedByte numberByte, int[] overflow) {
		return add(numberByte.b, overflow);
	}
	
	@Override
	public NumberUnsignedByte sub(NumberUnsignedByte numberByte, int[] overflow) {
		return sub(numberByte.b, overflow);
	}
	
	@Override
	public NumberUnsignedByte negate() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public NumberUnsignedByte multiply(NumberUnsignedByte numberByte) {
		return multiply(numberByte.b);
	}
	
	@Override
	public NumberUnsignedByte divide(NumberUnsignedByte numberByte) {
		return divide(numberByte.b);
	}
	
	@Override
	public NumberUnsignedByte pow2() {
		b <<= 2;
		return this;
	}
	
	@Override
	public NumberUnsignedByte pow(NumberUnsignedByte pow) {
		return pow(pow.b);
	}
	
	@Override
	public NumberUnsignedByte shiftRight(NumberUnsignedByte shift) {
		return shiftRight(shift.b);
	}
	
	@Override
	public NumberUnsignedByte shiftRightLog(NumberUnsignedByte shift) {
		return shiftRightLog(shift.b);
	}
	
	@Override
	public NumberUnsignedByte shiftLeft(NumberUnsignedByte shift) {
		return shiftLeft(shift.b);
	}
	
	//methods with primitive
	public NumberUnsignedByte add(byte n) {
		b += n;
		return this;
	}
	
	public NumberUnsignedByte sub(byte n) {
		b -= n;
		return this;
	}
	
	public NumberUnsignedByte add(byte n, int[] overflow) {
		int i = (int) b + n;
		b = (byte) i;
		overflow[0] += i >> 8;
		return this;
	}
	
	public NumberUnsignedByte sub(byte n, int[] overflow) {
		int i = (int) b - n;
		b = (byte) i;
		overflow[0] += i >> 8;
		return this;
	}
	
	public NumberUnsignedByte multiply(byte n) {
		b *= n;
		return this;
	}
	
	public NumberUnsignedByte divide(byte n) {
		b /= n;
		return this;
	}
	
	public NumberUnsignedByte pow(byte pow) {
		b = MathUtils.pow(b, pow);
		return this;
	}
	
	public NumberUnsignedByte shiftRight(byte shift) {
		b >>>= shift;
		return this;
	}
	
	public NumberUnsignedByte shiftRightLog(byte shift) {
		b >>>= shift;
		return this;
	}
	
	public NumberUnsignedByte shiftLeft(byte shift) {
		b <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return Integer.toUnsignedString(b);
	}
}
