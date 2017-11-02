package space.engine.number.integer.primitive;

import spaceOld.util.conversion.IConverterSearchable;
import spaceOld.util.math.BigPrimitiveMath;
import spaceOld.util.math.number.INumberConverter;
import spaceOld.util.math.number.integer.IntegerGeneral;

public class IntegerConverterPrimitiveGeneral {
	
	//to General
	@INumberConverter
	public static class ConverterByteToIntegerGeneral implements IConverterSearchable<NumberByte, IntegerGeneral> {
		
		@Override
		public Class<NumberByte> classFrom() {
			return NumberByte.class;
		}
		
		@Override
		public Class<IntegerGeneral> classTo() {
			return IntegerGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public IntegerGeneral convertNew(NumberByte number) throws UnsupportedOperationException {
			return convertInstance(number, new IntegerGeneral());
		}
		
		@Override
		public IntegerGeneral convertInstance(NumberByte number, IntegerGeneral ret) {
			ret.sign = BigPrimitiveMath.getSign(number.b);
			ret.number = BigPrimitiveMath.intArrayFromByteSigned(number.b);
			return ret;
		}
		
		@Override
		public IntegerGeneral convertType(NumberByte number, Class<? extends IntegerGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterShortToIntegerGeneral implements IConverterSearchable<NumberShort, IntegerGeneral> {
		
		@Override
		public Class<NumberShort> classFrom() {
			return NumberShort.class;
		}
		
		@Override
		public Class<IntegerGeneral> classTo() {
			return IntegerGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public IntegerGeneral convertNew(NumberShort number) throws UnsupportedOperationException {
			return convertInstance(number, new IntegerGeneral());
		}
		
		@Override
		public IntegerGeneral convertInstance(NumberShort number, IntegerGeneral ret) {
			ret.sign = BigPrimitiveMath.getSign(number.s);
			ret.number = BigPrimitiveMath.intArrayFromShortSigned(number.s);
			return ret;
		}
		
		@Override
		public IntegerGeneral convertType(NumberShort number, Class<? extends IntegerGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterIntToIntegerGeneral implements IConverterSearchable<NumberInt, IntegerGeneral> {
		
		@Override
		public Class<NumberInt> classFrom() {
			return NumberInt.class;
		}
		
		@Override
		public Class<IntegerGeneral> classTo() {
			return IntegerGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public IntegerGeneral convertNew(NumberInt number) throws UnsupportedOperationException {
			return convertInstance(number, new IntegerGeneral());
		}
		
		@Override
		public IntegerGeneral convertInstance(NumberInt number, IntegerGeneral ret) {
			ret.sign = BigPrimitiveMath.getSign(number.i);
			ret.number = BigPrimitiveMath.intArrayFromIntSigned(number.i);
			return ret;
		}
		
		@Override
		public IntegerGeneral convertType(NumberInt number, Class<? extends IntegerGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterLongToIntegerGeneral implements IConverterSearchable<NumberLong, IntegerGeneral> {
		
		@Override
		public Class<NumberLong> classFrom() {
			return NumberLong.class;
		}
		
		@Override
		public Class<IntegerGeneral> classTo() {
			return IntegerGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public IntegerGeneral convertNew(NumberLong number) throws UnsupportedOperationException {
			return convertInstance(number, new IntegerGeneral());
		}
		
		@Override
		public IntegerGeneral convertInstance(NumberLong number, IntegerGeneral ret) {
			ret.sign = BigPrimitiveMath.getSign(number.l);
			ret.number = BigPrimitiveMath.intArrayFromLongSigned(number.l);
			return ret;
		}
		
		@Override
		public IntegerGeneral convertType(NumberLong number, Class<? extends IntegerGeneral> type) {
			return convertNew(number);
		}
	}
	
	//from General
	@INumberConverter
	public static class ConverterIntegerGeneralToByte implements IConverterSearchable<IntegerGeneral, NumberByte> {
		
		@Override
		public Class<IntegerGeneral> classFrom() {
			return IntegerGeneral.class;
		}
		
		@Override
		public Class<NumberByte> classTo() {
			return NumberByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberByte convertNew(IntegerGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberByte());
		}
		
		@Override
		public NumberByte convertInstance(IntegerGeneral number, NumberByte ret) {
			ret.b = BigPrimitiveMath.byteFromIntArraySigned(number.number, number.sign);
			return ret;
		}
		
		@Override
		public NumberByte convertType(IntegerGeneral number, Class<? extends NumberByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterIntegerGeneralToShort implements IConverterSearchable<IntegerGeneral, NumberShort> {
		
		@Override
		public Class<IntegerGeneral> classFrom() {
			return IntegerGeneral.class;
		}
		
		@Override
		public Class<NumberShort> classTo() {
			return NumberShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberShort convertNew(IntegerGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberShort());
		}
		
		@Override
		public NumberShort convertInstance(IntegerGeneral number, NumberShort ret) {
			ret.s = BigPrimitiveMath.shortFromIntArraySigned(number.number, number.sign);
			return ret;
		}
		
		@Override
		public NumberShort convertType(IntegerGeneral number, Class<? extends NumberShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterIntegerGeneralToInt implements IConverterSearchable<IntegerGeneral, NumberInt> {
		
		@Override
		public Class<IntegerGeneral> classFrom() {
			return IntegerGeneral.class;
		}
		
		@Override
		public Class<NumberInt> classTo() {
			return NumberInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberInt convertNew(IntegerGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberInt());
		}
		
		@Override
		public NumberInt convertInstance(IntegerGeneral number, NumberInt ret) {
			ret.i = BigPrimitiveMath.intFromIntArraySigned(number.number, number.sign);
			return ret;
		}
		
		@Override
		public NumberInt convertType(IntegerGeneral number, Class<? extends NumberInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterIntegerGeneralToLong implements IConverterSearchable<IntegerGeneral, NumberLong> {
		
		@Override
		public Class<IntegerGeneral> classFrom() {
			return IntegerGeneral.class;
		}
		
		@Override
		public Class<NumberLong> classTo() {
			return NumberLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberLong convertNew(IntegerGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberLong());
		}
		
		@Override
		public NumberLong convertInstance(IntegerGeneral number, NumberLong ret) {
			ret.l = BigPrimitiveMath.longFromIntArraySigned(number.number, number.sign);
			return ret;
		}
		
		@Override
		public NumberLong convertType(IntegerGeneral number, Class<? extends NumberLong> type) {
			return convertNew(number);
		}
	}
}
