package space.util.number.big;

import space.util.annotation.Self;
import space.util.math.BigMath;
import space.util.math.BigPrimitiveMath;

public class BigNumberSigned extends BigNumber {
	
	public static final boolean TO_STRING_OUTPUT_POSITIVE_AND_NEGATIVE = false;
	
	public boolean sign;
	
	public BigNumberSigned() {
		this(new int[0], true);
	}
	
	public BigNumberSigned(int[] magnitude, boolean sign) {
		super(magnitude);
		this.sign = sign;
	}
	
	//pharse
	public BigNumberSigned(int magnitude) {
		this(BigPrimitiveMath.intArrayFromIntSigned(magnitude), magnitude >= 0);
	}
	
	public BigNumberSigned(long magnitude) {
		this(BigPrimitiveMath.intArrayFromLongSigned(magnitude), magnitude >= 0);
	}
	
	//set
	public void set(int[] i, boolean sign) {
		setSign(sign);
		setNumber(i);
	}
	
	public void setSign(boolean sign) {
		this.sign = sign;
	}
	
	//simple math
	@Self
	public BigNumberSigned addSigned(BigNumberSigned n) {
		BigMath.addSigned(magnitude, n.magnitude, sign, n.sign, getFillNumber(), this);
		return this;
	}
	
	@Self
	public BigNumberSigned subSigned(BigNumberSigned n) {
		BigMath.subSigned(magnitude, n.magnitude, sign, n.sign, getFillNumber(), this);
		return this;
	}
	
	@Self
	public BigNumberSigned negate() {
		sign = !sign;
		magnitude = BigMath.twosConversion(magnitude);
		return this;
	}
	
	//util
	@Override
	public BigNumberSigned copy() {
		return new BigNumberSigned(magnitude.clone(), sign);
	}
	
	@Override
	public BigNumberSigned trim() {
		magnitude = BigMath.trim(magnitude, getFillNumber());
		return this;
	}
	
	@Override
	public boolean isZero() {
		if (!sign)
			return false;
		if (magnitude.length == 0)
			return true;
		
		for (int i : magnitude)
			if (i != 0)
				return false;
		return true;
	}
	
	@Override
	public int getFillNumber() {
		return sign ? 0 : 0xFFFFFFFF;
	}
	
	@Override
	public String toString() {
		if (magnitude.length == 0)
			return "0";
		if (sign)
			return BigMath.toString(magnitude);
		else
			return '-' + BigMath.toStringNegative(magnitude);
	}
}
