package space.engine.number.integer;

import space.engine.number.base.BigNumberSigned;
import space.engine.number.base.INumberSimple;
import space.util.creatorOld.Creator;
import spaceOld.util.string.builder.IStringBuilder;
import spaceOld.util.string.builder.LayeredToString;

public class NumberBigInteger extends IInteger<NumberBigInteger> implements INumberSimple<NumberBigInteger>, LayeredToString {
	
	public static final Creator<NumberBigInteger> CREATOR = new Creator<NumberBigInteger>() {
		@Override
		public NumberBigInteger create() {
			return new NumberBigInteger();
		}
		
		@Override
		public NumberBigInteger[] createArray(int size) {
			return new NumberBigInteger[size];
		}
	};
	public BigNumberSigned n;
	
	//constructor
	public NumberBigInteger() {
		this(new int[0], true);
	}
	
	public NumberBigInteger(int[] magnitude, boolean sign) {
		this(new BigNumberSigned(magnitude, sign));
	}
	
	public NumberBigInteger(BigNumberSigned number) {
		n = number;
	}
	
	public NumberBigInteger(int magnitude) {
		this(new BigNumberSigned(magnitude));
	}
	
	public NumberBigInteger(long magnitude) {
		this(new BigNumberSigned(magnitude));
	}
	
	@Override
	public Creator<NumberBigInteger> creator() {
		return CREATOR;
	}
	
	public long getCapacityBits() {
		return n.getCapacityNumberBit();
	}
	
	@Override
	public NumberBigInteger set(NumberBigInteger n) {
		this.n = n.n.copy();
		return this;
	}
	
	@Override
	public NumberBigInteger make() {
		return new NumberBigInteger();
	}
	
	@Override
	public NumberBigInteger copy() {
		return new NumberBigInteger(n.copy());
	}
	
	//math simple
	@Override
	public NumberBigInteger add(NumberBigInteger b) {
		n.addSigned(b.n);
		return this;
	}
	
	@Override
	public NumberBigInteger sub(NumberBigInteger b) {
		n.subSigned(b.n);
		return this;
	}
	
	@Override
	public NumberBigInteger negate() {
		n.negate();
		return this;
	}
	
	public NumberBigInteger trim() {
		n.trim();
		return this;
	}
	
	//toString
	@Override
	public void toStringLayered(IStringBuilder sb) {
		n.toStringLayered(sb);
	}
	
	@Override
	public String toString() {
		return n.toString0();
	}
}
