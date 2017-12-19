package space.util.number.converter;

import space.util.conversion.smart.ConverterSmart;
import space.util.number.base.NumberBase;
import space.util.number.integer.IntegerGeneral;
import space.util.number.integer.NumberBigInteger;
import space.util.number.integer.nativeType.NumberByte;
import space.util.number.integer.nativeType.NumberInt;
import space.util.number.integer.nativeType.NumberLong;
import space.util.number.integer.nativeType.NumberShort;

import static space.util.math.BigPrimitiveMath.*;
import static space.util.number.converter.INumberConverterRegistry.add;
import static space.util.number.converter.NumberConverter.*;

public class IntegerConverter implements INumberConverterRegistry {
	
	@Override
	public void accept(ConverterSmart<NumberBase> conv) {
		//general to
		add(conv, NumberByte.class, IntegerGeneral.class, IntegerGeneral::new, (from, to) -> to.set(from.b >= 0, intArrayFromByteSigned(from.b)), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, NumberShort.class, IntegerGeneral.class, IntegerGeneral::new, (from, to) -> to.set(from.s >= 0, intArrayFromShortSigned(from.s)), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, NumberInt.class, IntegerGeneral.class, IntegerGeneral::new, (from, to) -> to.set(from.i >= 0, intArrayFromIntSigned(from.i)), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, NumberLong.class, IntegerGeneral.class, IntegerGeneral::new, (from, to) -> to.set(from.l >= 0, intArrayFromLongSigned(from.l)), WEIGHT_GENERAL_CONVERSION, false);
		//general from
		add(conv, IntegerGeneral.class, NumberByte.class, NumberByte::new, (from, to) -> to.b = byteFromIntArraySigned(from.number, from.sign), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, IntegerGeneral.class, NumberShort.class, NumberShort::new, (from, to) -> to.s = shortFromIntArraySigned(from.number, from.sign), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, IntegerGeneral.class, NumberInt.class, NumberInt::new, (from, to) -> to.i = intFromIntArraySigned(from.number, from.sign), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, IntegerGeneral.class, NumberLong.class, NumberLong::new, (from, to) -> to.l = longFromIntArraySigned(from.number, from.sign), WEIGHT_GENERAL_CONVERSION, false);
		
		//BigInteger
		add(conv, IntegerGeneral.class, NumberBigInteger.class, NumberBigInteger::new, (from, to) -> to.set(from.sign, from.number), WEIGHT_GENERAL_CONVERSION, false);
		add(conv, NumberBigInteger.class, IntegerGeneral.class, IntegerGeneral::new, (from, to) -> to.set(from.n.sign, from.n.magnitude), WEIGHT_GENERAL_CONVERSION, false);
		
		//byte up
		add(conv, NumberByte.class, NumberShort.class, NumberShort::new, (from, to) -> to.s = from.b, WEIGHT_NATIVE_CONVERSION, false);
		add(conv, NumberByte.class, NumberInt.class, NumberInt::new, (from, to) -> to.i = from.b, WEIGHT_NATIVE_CONVERSION, false);
		add(conv, NumberByte.class, NumberLong.class, NumberLong::new, (from, to) -> to.l = from.b, WEIGHT_NATIVE_CONVERSION, false);
		//byte down
		add(conv, NumberShort.class, NumberByte.class, NumberByte::new, (from, to) -> to.b = (byte) from.s, WEIGHT_NATIVE_CONVERSION, true);
		add(conv, NumberInt.class, NumberByte.class, NumberByte::new, (from, to) -> to.b = (byte) from.i, WEIGHT_NATIVE_CONVERSION, true);
		add(conv, NumberLong.class, NumberByte.class, NumberByte::new, (from, to) -> to.b = (byte) from.l, WEIGHT_NATIVE_CONVERSION, true);
		
		//short up
		add(conv, NumberShort.class, NumberInt.class, NumberInt::new, (from, to) -> to.i = from.s, WEIGHT_NATIVE_CONVERSION, false);
		add(conv, NumberShort.class, NumberLong.class, NumberLong::new, (from, to) -> to.l = from.s, WEIGHT_NATIVE_CONVERSION, false);
		//short down
		add(conv, NumberInt.class, NumberShort.class, NumberShort::new, (from, to) -> to.s = (short) from.i, WEIGHT_NATIVE_CONVERSION, true);
		add(conv, NumberLong.class, NumberShort.class, NumberShort::new, (from, to) -> to.s = (short) from.l, WEIGHT_NATIVE_CONVERSION, true);
		
		//int up
		add(conv, NumberInt.class, NumberLong.class, NumberLong::new, (from, to) -> to.l = from.i, WEIGHT_NATIVE_CONVERSION, false);
		//int down
		add(conv, NumberLong.class, NumberInt.class, NumberInt::new, (from, to) -> to.i = (int) from.l, WEIGHT_NATIVE_CONVERSION, true);
	}
}
