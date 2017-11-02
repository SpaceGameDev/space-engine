package space.engine.number.fixedpoint.primitive;

import space.engine.number.base.INumberComplex;
import space.engine.number.fixedpoint.IFixedPoint;
import space.util.creatorOld.Creator;
import space.util.math.PrimitiveInteger;
import space.util.math.UnsignedMath;
import space.util.wrapper.IntWrapper.IntWrapper1;
import spaceOld.util.math.MathUtils;

public class NumberFixedShort extends IFixedPoint<NumberFixedShort> implements INumberComplex<NumberFixedShort> {
	
	public static final Creator<NumberFixedShort> CREATOR = new Creator<NumberFixedShort>() {
		@Override
		public NumberFixedShort create() {
			return new NumberFixedShort();
		}
		
		@Override
		public NumberFixedShort[] createArray(int size) {
			return new NumberFixedShort[size];
		}
	};
	public static short max = (short) 0xFFFF;
	public static float maxf = UnsignedMath.toFloatUnsigned(max);
	public short s;
	
	public NumberFixedShort() {
	}
	
	public NumberFixedShort(short s) {
		this.s = s;
	}
	
	@Override
	public Creator<NumberFixedShort> creator() {
		return CREATOR;
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
	public NumberFixedShort add(NumberFixedShort numberByte, IntWrapper1 overflow) {
		return add(numberByte.s, overflow);
	}
	
	@Override
	public NumberFixedShort sub(NumberFixedShort numberByte, IntWrapper1 overflow) {
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
	public NumberFixedShort pow2() {
		s <<= 2;
		return this;
	}
	
	@Override
	public NumberFixedShort pow(NumberFixedShort pow) {
		return pow(pow.s);
	}
	
	@Override
	public NumberFixedShort sqrt() {
		s = (short) Math.sqrt(s & PrimitiveInteger.int16MaskNumber);
		return this;
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
	
	public NumberFixedShort add(short n, IntWrapper1 overflow) {
		int i = (int) s + n;
		s = (short) i;
		overflow.int1 += i >>> 16;
		return this;
	}
	
	public NumberFixedShort sub(short n, IntWrapper1 overflow) {
		int i = (int) s - n;
		s = (short) i;
		overflow.int1 += i >>> 16;
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
	
	public NumberFixedShort pow(int pow) {
		s = (short) Math.pow(s & PrimitiveInteger.int16MaskNumber, pow & PrimitiveInteger.int16MaskNumber);
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
