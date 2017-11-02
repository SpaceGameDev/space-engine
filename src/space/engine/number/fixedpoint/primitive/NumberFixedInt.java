package space.engine.number.fixedpoint.primitive;

import space.engine.number.base.INumberComplex;
import space.engine.number.fixedpoint.IFixedPoint;
import space.util.creatorOld.Creator;
import space.util.math.PrimitiveInteger;
import space.util.math.UnsignedMath;
import space.util.wrapper.IntWrapper.IntWrapper1;
import spaceOld.util.math.MathUtils;

public class NumberFixedInt extends IFixedPoint<NumberFixedInt> implements INumberComplex<NumberFixedInt> {
	
	public static final Creator<NumberFixedInt> CREATOR = new Creator<NumberFixedInt>() {
		@Override
		public NumberFixedInt create() {
			return new NumberFixedInt();
		}
		
		@Override
		public NumberFixedInt[] createArray(int size) {
			return new NumberFixedInt[size];
		}
	};
	public static int max = 0xFFFFFFFF;
	public static double maxd = UnsignedMath.toDoubleUnsigned(max);
	public int i;
	
	public NumberFixedInt() {
	}
	
	public NumberFixedInt(int i) {
		this.i = i;
	}
	
	@Override
	public Creator<NumberFixedInt> creator() {
		return CREATOR;
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
	public NumberFixedInt add(NumberFixedInt numberByte, IntWrapper1 overflow) {
		return add(numberByte.i, overflow);
	}
	
	@Override
	public NumberFixedInt sub(NumberFixedInt numberByte, IntWrapper1 overflow) {
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
	public NumberFixedInt pow2() {
		i <<= 2;
		return this;
	}
	
	@Override
	public NumberFixedInt pow(NumberFixedInt pow) {
		return pow(pow.i);
	}
	
	@Override
	public NumberFixedInt sqrt() {
		i = (int) Math.sqrt(i & PrimitiveInteger.int32MaskNumber);
		return this;
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
	
	public NumberFixedInt add(int n, IntWrapper1 overflow) {
		long i = this.i + n;
		this.i = (int) i;
		overflow.int1 += i >>> 32;
		return this;
	}
	
	public NumberFixedInt sub(int n, IntWrapper1 overflow) {
		long i = this.i - n;
		this.i = (int) i;
		overflow.int1 += i >>> 32;
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
	
	public NumberFixedInt pow(int pow) {
		i = (int) Math.pow(i & PrimitiveInteger.int32MaskNumber, pow & PrimitiveInteger.int32MaskNumber);
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
