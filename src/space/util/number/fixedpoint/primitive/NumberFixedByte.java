package space.util.number.fixedpoint.primitive;

import space.util.math.MathUtils;
import space.util.math.UnsignedMath;
import space.util.number.base.NumberMulDiv;
import space.util.number.fixedpoint.IFixedPoint;

public class NumberFixedByte extends IFixedPoint<NumberFixedByte> implements NumberMulDiv<NumberFixedByte> {
	
	public byte b;
	
	public NumberFixedByte() {
	}
	
	public NumberFixedByte(byte b) {
		this.b = b;
	}
	
	@Override
	public NumberFixedByte set(NumberFixedByte n) {
		b = n.b;
		return this;
	}
	
	@Override
	public NumberFixedByte make() {
		return new NumberFixedByte();
	}
	
	@Override
	public NumberFixedByte copy() {
		return new NumberFixedByte(b);
	}
	
	//methods with Wrapper
	@Override
	public NumberFixedByte add(NumberFixedByte numberByte) {
		return add(numberByte.b);
	}
	
	@Override
	public NumberFixedByte sub(NumberFixedByte numberByte) {
		return sub(numberByte.b);
	}
	
	@Override
	public NumberFixedByte add(NumberFixedByte numberByte, int[] overflow) {
		return add(numberByte.b, overflow);
	}
	
	@Override
	public NumberFixedByte sub(NumberFixedByte numberByte, int[] overflow) {
		return sub(numberByte.b, overflow);
	}
	
	@Override
	public NumberFixedByte negate() {
		return this;
	}
	
	@Override
	public NumberFixedByte multiply(NumberFixedByte numberByte) {
		return multiply(numberByte.b);
	}
	
	@Override
	public NumberFixedByte divide(NumberFixedByte numberByte) {
		return divide(numberByte.b);
	}
	
	@Override
	public NumberFixedByte shiftRight(NumberFixedByte shift) {
		return shiftRight(shift.b);
	}
	
	@Override
	public NumberFixedByte shiftRightLog(NumberFixedByte shift) {
		return shiftRightLog(shift.b);
	}
	
	@Override
	public NumberFixedByte shiftLeft(NumberFixedByte shift) {
		return shiftLeft(shift.b);
	}
	
	//methods with primitive
	public NumberFixedByte add(byte n) {
		b += n;
		return this;
	}
	
	public NumberFixedByte sub(byte n) {
		b -= n;
		return this;
	}
	
	public NumberFixedByte add(byte n, int[] overflow) {
		int i = (int) b + n;
		b = (byte) i;
		overflow[0] += i >> 8;
		return this;
	}
	
	public NumberFixedByte sub(byte n, int[] overflow) {
		int i = (int) b - n;
		b = (byte) i;
		overflow[0] += i >> 8;
		return this;
	}
	
	public NumberFixedByte multiply(byte n) {
		b *= n;
		return this;
	}
	
	public NumberFixedByte divide(byte n) {
		b /= n;
		return this;
	}
	
	public NumberFixedByte shiftRight(byte shift) {
		b >>>= shift;
		return this;
	}
	
	public NumberFixedByte shiftRightLog(byte shift) {
		b >>>= shift;
		return this;
	}
	
	public NumberFixedByte shiftLeft(byte shift) {
		b <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return MathUtils.fpFormatterAcc.toStringFloat(UnsignedMath.toFloatUnsigned(b) / maxf);
	}
}
