package space.util.number.converter;

import space.util.conversion.smart.ConverterSmart;
import space.util.number.base.NumberBase;
import space.util.number.unsigned.NumberUnsignedBigInteger;
import space.util.number.unsigned.UnsignedGeneral;
import space.util.number.unsigned.primitive.NumberUnsignedByte;
import space.util.number.unsigned.primitive.NumberUnsignedInt;
import space.util.number.unsigned.primitive.NumberUnsignedLong;
import space.util.number.unsigned.primitive.NumberUnsignedShort;

import static space.util.math.BigPrimitiveMath.*;
import static space.util.number.converter.INumberConverterRegistry.add;
import static space.util.number.converter.NumberConverter.*;

public class UnsignedConverter implements INumberConverterRegistry {
	
	@Override
	public void accept(ConverterSmart<NumberBase> conv) {
		//general to
		add(conv, NumberUnsignedByte.class, UnsignedGeneral.class, UnsignedGeneral::new, (from, to) -> to.set(intArrayFromByteUnsigned(from.b)), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, NumberUnsignedShort.class, UnsignedGeneral.class, UnsignedGeneral::new, (from, to) -> to.set(intArrayFromShortUnsigned(from.s)), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, NumberUnsignedInt.class, UnsignedGeneral.class, UnsignedGeneral::new, (from, to) -> to.set(intArrayFromIntUnsigned(from.i)), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, NumberUnsignedLong.class, UnsignedGeneral.class, UnsignedGeneral::new, (from, to) -> to.set(intArrayFromLongUnsigned(from.l)), WEIGHT_GENERAL_CONVERSION, false);
		//general from
		add(conv, UnsignedGeneral.class, NumberUnsignedByte.class, NumberUnsignedByte::new, (from, to) -> to.b = byteFromIntArrayUnsigned(from.number), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, UnsignedGeneral.class, NumberUnsignedShort.class, NumberUnsignedShort::new, (from, to) -> to.s = shortFromIntArrayUnsigned(from.number), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, UnsignedGeneral.class, NumberUnsignedInt.class, NumberUnsignedInt::new, (from, to) -> to.i = intFromIntArrayUnsigned(from.number), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, UnsignedGeneral.class, NumberUnsignedLong.class, NumberUnsignedLong::new, (from, to) -> to.l = longFromIntArrayUnsigned(from.number), WEIGHT_GENERAL_CONVERSION, false);
		
		//BigInteger
		add(conv, UnsignedGeneral.class, NumberUnsignedBigInteger.class, NumberUnsignedBigInteger::new, (from, to) -> to.set(from.number), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, NumberUnsignedBigInteger.class, UnsignedGeneral.class, UnsignedGeneral::new, (from, to) -> to.set(from.n.magnitude), WEIGHT_GENERAL_CONVERSION, false);
		
		//byte up
		add(conv, NumberUnsignedByte.class, NumberUnsignedShort.class, NumberUnsignedShort::new, (from, to) -> to.s = from.b, WEIGHT_NATIVE_CONVERSION, false);
		add(conv, NumberUnsignedByte.class, NumberUnsignedInt.class, NumberUnsignedInt::new, (from, to) -> to.i = from.b, WEIGHT_NATIVE_CONVERSION, false);
		add(conv, NumberUnsignedByte.class, NumberUnsignedLong.class, NumberUnsignedLong::new, (from, to) -> to.l = from.b, WEIGHT_NATIVE_CONVERSION, false);
		//byte down
		add(conv, NumberUnsignedShort.class, NumberUnsignedByte.class, NumberUnsignedByte::new, (from, to) -> to.b = (byte) from.s, WEIGHT_NATIVE_CONVERSION, true);
		add(conv, NumberUnsignedInt.class, NumberUnsignedByte.class, NumberUnsignedByte::new, (from, to) -> to.b = (byte) from.i, WEIGHT_NATIVE_CONVERSION, true);
		add(conv, NumberUnsignedLong.class, NumberUnsignedByte.class, NumberUnsignedByte::new, (from, to) -> to.b = (byte) from.l, WEIGHT_NATIVE_CONVERSION, true);
		
		//short up
		add(conv, NumberUnsignedShort.class, NumberUnsignedInt.class, NumberUnsignedInt::new, (from, to) -> to.i = from.s, WEIGHT_NATIVE_CONVERSION, false);
		add(conv, NumberUnsignedShort.class, NumberUnsignedLong.class, NumberUnsignedLong::new, (from, to) -> to.l = from.s, WEIGHT_NATIVE_CONVERSION, false);
		//short down
		add(conv, NumberUnsignedInt.class, NumberUnsignedShort.class, NumberUnsignedShort::new, (from, to) -> to.s = (short) from.i, WEIGHT_NATIVE_CONVERSION, true);
		add(conv, NumberUnsignedLong.class, NumberUnsignedShort.class, NumberUnsignedShort::new, (from, to) -> to.s = (short) from.l, WEIGHT_NATIVE_CONVERSION, true);
		
		//int up
		add(conv, NumberUnsignedInt.class, NumberUnsignedLong.class, NumberUnsignedLong::new, (from, to) -> to.l = from.i, WEIGHT_NATIVE_CONVERSION, false);
		//int down
		add(conv, NumberUnsignedLong.class, NumberUnsignedInt.class, NumberUnsignedInt::new, (from, to) -> to.i = (int) from.l, WEIGHT_NATIVE_CONVERSION, true);
	}
}
