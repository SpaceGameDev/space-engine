package space.engine.number.floatingpoint;

import space.engine.number.NumberConverter;
import space.engine.number.fixedpoint.FixedPointGeneral;
import space.engine.number.fixedpoint.IFixedPoint;
import space.engine.number.integer.IInteger;
import space.engine.number.integer.IntegerGeneral;
import space.util.creatorOld.Creator;
import space.util.math.BigMath;
import space.util.math.BigPrimitiveMath;
import space.util.math.MathUtils;
import space.util.math.PrimitiveInteger;
import space.util.string.builder.IStringBuilder;
import spaceOld.util.string.builder.LayeredToString;

public final class FloatingPointGeneral extends IFloatingPoint<FloatingPointGeneral> implements LayeredToString {
	
	public static final Creator<FloatingPointGeneral> CREATOR = new Creator<FloatingPointGeneral>() {
		@Override
		public FloatingPointGeneral create() {
			return new FloatingPointGeneral();
		}
		
		@Override
		public FloatingPointGeneral[] createArray(int size) {
			return new FloatingPointGeneral[size];
		}
	};
	public FloatingPointSpecialNumber type;
	public boolean sign;
	public FixedPointGeneral fraction;
	public IntegerGeneral exponent;
	
	public FloatingPointGeneral() {
		this(null, true, new FixedPointGeneral(), new IntegerGeneral());
	}
	
	public FloatingPointGeneral(FloatingPointSpecialNumber type, boolean sign, IFixedPoint<?> fraction, IInteger<?> exponent) {
		this(type, sign, NumberConverter.conv.convertInstance(fraction, new FixedPointGeneral()), NumberConverter.conv.convertInstance(exponent, new IntegerGeneral()));
	}
	
	public FloatingPointGeneral(FloatingPointSpecialNumber type, boolean sign, FixedPointGeneral fraction, IntegerGeneral exponent) {
		this.type = type;
		this.sign = sign;
		this.fraction = fraction;
		this.exponent = exponent;
	}
	
	@Override
	public Creator<FloatingPointGeneral> creator() {
		return CREATOR;
	}
	
	@Override
	public FloatingPointGeneral set(FloatingPointGeneral n) {
		type = n.type;
		sign = n.sign;
		fraction = n.fraction.copy();
		exponent = n.exponent.copy();
		return this;
	}
	
	@Override
	public FloatingPointGeneral make() {
		return new FloatingPointGeneral();
	}
	
	@Override
	public FloatingPointGeneral copy() {
		return new FloatingPointGeneral(type, sign, fraction.copy(), exponent.copy());
	}
	
	@Override
	public void toStringLayered(IStringBuilder<?> b) {
		if (type == FloatingPointSpecialNumber.NORMAL) {
			if (!sign)
				b.append('-');
			
			b.append(fraction);
//			toStringLayeredFractionSimple(b, 10, 4);
			b.append('E');
			b.append(exponent);
//			toStringLayeredExponentSimple(b, 10, Integer.MAX_VALUE);
		} else {
			b.append(type.name);
		}
	}
	
	public void toStringLayeredFractionSimple(IStringBuilder<?> b, int radix, int cnt) {
		int v = BigPrimitiveMath.intFromIntArrayFixed(fraction.number) & PrimitiveInteger.int32MaskNumber;
		for (int i = 0; i < cnt && v > radix; i++) {
			b.append(MathUtils.digits[BigMath.getUpper(v * 10L)]);
			v *= 10;
		}
	}
	
	public void toStringLayeredExponentSimple(IStringBuilder<?> b, int radix, int cnt) {
		if (!exponent.sign)
			b.append('-');
		long v = BigPrimitiveMath.longFromIntArrayUnsigned(exponent.number) & PrimitiveInteger.int64MaskNumber;
		for (int i = 0; i < cnt && v > radix; i++) {
			b.append(MathUtils.digits[(int) (v % 10)]);
			v /= 10;
		}
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
