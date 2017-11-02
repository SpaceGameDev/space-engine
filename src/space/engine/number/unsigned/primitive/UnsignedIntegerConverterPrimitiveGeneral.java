package space.engine.number.unsigned.primitive;

import spaceOld.util.conversion.IConverterSearchable;
import spaceOld.util.math.BigPrimitiveMath;
import spaceOld.util.math.number.INumberConverter;
import spaceOld.util.math.number.unsigned.UnsignedIntegerGeneral;

public class UnsignedIntegerConverterPrimitiveGeneral {
	
	//to General
	@INumberConverter
	public static class ConverterUnsignedByteToUnsignedIntegerGeneral implements IConverterSearchable<NumberUnsignedByte, UnsignedIntegerGeneral> {
		
		@Override
		public Class<NumberUnsignedByte> classFrom() {
			return NumberUnsignedByte.class;
		}
		
		@Override
		public Class<UnsignedIntegerGeneral> classTo() {
			return UnsignedIntegerGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public UnsignedIntegerGeneral convertNew(NumberUnsignedByte number) throws UnsupportedOperationException {
			return convertInstance(number, new UnsignedIntegerGeneral());
		}
		
		@Override
		public UnsignedIntegerGeneral convertInstance(NumberUnsignedByte number, UnsignedIntegerGeneral ret) {
			ret.number = BigPrimitiveMath.intArrayFromByteUnsigned(number.b);
			return ret;
		}
		
		@Override
		public UnsignedIntegerGeneral convertType(NumberUnsignedByte number, Class<? extends UnsignedIntegerGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedShortToUnsignedIntegerGeneral implements IConverterSearchable<NumberUnsignedShort, UnsignedIntegerGeneral> {
		
		@Override
		public Class<NumberUnsignedShort> classFrom() {
			return NumberUnsignedShort.class;
		}
		
		@Override
		public Class<UnsignedIntegerGeneral> classTo() {
			return UnsignedIntegerGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public UnsignedIntegerGeneral convertNew(NumberUnsignedShort number) throws UnsupportedOperationException {
			return convertInstance(number, new UnsignedIntegerGeneral());
		}
		
		@Override
		public UnsignedIntegerGeneral convertInstance(NumberUnsignedShort number, UnsignedIntegerGeneral ret) {
			ret.number = BigPrimitiveMath.intArrayFromShortUnsigned(number.s);
			return ret;
		}
		
		@Override
		public UnsignedIntegerGeneral convertType(NumberUnsignedShort number, Class<? extends UnsignedIntegerGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterIntToUnsignedIntegerGeneral implements IConverterSearchable<NumberUnsignedInt, UnsignedIntegerGeneral> {
		
		@Override
		public Class<NumberUnsignedInt> classFrom() {
			return NumberUnsignedInt.class;
		}
		
		@Override
		public Class<UnsignedIntegerGeneral> classTo() {
			return UnsignedIntegerGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public UnsignedIntegerGeneral convertNew(NumberUnsignedInt number) throws UnsupportedOperationException {
			return convertInstance(number, new UnsignedIntegerGeneral());
		}
		
		@Override
		public UnsignedIntegerGeneral convertInstance(NumberUnsignedInt number, UnsignedIntegerGeneral ret) {
			ret.number = BigPrimitiveMath.intArrayFromIntUnsigned(number.i);
			return ret;
		}
		
		@Override
		public UnsignedIntegerGeneral convertType(NumberUnsignedInt number, Class<? extends UnsignedIntegerGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterLongToUnsignedIntegerGeneral implements IConverterSearchable<NumberUnsignedLong, UnsignedIntegerGeneral> {
		
		@Override
		public Class<NumberUnsignedLong> classFrom() {
			return NumberUnsignedLong.class;
		}
		
		@Override
		public Class<UnsignedIntegerGeneral> classTo() {
			return UnsignedIntegerGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public UnsignedIntegerGeneral convertNew(NumberUnsignedLong number) throws UnsupportedOperationException {
			return convertInstance(number, new UnsignedIntegerGeneral());
		}
		
		@Override
		public UnsignedIntegerGeneral convertInstance(NumberUnsignedLong number, UnsignedIntegerGeneral ret) {
			ret.number = BigPrimitiveMath.intArrayFromLongUnsigned(number.l);
			return ret;
		}
		
		@Override
		public UnsignedIntegerGeneral convertType(NumberUnsignedLong number, Class<? extends UnsignedIntegerGeneral> type) {
			return convertNew(number);
		}
	}
	
	//from General
	@INumberConverter
	public static class ConverterUnsignedIntegerGeneralToByte implements IConverterSearchable<UnsignedIntegerGeneral, NumberUnsignedByte> {
		
		@Override
		public Class<UnsignedIntegerGeneral> classFrom() {
			return UnsignedIntegerGeneral.class;
		}
		
		@Override
		public Class<NumberUnsignedByte> classTo() {
			return NumberUnsignedByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberUnsignedByte convertNew(UnsignedIntegerGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedByte());
		}
		
		@Override
		public NumberUnsignedByte convertInstance(UnsignedIntegerGeneral number, NumberUnsignedByte ret) {
			ret.b = BigPrimitiveMath.byteFromIntArrayUnsigned(number.number);
			return ret;
		}
		
		@Override
		public NumberUnsignedByte convertType(UnsignedIntegerGeneral number, Class<? extends NumberUnsignedByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntegerGeneralToShort implements IConverterSearchable<UnsignedIntegerGeneral, NumberUnsignedShort> {
		
		@Override
		public Class<UnsignedIntegerGeneral> classFrom() {
			return UnsignedIntegerGeneral.class;
		}
		
		@Override
		public Class<NumberUnsignedShort> classTo() {
			return NumberUnsignedShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberUnsignedShort convertNew(UnsignedIntegerGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedShort());
		}
		
		@Override
		public NumberUnsignedShort convertInstance(UnsignedIntegerGeneral number, NumberUnsignedShort ret) {
			ret.s = BigPrimitiveMath.shortFromIntArrayUnsigned(number.number);
			return ret;
		}
		
		@Override
		public NumberUnsignedShort convertType(UnsignedIntegerGeneral number, Class<? extends NumberUnsignedShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntegerGeneralToInt implements IConverterSearchable<UnsignedIntegerGeneral, NumberUnsignedInt> {
		
		@Override
		public Class<UnsignedIntegerGeneral> classFrom() {
			return UnsignedIntegerGeneral.class;
		}
		
		@Override
		public Class<NumberUnsignedInt> classTo() {
			return NumberUnsignedInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberUnsignedInt convertNew(UnsignedIntegerGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedInt());
		}
		
		@Override
		public NumberUnsignedInt convertInstance(UnsignedIntegerGeneral number, NumberUnsignedInt ret) {
			ret.i = BigPrimitiveMath.intFromIntArrayUnsigned(number.number);
			return ret;
		}
		
		@Override
		public NumberUnsignedInt convertType(UnsignedIntegerGeneral number, Class<? extends NumberUnsignedInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntegerGeneralToLong implements IConverterSearchable<UnsignedIntegerGeneral, NumberUnsignedLong> {
		
		@Override
		public Class<UnsignedIntegerGeneral> classFrom() {
			return UnsignedIntegerGeneral.class;
		}
		
		@Override
		public Class<NumberUnsignedLong> classTo() {
			return NumberUnsignedLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberUnsignedLong convertNew(UnsignedIntegerGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedLong());
		}
		
		@Override
		public NumberUnsignedLong convertInstance(UnsignedIntegerGeneral number, NumberUnsignedLong ret) {
			ret.l = BigPrimitiveMath.longFromIntArrayUnsigned(number.number);
			return ret;
		}
		
		@Override
		public NumberUnsignedLong convertType(UnsignedIntegerGeneral number, Class<? extends NumberUnsignedLong> type) {
			return convertNew(number);
		}
	}
}
