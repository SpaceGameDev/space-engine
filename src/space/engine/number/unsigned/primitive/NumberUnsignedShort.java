package space.engine.number.unsigned.primitive;

import space.engine.number.base.INumberComplex;
import space.engine.number.unsigned.IUnsignedInteger;
import space.util.creatorOld.Creator;
import space.util.math.PrimitiveInteger;
import space.util.wrapper.IntWrapper.IntWrapper1;

public class NumberUnsignedShort extends IUnsignedInteger<NumberUnsignedShort> implements INumberComplex<NumberUnsignedShort> {
	
	public static final Creator<NumberUnsignedShort> CREATOR = new Creator<NumberUnsignedShort>() {
		@Override
		public NumberUnsignedShort create() {
			return new NumberUnsignedShort();
		}
		
		@Override
		public NumberUnsignedShort[] createArray(int size) {
			return new NumberUnsignedShort[size];
		}
	};
	public short s;
	
	public NumberUnsignedShort() {
	}
	
	public NumberUnsignedShort(short s) {
		this.s = s;
	}
	
	@Override
	public Creator<NumberUnsignedShort> creator() {
		return CREATOR;
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
	public NumberUnsignedShort add(NumberUnsignedShort numberByte, IntWrapper1 overflow) {
		return add(numberByte.s, overflow);
	}
	
	@Override
	public NumberUnsignedShort sub(NumberUnsignedShort numberByte, IntWrapper1 overflow) {
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
	public NumberUnsignedShort sqrt() {
		s = (short) Math.sqrt(s & PrimitiveInteger.int16MaskNumber);
		return this;
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
	
	public NumberUnsignedShort add(short n, IntWrapper1 overflow) {
		int i = (int) s + n;
		s = (short) i;
		overflow.int1 += i >>> 16;
		return this;
	}
	
	public NumberUnsignedShort sub(short n, IntWrapper1 overflow) {
		int i = (int) s - n;
		s = (short) i;
		overflow.int1 += i >>> 16;
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
	
	public NumberUnsignedShort pow(int pow) {
		s = (short) Math.pow(s & PrimitiveInteger.int16MaskNumber, pow & PrimitiveInteger.int16MaskNumber);
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
