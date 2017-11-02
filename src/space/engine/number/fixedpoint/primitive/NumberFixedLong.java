package space.engine.number.fixedpoint.primitive;

import space.engine.number.base.INumberComplex;
import space.engine.number.fixedpoint.IFixedPoint;
import space.util.creatorOld.Creator;
import space.util.math.BigMath;
import space.util.math.PrimitiveInteger;
import space.util.math.UnsignedMath;
import space.util.wrapper.IntWrapper.IntWrapper1;
import spaceOld.util.math.MathUtils;

public class NumberFixedLong extends IFixedPoint<NumberFixedLong> implements INumberComplex<NumberFixedLong> {
	
	public static final Creator<NumberFixedLong> CREATOR = new Creator<NumberFixedLong>() {
		@Override
		public NumberFixedLong create() {
			return new NumberFixedLong();
		}
		
		@Override
		public NumberFixedLong[] createArray(int size) {
			return new NumberFixedLong[size];
		}
	};
	public static long max = 0xFFFFFFFFFFFFFFFFL;
	public static double maxd = UnsignedMath.toDoubleUnsigned(max);
	public long l;
	
	public NumberFixedLong() {
	}
	
	public NumberFixedLong(long l) {
		this.l = l;
	}
	
	@Override
	public Creator<NumberFixedLong> creator() {
		return CREATOR;
	}
	
	@Override
	public NumberFixedLong set(NumberFixedLong n) {
		l = n.l;
		return this;
	}
	
	@Override
	public NumberFixedLong make() {
		return new NumberFixedLong();
	}
	
	@Override
	public NumberFixedLong copy() {
		return new NumberFixedLong(l);
	}
	
	//methods with Wrapper
	@Override
	public NumberFixedLong add(NumberFixedLong numberByte) {
		return add(numberByte.l);
	}
	
	@Override
	public NumberFixedLong sub(NumberFixedLong numberByte) {
		return sub(numberByte.l);
	}
	
	@Override
	public NumberFixedLong add(NumberFixedLong numberByte, IntWrapper1 overflow) {
		return add(numberByte.l, overflow);
	}
	
	@Override
	public NumberFixedLong sub(NumberFixedLong numberByte, IntWrapper1 overflow) {
		return sub(numberByte.l, overflow);
	}
	
	@Override
	public NumberFixedLong negate() {
		return this;
	}
	
	@Override
	public NumberFixedLong multiply(NumberFixedLong numberByte) {
		return multiply(numberByte.l);
	}
	
	@Override
	public NumberFixedLong divide(NumberFixedLong numberByte) {
		return divide(numberByte.l);
	}
	
	@Override
	public NumberFixedLong pow2() {
		l <<= 2;
		return this;
	}
	
	@Override
	public NumberFixedLong pow(NumberFixedLong pow) {
		return pow(pow.l);
	}
	
	@Override
	public NumberFixedLong sqrt() {
		l = (long) Math.sqrt(l & PrimitiveInteger.int64MaskNumber);
		return this;
	}
	
	@Override
	public NumberFixedLong shiftRight(NumberFixedLong shift) {
		return shiftRight(shift.l);
	}
	
	@Override
	public NumberFixedLong shiftRightLog(NumberFixedLong shift) {
		return shiftRightLog(shift.l);
	}
	
	@Override
	public NumberFixedLong shiftLeft(NumberFixedLong shift) {
		return shiftLeft(shift.l);
	}
	
	//methods with primitive
	public NumberFixedLong add(long n) {
		l += n;
		return this;
	}
	
	public NumberFixedLong sub(long n) {
		l -= n;
		return this;
	}
	
	public NumberFixedLong add(long n, IntWrapper1 overflow) {
		long i = BigMath.getLowerL(l) + BigMath.getLowerL(n);
		long ret = BigMath.getLowerL(i);
		
		i = (i >>> 32) + BigMath.getUpper(l) + BigMath.getUpper(n);
		ret += BigMath.getLowerL(i);
		
		overflow.int1 += BigMath.getUpper(ret);
		return this;
	}
	
	public NumberFixedLong sub(long n, IntWrapper1 overflow) {
		long i = BigMath.getLowerL(l) - BigMath.getLowerL(n);
		long ret = BigMath.getLowerL(i);
		
		i = (i >>> 32) + BigMath.getUpper(l) - BigMath.getUpper(n);
		ret += BigMath.getLowerL(i);
		
		overflow.int1 += BigMath.getUpper(ret);
		return this;
	}
	
	public NumberFixedLong multiply(long n) {
		l *= n;
		return this;
	}
	
	public NumberFixedLong divide(long n) {
		l /= n;
		return this;
	}
	
	public NumberFixedLong pow(long pow) {
		l = (long) Math.pow(l & PrimitiveInteger.int64MaskNumber, pow & PrimitiveInteger.int64MaskNumber);
		return this;
	}
	
	public NumberFixedLong shiftRight(long shift) {
		l >>>= shift;
		return this;
	}
	
	public NumberFixedLong shiftRightLog(long shift) {
		l >>>= shift;
		return this;
	}
	
	public NumberFixedLong shiftLeft(long shift) {
		l <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return MathUtils.fpFormatterAcc.toStringDouble(UnsignedMath.toDoubleUnsigned(l) / maxd);
	}
}
