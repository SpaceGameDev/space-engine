package space.util.number.floatingpoint.primitive;

import space.util.math.BigPrimitiveMath;
import space.util.number.NumberZero;
import space.util.number.base.NumberComplex;
import space.util.number.exception.IllegalNumberOperationException;
import space.util.number.floatingpoint.FloatingPointGeneral;
import space.util.number.floatingpoint.FloatingPointSpecialNumber;
import space.util.primitive.FloatingPointConst;
import spaceOld.util.math.MathUtils;

public class NumberDouble extends IFloatingPointPrimitive<NumberDouble> implements NumberComplex<NumberDouble> {
	
	public double d;
	
	public NumberDouble() {
	}
	
	public NumberDouble(double d) {
		this.d = d;
	}
	
	private static void setZero(FloatingPointGeneral ret, boolean sign) {
		ret.sign = sign;
		ret.fraction = NumberZero.FixedPointZero;
		ret.exponent = NumberZero.IntegerZero;
	}
	
	@Override
	public FloatingPointGeneral get(FloatingPointGeneral ret) {
		long i = Double.doubleToRawLongBits(d);
		
		boolean rawSign = (i & FloatingPointConst.float64SignMask) == 0;
		long rawFraction = i & FloatingPointConst.float64FractionMask;
		int rawExponent = (int) ((i & FloatingPointConst.float64ExponentMask) >>> FloatingPointConst.float64ExponentShift);
		short exp;
		
		switch (rawExponent) {
			case 0x00:
				if (rawFraction == 0) {
					//NumberZero
					ret.type = rawSign ? FloatingPointSpecialNumber.ZERO : FloatingPointSpecialNumber.NEGATIVE_ZERO;
					setZero(ret, rawSign);
					return ret;
				}
				
				ret.type = FloatingPointSpecialNumber.NORMAL;
				ret.sign = rawSign;
				
				ret.fraction.number = BigPrimitiveMath.intArrayFromLongFixed(rawFraction << (64 - FloatingPointConst.float64FractionFullSize));
				ret.fraction.bits = 64;
				exp = (short) (rawExponent - 1022);
				ret.exponent.number = BigPrimitiveMath.intArrayFromShortSigned(exp);
				ret.exponent.sign = BigPrimitiveMath.getSign(exp);
				return ret;
			case 0x7FF:
				if (rawFraction == 0) {
					//Infinite
					ret.type = rawSign ? FloatingPointSpecialNumber.POSITIVE_INFINITY : FloatingPointSpecialNumber.NEGATIVE_INFINITY;
					setZero(ret, rawSign);
					return ret;
				}
				
				//NAN
				ret.type = FloatingPointSpecialNumber.NAN;
				setZero(ret, true);
				return ret;
			default:
				//normal
				ret.type = FloatingPointSpecialNumber.NORMAL;
				ret.sign = rawSign;
				ret.fraction.number = BigPrimitiveMath.intArrayFromLongFixed((FloatingPointConst.float64FractionFullBit ^ rawFraction) << (64 - FloatingPointConst.float64FractionFullSize));
				ret.fraction.bits = 64;
				exp = (short) (rawExponent - 1022);
				ret.exponent.number = BigPrimitiveMath.intArrayFromShortSigned(exp);
				ret.exponent.sign = BigPrimitiveMath.getSign(exp);
				return ret;
		}
	}
	
	@Override
	public NumberDouble set(FloatingPointGeneral get) {
		switch (get.type) {
			case ZERO:
				d = 0f;
				return this;
			case NEGATIVE_ZERO:
				d = -0f;
				return this;
			case NAN:
				d = Double.NaN;
				return this;
			case POSITIVE_INFINITY:
				d = Double.POSITIVE_INFINITY;
				return this;
			case NEGATIVE_INFINITY:
				d = Double.NEGATIVE_INFINITY;
				return this;
			case NORMAL:
				long fraction = BigPrimitiveMath.longFromIntArrayFixed(get.fraction.number);
				//FIXME: casting this to 11 bits...
				long exponent = BigPrimitiveMath.longFromIntArraySigned(get.exponent.number, get.exponent.sign) + 1022;
				
				if ((fraction & 0x8000000000000000L) == 0) {
					//not normalized
					throw new IllegalNumberOperationException();
				}
				
				if (exponent >= 0x7FF) {
					//too big
					d = get.sign ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
					return this;
				}
				
				if (exponent <= 0) {
					//subnormal
					long shift = -(exponent - 1);
					fraction >>>= shift;
					exponent = 0;
				}
				
				//normal
				d = Double.longBitsToDouble((get.sign ? 0 : FloatingPointConst.float64SignMask) | ((exponent << FloatingPointConst.float64ExponentShift) & FloatingPointConst.float64ExponentMask) | ((fraction >>> (64 - FloatingPointConst.float64FractionFullSize)) & FloatingPointConst.float64FractionMask));
				return this;
		}
		throw new IllegalArgumentException();
	}
	
	@Override
	public NumberDouble set(NumberDouble n) {
		d = n.d;
		return this;
	}
	
	@Override
	public NumberDouble make() {
		return new NumberDouble();
	}
	
	@Override
	public NumberDouble copy() {
		return new NumberDouble(d);
	}
	
	//methods with Wrapper
	@Override
	public NumberDouble add(NumberDouble numberByte) {
		return add(numberByte.d);
	}
	
	@Override
	public NumberDouble sub(NumberDouble numberByte) {
		return sub(numberByte.d);
	}
	
	@Override
	public NumberDouble multiply(NumberDouble numberByte) {
		return multiply(numberByte.d);
	}
	
	@Override
	public NumberDouble divide(NumberDouble numberByte) {
		return divide(numberByte.d);
	}
	
	@Override
	public NumberDouble pow2() {
		d = d * d;
		return this;
	}
	
	@Override
	public NumberDouble pow(NumberDouble pow) {
		d = Math.pow(d, pow.d);
		return this;
	}
	
	@Override
	public NumberDouble sqrt() {
		d = Math.sqrt(d);
		return this;
	}
	
	@Override
	public NumberDouble shiftRight(NumberDouble shift) {
		return shiftRight(shift.d);
	}
	
	@Override
	public NumberDouble shiftRightLog(NumberDouble shift) {
		return shiftRight(shift.d);
	}
	
	@Override
	public NumberDouble shiftLeft(NumberDouble shift) {
		return shiftLeft(shift.d);
	}
	
	@Override
	public NumberDouble negate() {
		d = -d;
		return this;
	}
	
	//methods with primitive
	public NumberDouble add(double n) {
		d += n;
		return this;
	}
	
	public NumberDouble sub(double n) {
		d -= n;
		return this;
	}
	
	public NumberDouble multiply(double n) {
		d *= n;
		return this;
	}
	
	public NumberDouble divide(double n) {
		d /= n;
		return this;
	}
	
	public NumberDouble pow(double pow) {
		d = Math.pow(d, pow);
		return this;
	}
	
	public NumberDouble shiftRight(double shift) {
		d = d / Math.pow(2, shift);
		return this;
	}
	
	public NumberDouble shiftLeft(double shift) {
		d = d * Math.pow(2, shift);
		return this;
	}
	
	//toString
	@Override
	public String toString() {
		return MathUtils.fpFormatterInf.toStringDouble(d);
	}
}
