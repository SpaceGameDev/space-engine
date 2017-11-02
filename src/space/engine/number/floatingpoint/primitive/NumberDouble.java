package space.engine.number.floatingpoint.primitive;

import space.engine.number.NumberZero;
import space.engine.number.base.INumberComplex;
import space.engine.number.exception.IllegalNumberOperationException;
import space.engine.number.floatingpoint.FloatingPointGeneral;
import space.engine.number.floatingpoint.FloatingPointSpecialNumber;
import space.util.creatorOld.Creator;
import space.util.math.BigPrimitiveMath;
import space.util.math.PrimitiveFloatingPoint;
import spaceOld.util.math.MathUtils;

public class NumberDouble extends IFloatingPointPrimitive<NumberDouble> implements INumberComplex<NumberDouble> {
	
	public static final Creator<NumberDouble> CREATOR = new Creator<NumberDouble>() {
		@Override
		public NumberDouble create() {
			return new NumberDouble();
		}
		
		@Override
		public NumberDouble[] createArray(int size) {
			return new NumberDouble[size];
		}
	};
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
	public Creator<NumberDouble> creator() {
		return CREATOR;
	}
	
	@Override
	public FloatingPointGeneral get(FloatingPointGeneral ret) {
		long i = Double.doubleToRawLongBits(d);
		
		boolean rawSign = (i & PrimitiveFloatingPoint.float64MaskSign) == 0;
		long rawFraction = i & PrimitiveFloatingPoint.float64MaskFraction;
		int rawExponent = (int) ((i & PrimitiveFloatingPoint.float64MaskExponent) >>> PrimitiveFloatingPoint.float64ShiftExponent);
		short exp;
		
		switch (rawExponent) {
			case 0x00:
				if (rawFraction == 0) {
					//NumberZero
					ret.type = rawSign ? FloatingPointSpecialNumber.ZERO : FloatingPointSpecialNumber.NEGATIVEZERO;
					setZero(ret, rawSign);
					return ret;
				}
				
				ret.type = FloatingPointSpecialNumber.NORMAL;
				ret.sign = rawSign;
				
				ret.fraction.number = BigPrimitiveMath.intArrayFromLongFixed(rawFraction << (64 - PrimitiveFloatingPoint.float64SizeFractionFull));
				ret.fraction.bits = 64;
				exp = (short) (rawExponent - 1022);
				ret.exponent.number = BigPrimitiveMath.intArrayFromShortSigned(exp);
				ret.exponent.sign = BigPrimitiveMath.getSign(exp);
				return ret;
			case 0x7FF:
				if (rawFraction == 0) {
					//Infinite
					ret.type = rawSign ? FloatingPointSpecialNumber.POSITIVEINFINITY : FloatingPointSpecialNumber.NEGATIVEINFINITY;
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
				ret.fraction.number = BigPrimitiveMath.intArrayFromLongFixed((PrimitiveFloatingPoint.float64FractionFullBit ^ rawFraction) << (64 - PrimitiveFloatingPoint.float64SizeFractionFull));
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
			case NEGATIVEZERO:
				d = -0f;
				return this;
			case NAN:
				d = Double.NaN;
				return this;
			case POSITIVEINFINITY:
				d = Double.POSITIVE_INFINITY;
				return this;
			case NEGATIVEINFINITY:
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
				d = Double.longBitsToDouble((get.sign ? 0 : PrimitiveFloatingPoint.float64MaskSign) | ((exponent << PrimitiveFloatingPoint.float64ShiftExponent) & PrimitiveFloatingPoint.float64MaskExponent) | ((fraction >>> (64 - PrimitiveFloatingPoint.float64SizeFractionFull)) & PrimitiveFloatingPoint.float64MaskFraction));
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
