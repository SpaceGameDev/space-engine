package space.engine.number.unsigned.primitive;

import space.engine.number.base.INumberComplex;
import space.engine.number.unsigned.IUnsignedInteger;
import space.util.creatorOld.Creator;
import space.util.math.PrimitiveInteger;
import space.util.wrapper.IntWrapper.IntWrapper1;

public class NumberUnsignedInt extends IUnsignedInteger<NumberUnsignedInt> implements INumberComplex<NumberUnsignedInt> {
	
	public static final Creator<NumberUnsignedInt> CREATOR = new Creator<NumberUnsignedInt>() {
		@Override
		public NumberUnsignedInt create() {
			return new NumberUnsignedInt();
		}
		
		@Override
		public NumberUnsignedInt[] createArray(int size) {
			return new NumberUnsignedInt[size];
		}
	};
	public int i;
	
	public NumberUnsignedInt() {
	}
	
	public NumberUnsignedInt(int i) {
		this.i = i;
	}
	
	@Override
	public Creator<NumberUnsignedInt> creator() {
		return CREATOR;
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
	public NumberUnsignedInt add(NumberUnsignedInt numberByte, IntWrapper1 overflow) {
		return add(numberByte.i, overflow);
	}
	
	@Override
	public NumberUnsignedInt sub(NumberUnsignedInt numberByte, IntWrapper1 overflow) {
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
	public NumberUnsignedInt sqrt() {
		i = (int) Math.sqrt(i & PrimitiveInteger.int32MaskNumber);
		return this;
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
	
	public NumberUnsignedInt add(int n, IntWrapper1 overflow) {
		long i = this.i + n;
		this.i = (int) i;
		overflow.int1 += i >>> 32;
		return this;
	}
	
	public NumberUnsignedInt sub(int n, IntWrapper1 overflow) {
		long i = this.i - n;
		this.i = (int) i;
		overflow.int1 += i >>> 32;
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
		i = (int) Math.pow(i & PrimitiveInteger.int32MaskNumber, pow & PrimitiveInteger.int32MaskNumber);
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
