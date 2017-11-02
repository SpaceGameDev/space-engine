package space.engine.number.base;

import space.util.annotation.Self;
import space.util.math.BigMath;
import space.util.math.BigPrimitiveMath;
import space.util.string.builder.IStringBuilder;

public class BigNumberSigned extends BigNumber {
	
	public static boolean toStringOutputPositiveAndNegative = false;
	public static boolean negateReleaseMemory = true;
	
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
		if (!isZero()) {
			sign = !sign;
			magnitude = BigMath.twosConversion(magnitude);
		} else {
			//why not? release some memory...
			if (negateReleaseMemory)
				magnitude = new int[0];
		}
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
	
	//toString
	@Override
	public void toStringLayered(IStringBuilder<?> sb) {
		IStringBuilder<?> b = sb.startLine();
		if (magnitude.length == 0) {
			b.append('0');
		} else {
			
			if (toStringOutputPositiveAndNegative) {
				BigMath.toString(b, magnitude);
				b.append(" | ");
				BigMath.toString(b, BigMath.twosConversion(magnitude));
				b.append(": ");
			}
			
			if (sign) {
				BigMath.toString(b, magnitude);
			} else {
				b.append('-');
				BigMath.toStringNegative(b, magnitude);
			}
		}
		b.endLine();
	}
}
