package space.engine.number.integer.primitive;

import space.engine.number.base.INumberComplex;
import space.engine.number.integer.IInteger;
import space.util.creatorOld.Creator;
import space.util.math.BigMath;
import space.util.math.PrimitiveInteger;
import space.util.wrapper.IntWrapper.IntWrapper1;

public class NumberLong extends IInteger<NumberLong> implements INumberComplex<NumberLong> {
	
	public static final Creator<NumberLong> CREATOR = new Creator<NumberLong>() {
		@Override
		public NumberLong create() {
			return new NumberLong();
		}
		
		@Override
		public NumberLong[] createArray(int size) {
			return new NumberLong[size];
		}
	};
	public long l;
	
	public NumberLong() {
	}
	
	public NumberLong(long l) {
		this.l = l;
	}
	
	@Override
	public Creator<NumberLong> creator() {
		return CREATOR;
	}
	
	@Override
	public NumberLong set(NumberLong n) {
		l = n.l;
		return this;
	}
	
	@Override
	public NumberLong make() {
		return new NumberLong();
	}
	
	@Override
	public NumberLong copy() {
		return new NumberLong(l);
	}
	
	//methods with Wrapper
	@Override
	public NumberLong add(NumberLong numberByte) {
		return add(numberByte.l);
	}
	
	@Override
	public NumberLong sub(NumberLong numberByte) {
		return sub(numberByte.l);
	}
	
	@Override
	public NumberLong add(NumberLong numberByte, IntWrapper1 overflow) {
		return add(numberByte.l, overflow);
	}
	
	@Override
	public NumberLong sub(NumberLong numberByte, IntWrapper1 overflow) {
		return sub(numberByte.l, overflow);
	}
	
	@Override
	public NumberLong negate() {
		l = l ^ PrimitiveInteger.int64MaskSign;
		return this;
	}
	
	@Override
	public NumberLong multiply(NumberLong numberByte) {
		return multiply(numberByte.l);
	}
	
	@Override
	public NumberLong divide(NumberLong numberByte) {
		return divide(numberByte.l);
	}
	
	@Override
	public NumberLong pow2() {
		l <<= 2;
		return this;
	}
	
	@Override
	public NumberLong pow(NumberLong pow) {
		return pow(pow.l);
	}
	
	@Override
	public NumberLong sqrt() {
		l = (long) Math.sqrt(l);
		return this;
	}
	
	@Override
	public NumberLong shiftRight(NumberLong shift) {
		return shiftRight(shift.l);
	}
	
	@Override
	public NumberLong shiftRightLog(NumberLong shift) {
		return shiftRightLog(shift.l);
	}
	
	@Override
	public NumberLong shiftLeft(NumberLong shift) {
		return shiftLeft(shift.l);
	}
	
	//methods with primitive
	public NumberLong add(long n) {
		l += n;
		return this;
	}
	
	public NumberLong sub(long n) {
		l -= n;
		return this;
	}
	
	public NumberLong add(long n, IntWrapper1 overflow) {
		long i = BigMath.getLowerL(l) + BigMath.getLowerL(n);
		long ret = BigMath.getLowerL(i);
		
		i = (i >>> 32) + BigMath.getUpper(l) + BigMath.getUpper(n);
		ret += BigMath.getLowerL(i);
		
		overflow.int1 += BigMath.getUpper(ret);
		return this;
	}
	
	public NumberLong sub(long n, IntWrapper1 overflow) {
		long i = BigMath.getLowerL(l) - BigMath.getLowerL(n);
		long ret = BigMath.getLowerL(i);
		
		i = (i >>> 32) + BigMath.getUpper(l) - BigMath.getUpper(n);
		ret += BigMath.getLowerL(i);
		
		overflow.int1 += BigMath.getUpper(ret);
		return this;
	}
	
	public NumberLong multiply(long n) {
		l *= n;
		return this;
	}
	
	public NumberLong divide(long n) {
		l /= n;
		return this;
	}
	
	public NumberLong pow(long pow) {
		l = (long) Math.pow(l, pow);
		return this;
	}
	
	public NumberLong shiftRight(long shift) {
		l >>= shift;
		return this;
	}
	
	public NumberLong shiftRightLog(long shift) {
		l >>>= shift;
		return this;
	}
	
	public NumberLong shiftLeft(long shift) {
		l <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return Long.toString(l);
	}
}
