package space.engine.number.unsigned;

import space.engine.number.base.BigNumber;
import space.engine.number.base.INumberSimple;
import space.util.creatorOld.Creator;
import space.util.math.BigPrimitiveMath;
import space.util.string.builder.IStringBuilder;
import spaceOld.util.string.builder.LayeredToString;

public class NumberBigUnsignedInteger extends IUnsignedInteger<NumberBigUnsignedInteger> implements INumberSimple<NumberBigUnsignedInteger>, LayeredToString {
	
	public static final Creator<NumberBigUnsignedInteger> CREATOR = new Creator<NumberBigUnsignedInteger>() {
		@Override
		public NumberBigUnsignedInteger create() {
			return new NumberBigUnsignedInteger();
		}
		
		@Override
		public NumberBigUnsignedInteger[] createArray(int size) {
			return new NumberBigUnsignedInteger[size];
		}
	};
	public BigNumber n;
	
	//constructor
	public NumberBigUnsignedInteger() {
		this(new int[0]);
	}
	
	public NumberBigUnsignedInteger(int magnitude) {
		this(BigPrimitiveMath.intArrayFromIntUnsigned(magnitude));
	}
	
	public NumberBigUnsignedInteger(long magnitude) {
		this(BigPrimitiveMath.intArrayFromLongUnsigned(magnitude));
	}
	
	public NumberBigUnsignedInteger(int[] magnitude) {
		this(new BigNumber(magnitude));
	}
	
	public NumberBigUnsignedInteger(BigNumber number) {
		n = number;
	}
	
	@Override
	public Creator<NumberBigUnsignedInteger> creator() {
		return CREATOR;
	}
	
	public long getCapacityBits() {
		return n.getCapacityNumberBit();
	}
	
	@Override
	public NumberBigUnsignedInteger set(NumberBigUnsignedInteger n) {
		this.n = n.n.copy();
		return this;
	}
	
	@Override
	public NumberBigUnsignedInteger make() {
		return new NumberBigUnsignedInteger();
	}
	
	@Override
	public NumberBigUnsignedInteger copy() {
		return new NumberBigUnsignedInteger(n.copy());
	}
	
	//math simple
	@Override
	public NumberBigUnsignedInteger add(NumberBigUnsignedInteger b) {
		n.addUnsigned(b.n);
		return this;
	}
	
	@Override
	public NumberBigUnsignedInteger sub(NumberBigUnsignedInteger b) {
		n.subUnsigned(b.n);
		return this;
	}
	
	@Override
	public NumberBigUnsignedInteger negate() {
		return this;
	}
	
	//toString
	@Override
	public void toStringLayered(IStringBuilder<?> sb) {
		n.toStringLayered(sb);
	}
	
	@Override
	public String toString() {
		return n.toString0();
	}
}
