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

public class NumberFloat extends IFloatingPointPrimitive<NumberFloat> implements INumberComplex<NumberFloat> {
	
	public static final Creator<NumberFloat> CREATOR = new Creator<NumberFloat>() {
		@Override
		public NumberFloat create() {
			return new NumberFloat();
		}
		
		@Override
		public NumberFloat[] createArray(int size) {
			return new NumberFloat[size];
		}
	};
	public float f;
	
	public NumberFloat() {
	}
	
	public NumberFloat(float f) {
		this.f = f;
	}
	
	private static void setZero(FloatingPointGeneral ret, boolean sign) {
		ret.sign = sign;
		ret.fraction = NumberZero.FixedPointZero;
		ret.exponent = NumberZero.IntegerZero;
	}
	
	@Override
	public Creator<NumberFloat> creator() {
		return CREATOR;
	}
	
	@Override
	public FloatingPointGeneral get(FloatingPointGeneral ret) {
		int i = Float.floatToRawIntBits(f);
		
		boolean rawSign = (i & PrimitiveFloatingPoint.float32MaskSign) == 0;
		int rawFraction = i & PrimitiveFloatingPoint.float32MaskFraction;
		int rawExponent = (i & PrimitiveFloatingPoint.float32MaskExponent) >>> PrimitiveFloatingPoint.float32ShiftExponent;
		byte exp;
		
		switch (rawExponent) {
			case 0x00:
				if (rawFraction == 0) {
					//NumberZero
					ret.type = rawSign ? FloatingPointSpecialNumber.ZERO : FloatingPointSpecialNumber.NEGATIVEZERO;
					setZero(ret, rawSign);
					return ret;
				}
				
				//subnormal
				ret.type = FloatingPointSpecialNumber.NORMAL;
				ret.sign = rawSign;
				ret.fraction.number = BigPrimitiveMath.intArrayFromIntFixed(rawFraction << (32 - PrimitiveFloatingPoint.float32SizeFractionFull));
				ret.fraction.bits = 32;
				exp = (byte) (rawExponent - 126);
				ret.exponent.number = BigPrimitiveMath.intArrayFromByteSigned(exp);
				ret.exponent.sign = BigPrimitiveMath.getSign(exp);
				return ret;
			case 0xFF:
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
				
				ret.fraction.number = BigPrimitiveMath.intArrayFromIntFixed((PrimitiveFloatingPoint.float32FractionFullBit ^ rawFraction) << (32 - PrimitiveFloatingPoint.float32SizeFractionFull));
				ret.fraction.bits = 32;
				exp = (byte) (rawExponent - 126);
				ret.exponent.number = BigPrimitiveMath.intArrayFromByteSigned(exp);
				ret.exponent.sign = BigPrimitiveMath.getSign(exp);
				return ret;
		}
	}
	
	@Override
	public NumberFloat set(FloatingPointGeneral get) {
		switch (get.type) {
			case ZERO:
				f = 0f;
				return this;
			case NEGATIVEZERO:
				f = -0f;
				return this;
			case NAN:
				f = Float.NaN;
				return this;
			case POSITIVEINFINITY:
				f = Float.POSITIVE_INFINITY;
				return this;
			case NEGATIVEINFINITY:
				f = Float.NEGATIVE_INFINITY;
				return this;
			case NORMAL:
				int fraction = BigPrimitiveMath.intFromIntArrayFixed(get.fraction.number);
				int exponent = BigPrimitiveMath.intFromIntArraySigned(get.exponent.number, get.exponent.sign) + 126;
				
				if ((fraction & 0x80000000) == 0) {
					//not normalized
					throw new IllegalNumberOperationException();
				}
				
				if (exponent >= 0xFF) {
					//too big
					f = get.sign ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
					return this;
				}
				
				if (exponent <= 0) {
					//subnormal
					int shift = -(exponent - 1);
					fraction >>>= shift;
					exponent = 0;
				}
				
				//normal
				f = Float.intBitsToFloat((get.sign ? 0 : PrimitiveFloatingPoint.float32MaskSign) | ((exponent << PrimitiveFloatingPoint.float32ShiftExponent) & PrimitiveFloatingPoint.float32MaskExponent) | ((fraction >>> (32 - PrimitiveFloatingPoint.float32SizeFractionFull)) & PrimitiveFloatingPoint.float32MaskFraction));
				return this;
		}
		throw new IllegalArgumentException();
	}
	
	@Override
	public NumberFloat set(NumberFloat n) {
		f = n.f;
		return this;
	}
	
	@Override
	public NumberFloat make() {
		return new NumberFloat();
	}
	
	@Override
	public NumberFloat copy() {
		return new NumberFloat(f);
	}
	
	//methods with Wrapper
	@Override
	public NumberFloat add(NumberFloat numberByte) {
		return add(numberByte.f);
	}
	
	@Override
	public NumberFloat sub(NumberFloat numberByte) {
		return sub(numberByte.f);
	}
	
	@Override
	public NumberFloat multiply(NumberFloat numberByte) {
		return multiply(numberByte.f);
	}
	
	@Override
	public NumberFloat divide(NumberFloat numberByte) {
		return divide(numberByte.f);
	}
	
	@Override
	public NumberFloat pow2() {
		f = f * f;
		return this;
	}
	
	@Override
	public NumberFloat pow(NumberFloat pow) {
		f = (float) Math.pow(f, pow.f);
		return this;
	}
	
	@Override
	public NumberFloat sqrt() {
		f = (float) Math.sqrt(f);
		return this;
	}
	
	@Override
	public NumberFloat shiftRight(NumberFloat shift) {
		return shiftRight(shift.f);
	}
	
	@Override
	public NumberFloat shiftRightLog(NumberFloat shift) {
		return shiftRight(shift.f);
	}
	
	@Override
	public NumberFloat shiftLeft(NumberFloat shift) {
		return shiftLeft(shift.f);
	}
	
	@Override
	public NumberFloat negate() {
		f = -f;
		return this;
	}
	
	//methods with primitive
	public NumberFloat add(float n) {
		f += n;
		return this;
	}
	
	public NumberFloat sub(float n) {
		f -= n;
		return this;
	}
	
	public NumberFloat multiply(float n) {
		f *= n;
		return this;
	}
	
	public NumberFloat divide(float n) {
		f /= n;
		return this;
	}
	
	public NumberFloat pow(float pow) {
		f = (float) Math.pow(f, pow);
		return this;
	}
	
	public NumberFloat shiftRight(float shift) {
		f = f / (float) Math.pow(2, shift);
		return this;
	}
	
	public NumberFloat shiftLeft(float shift) {
		f = f * (float) Math.pow(2, shift);
		return this;
	}
	
	//toString
	@Override
	public String toString() {
		return MathUtils.fpFormatterInf.toStringFloat(f);
	}
}
