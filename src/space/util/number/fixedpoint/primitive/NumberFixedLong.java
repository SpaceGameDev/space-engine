package space.util.number.fixedpoint.primitive;

import space.util.math.BigMath;
import space.util.math.UnsignedMath;
import space.util.number.base.NumberMulDiv;
import space.util.number.fixedpoint.IFixedPoint;
import spaceOld.util.math.MathUtils;

public class NumberFixedLong extends IFixedPoint<NumberFixedLong> implements NumberMulDiv<NumberFixedLong> {
	
	public long l;
	
	public NumberFixedLong() {
	}
	
	public NumberFixedLong(long l) {
		this.l = l;
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
	public NumberFixedLong add(NumberFixedLong numberByte, int[] overflow) {
		return add(numberByte.l, overflow);
	}
	
	@Override
	public NumberFixedLong sub(NumberFixedLong numberByte, int[] overflow) {
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
	
	public NumberFixedLong add(long n, int[] overflow) {
		long i = BigMath.getLowerL(l) + BigMath.getLowerL(n);
		long ret = BigMath.getLowerL(i);
		
		i = (i >>> 32) + BigMath.getUpper(l) + BigMath.getUpper(n);
		ret += BigMath.getLowerL(i);
		
		overflow[0] += BigMath.getUpper(ret);
		return this;
	}
	
	public NumberFixedLong sub(long n, int[] overflow) {
		long i = BigMath.getLowerL(l) - BigMath.getLowerL(n);
		long ret = BigMath.getLowerL(i);
		
		i = (i >>> 32) + BigMath.getUpper(l) - BigMath.getUpper(n);
		ret += BigMath.getLowerL(i);
		
		overflow[0] += BigMath.getUpper(ret);
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
