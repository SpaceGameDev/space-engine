package space.engine.number.integer.primitive;

import space.engine.number.base.INumberComplex;
import space.engine.number.integer.IInteger;
import space.util.creatorOld.Creator;
import space.util.math.PrimitiveInteger;
import space.util.wrapper.IntWrapper.IntWrapper1;

public class NumberByte extends IInteger<NumberByte> implements INumberComplex<NumberByte> {
	
	public static final Creator<NumberByte> CREATOR = new Creator<NumberByte>() {
		@Override
		public NumberByte create() {
			return new NumberByte();
		}
		
		@Override
		public NumberByte[] createArray(int size) {
			return new NumberByte[size];
		}
	};
	public byte b;
	
	public NumberByte() {
	}
	
	public NumberByte(byte b) {
		this.b = b;
	}
	
	@Override
	public Creator<NumberByte> creator() {
		return CREATOR;
	}
	
	@Override
	public NumberByte set(NumberByte n) {
		b = n.b;
		return this;
	}
	
	@Override
	public NumberByte make() {
		return new NumberByte();
	}
	
	@Override
	public NumberByte copy() {
		return new NumberByte(b);
	}
	
	//methods with Wrapper
	@Override
	public NumberByte add(NumberByte numberByte) {
		return add(numberByte.b);
	}
	
	@Override
	public NumberByte sub(NumberByte numberByte) {
		return sub(numberByte.b);
	}
	
	@Override
	public NumberByte add(NumberByte numberByte, IntWrapper1 overflow) {
		return add(numberByte.b, overflow);
	}
	
	@Override
	public NumberByte sub(NumberByte numberByte, IntWrapper1 overflow) {
		return sub(numberByte.b, overflow);
	}
	
	@Override
	public NumberByte negate() {
		b = (byte) (b ^ PrimitiveInteger.ByteMaskSign);
		return this;
	}
	
	@Override
	public NumberByte multiply(NumberByte numberByte) {
		return multiply(numberByte.b);
	}
	
	@Override
	public NumberByte divide(NumberByte numberByte) {
		return divide(numberByte.b);
	}
	
	@Override
	public NumberByte pow2() {
		b <<= 2;
		return this;
	}
	
	@Override
	public NumberByte pow(NumberByte pow) {
		return pow(pow.b);
	}
	
	@Override
	public NumberByte sqrt() {
		b = (byte) Math.sqrt(b);
		return this;
	}
	
	@Override
	public NumberByte shiftRight(NumberByte shift) {
		return shiftRight(shift.b);
	}
	
	@Override
	public NumberByte shiftRightLog(NumberByte shift) {
		return shiftRightLog(shift.b);
	}
	
	@Override
	public NumberByte shiftLeft(NumberByte shift) {
		return shiftLeft(shift.b);
	}
	
	//methods with primitive
	public NumberByte add(byte n) {
		b += n;
		return this;
	}
	
	public NumberByte sub(byte n) {
		b -= n;
		return this;
	}
	
	public NumberByte add(byte n, IntWrapper1 overflow) {
		int i = (int) b + n;
		b = (byte) i;
		overflow.int1 += i >>> 8;
		return this;
	}
	
	public NumberByte sub(byte n, IntWrapper1 overflow) {
		int i = (int) b - n;
		b = (byte) i;
		overflow.int1 += i >>> 8;
		return this;
	}
	
	public NumberByte multiply(byte n) {
		b *= n;
		return this;
	}
	
	public NumberByte divide(byte n) {
		b /= n;
		return this;
	}
	
	public NumberByte pow(byte pow) {
		b = (byte) Math.pow(b, pow);
		return this;
	}
	
	public NumberByte shiftRight(byte shift) {
		b >>= shift;
		return this;
	}
	
	public NumberByte shiftRightLog(byte shift) {
		b >>>= shift;
		return this;
	}
	
	public NumberByte shiftLeft(byte shift) {
		b <<= shift;
		return this;
	}
	
	@Override
	public String toString() {
		return Byte.toString(b);
	}
}
