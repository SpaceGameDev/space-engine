package space.engine.number.fixedpoint.primitive;

import space.engine.number.base.INumberComplex;
import space.engine.number.fixedpoint.IFixedPoint;
import space.util.creatorOld.Creator;
import space.util.math.MathUtils;
import space.util.math.PrimitiveInteger;
import space.util.math.UnsignedMath;
import space.util.wrapper.IntWrapper.IntWrapper1;

public class NumberFixedByte extends IFixedPoint<NumberFixedByte> implements INumberComplex<NumberFixedByte> {
	
	public static final Creator<NumberFixedByte> CREATOR = new Creator<NumberFixedByte>() {
		@Override
		public NumberFixedByte create() {
			return new NumberFixedByte();
		}
		
		@Override
		public NumberFixedByte[] createArray(int size) {
			return new NumberFixedByte[size];
		}
	};
	public static byte max = (byte) 0xFF;
	public static float maxf = UnsignedMath.toFloatUnsigned(max);
	public byte b;
	
	public NumberFixedByte() {
	}
	
	public NumberFixedByte(byte b) {
		this.b = b;
	}
	
	@Override
	public Creator<NumberFixedByte> creator() {
		return CREATOR;
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
	public NumberFixedByte add(NumberFixedByte numberByte, IntWrapper1 overflow) {
		return add(numberByte.b, overflow);
	}
	
	@Override
	public NumberFixedByte sub(NumberFixedByte numberByte, IntWrapper1 overflow) {
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
	public NumberFixedByte pow2() {
		b <<= 2;
		return this;
	}
	
	@Override
	public NumberFixedByte pow(NumberFixedByte pow) {
		return pow(pow.b);
	}
	
	@Override
	public NumberFixedByte sqrt() {
		b = (byte) Math.sqrt(b & PrimitiveInteger.ByteMaskNumber);
		return this;
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
	
	public NumberFixedByte add(byte n, IntWrapper1 overflow) {
		int i = (int) b + n;
		b = (byte) i;
		overflow.int1 += i >>> 8;
		return this;
	}
	
	public NumberFixedByte sub(byte n, IntWrapper1 overflow) {
		int i = (int) b - n;
		b = (byte) i;
		overflow.int1 += i >>> 8;
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
	
	public NumberFixedByte pow(byte pow) {
		b = (byte) Math.pow(b & PrimitiveInteger.ByteMaskNumber, pow & PrimitiveInteger.ByteMaskNumber);
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
