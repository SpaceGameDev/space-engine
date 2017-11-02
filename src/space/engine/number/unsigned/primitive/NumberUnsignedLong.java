package space.engine.number.unsigned.primitive;

import space.engine.number.base.INumberComplex;
import space.engine.number.unsigned.IUnsignedInteger;
import space.util.creatorOld.Creator;
import space.util.math.BigMath;
import space.util.math.PrimitiveInteger;
import space.util.wrapper.IntWrapper.IntWrapper1;

public class NumberUnsignedLong extends IUnsignedInteger<NumberUnsignedLong> implements INumberComplex<NumberUnsignedLong> {
	
	public static final Creator<NumberUnsignedLong> CREATOR = new Creator<NumberUnsignedLong>() {
		@Override
		public NumberUnsignedLong create() {
			return new NumberUnsignedLong();
		}
		
		@Override
		public NumberUnsignedLong[] createArray(int size) {
			return new NumberUnsignedLong[size];
		}
	};
	public long l;
	
	public NumberUnsignedLong() {
	}
	
	public NumberUnsignedLong(long l) {
		this.l = l;
	}
	
	@Override
	public Creator<NumberUnsignedLong> creator() {
		return CREATOR;
	}
	
	@Override
	public NumberUnsignedLong set(NumberUnsignedLong n) {
		l = n.l;
		return this;
	}
	
	@Override
	public NumberUnsignedLong make() {
		return new NumberUnsignedLong();
	}
	
	@Override
	public NumberUnsignedLong copy() {
		return new NumberUnsignedLong(l);
	}
	
	//methods with Wrapper
	@Override
	public NumberUnsignedLong add(NumberUnsignedLong numberByte) {
		return add(numberByte.l);
	}
	
	@Override
	public NumberUnsignedLong sub(NumberUnsignedLong numberByte) {
		return sub(numberByte.l);
	}
	
	@Override
	public NumberUnsignedLong add(NumberUnsignedLong numberByte, IntWrapper1 overflow) {
		return add(numberByte.l, overflow);
	}
	
	@Override
	public NumberUnsignedLong sub(NumberUnsignedLong numberByte, IntWrapper1 overflow) {
		return sub(numberByte.l, overflow);
	}
	
	@Override
	public NumberUnsignedLong negate() {
		return this;
	}
	
	@Override
	public NumberUnsignedLong multiply(NumberUnsignedLong numberByte) {
		return multiply(numberByte.l);
	}
	
	@Override
	public NumberUnsignedLong divide(NumberUnsignedLong numberByte) {
		return divide(numberByte.l);
	}
	
	@Override
	public NumberUnsignedLong pow2() {
		l <<= 2;
		return this;
	}
	
	@Override
	public NumberUnsignedLong pow(NumberUnsignedLong pow) {
		return pow(pow.l);
	}
	
	@Override
	public NumberUnsignedLong sqrt() {
		l = (long) Math.sqrt(l & PrimitiveInteger.int64MaskNumber);
		return this;
	}
	
	@Override
	public NumberUnsignedLong shiftRight(NumberUnsignedLong shift) {
		return shiftRight(shift.l);
	}
	
	@Override
	public NumberUnsignedLong shiftRightLog(NumberUnsignedLong shift) {
		return shiftRightLog(shift.l);
	}
	
	@Override
	public NumberUnsignedLong shiftLeft(NumberUnsignedLong shift) {
		return shiftLeft(shift.l);
	}
	
	//methods with primitive
	public NumberUnsignedLong add(long n) {
		l += n;
		return this;
	}
	
	public NumberUnsignedLong sub(long n) {
		l -= n;
		return this;
	}
	
	public NumberUnsignedLong add(long n, IntWrapper1 overflow) {
		long i = BigMath.getLowerL(l) + BigMath.getLowerL(n);
		long ret = BigMath.getLowerL(i);
		
		i = (i >>> 32) + BigMath.getUpper(l) + BigMath.getUpper(n);
		ret += BigMath.getLowerL(i);
		
		overflow.int1 += BigMath.getUpper(ret);
		return this;
	}
	
	public NumberUnsignedLong sub(long n, IntWrapper1 overflow) {
		long i = BigMath.getLowerL(l) - BigMath.getLowerL(n);
		long ret = BigMath.getLowerL(i);
		
		i = (i >>> 32) + BigMath.getUpper(l) - BigMath.getUpper(n);
		ret += BigMath.getLowerL(i);
		
		overflow.int1 += BigMath.getUpper(ret);
		return this;
	}
	
	public NumberUnsignedLong multiply(long n) {
		l *= n;
		return this;
	}
	
	public NumberUnsignedLong divide(long n) {
		l /= n;
		return this;
	}
	
	public NumberUnsignedLong pow(long pow) {
		l = (long) Math.pow(l & PrimitiveInteger.int64MaskNumber, pow & PrimitiveInteger.int64MaskNumber);
		return this;
	}
	
	public NumberUnsignedLong shiftRight(long shift) {
		l >>>= shift;
		return this;
	}
	
	public NumberUnsignedLong shiftRightLog(long shift) {
		l >>>= shift;
		return this;
	}
	
	public NumberUnsignedLong shiftLeft(long shift) {
		l <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return Long.toUnsignedString(l);
	}
}
