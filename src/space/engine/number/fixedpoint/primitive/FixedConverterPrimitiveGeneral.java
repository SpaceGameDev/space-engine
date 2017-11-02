package space.engine.number.fixedpoint.primitive;

import spaceOld.util.conversion.IConverterSearchable;
import spaceOld.util.math.BigPrimitiveMath;
import spaceOld.util.math.number.INumberConverter;
import spaceOld.util.math.number.fixedpoint.FixedPointGeneral;

public class FixedConverterPrimitiveGeneral {
	
	//to General
	@INumberConverter
	public static class ConverterUnsignedByteToUnsignedIntegerGeneral implements IConverterSearchable<NumberFixedByte, FixedPointGeneral> {
		
		@Override
		public Class<NumberFixedByte> classFrom() {
			return NumberFixedByte.class;
		}
		
		@Override
		public Class<FixedPointGeneral> classTo() {
			return FixedPointGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public FixedPointGeneral convertNew(NumberFixedByte number) throws UnsupportedOperationException {
			return convertInstance(number, new FixedPointGeneral());
		}
		
		@Override
		public FixedPointGeneral convertInstance(NumberFixedByte number, FixedPointGeneral ret) {
			ret.number = BigPrimitiveMath.intArrayFromByteFixed(number.b);
			ret.bits = 8;
			return ret;
		}
		
		@Override
		public FixedPointGeneral convertType(NumberFixedByte number, Class<? extends FixedPointGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedShortToUnsignedIntegerGeneral implements IConverterSearchable<NumberFixedShort, FixedPointGeneral> {
		
		@Override
		public Class<NumberFixedShort> classFrom() {
			return NumberFixedShort.class;
		}
		
		@Override
		public Class<FixedPointGeneral> classTo() {
			return FixedPointGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public FixedPointGeneral convertNew(NumberFixedShort number) throws UnsupportedOperationException {
			return convertInstance(number, new FixedPointGeneral());
		}
		
		@Override
		public FixedPointGeneral convertInstance(NumberFixedShort number, FixedPointGeneral ret) {
			ret.number = BigPrimitiveMath.intArrayFromShortFixed(number.s);
			ret.bits = 16;
			return ret;
		}
		
		@Override
		public FixedPointGeneral convertType(NumberFixedShort number, Class<? extends FixedPointGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterIntToUnsignedIntegerGeneral implements IConverterSearchable<NumberFixedInt, FixedPointGeneral> {
		
		@Override
		public Class<NumberFixedInt> classFrom() {
			return NumberFixedInt.class;
		}
		
		@Override
		public Class<FixedPointGeneral> classTo() {
			return FixedPointGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public FixedPointGeneral convertNew(NumberFixedInt number) throws UnsupportedOperationException {
			return convertInstance(number, new FixedPointGeneral());
		}
		
		@Override
		public FixedPointGeneral convertInstance(NumberFixedInt number, FixedPointGeneral ret) {
			ret.number = BigPrimitiveMath.intArrayFromIntFixed(number.i);
			ret.bits = 32;
			return ret;
		}
		
		@Override
		public FixedPointGeneral convertType(NumberFixedInt number, Class<? extends FixedPointGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterLongToUnsignedIntegerGeneral implements IConverterSearchable<NumberFixedLong, FixedPointGeneral> {
		
		@Override
		public Class<NumberFixedLong> classFrom() {
			return NumberFixedLong.class;
		}
		
		@Override
		public Class<FixedPointGeneral> classTo() {
			return FixedPointGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public FixedPointGeneral convertNew(NumberFixedLong number) throws UnsupportedOperationException {
			return convertInstance(number, new FixedPointGeneral());
		}
		
		@Override
		public FixedPointGeneral convertInstance(NumberFixedLong number, FixedPointGeneral ret) {
			ret.number = BigPrimitiveMath.intArrayFromLongFixed(number.l);
			ret.bits = 64;
			return ret;
		}
		
		@Override
		public FixedPointGeneral convertType(NumberFixedLong number, Class<? extends FixedPointGeneral> type) {
			return convertNew(number);
		}
	}
	
	//from General
	@INumberConverter
	public static class ConverterUnsignedIntegerGeneralToByte implements IConverterSearchable<FixedPointGeneral, NumberFixedByte> {
		
		@Override
		public Class<FixedPointGeneral> classFrom() {
			return FixedPointGeneral.class;
		}
		
		@Override
		public Class<NumberFixedByte> classTo() {
			return NumberFixedByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberFixedByte convertNew(FixedPointGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedByte());
		}
		
		@Override
		public NumberFixedByte convertInstance(FixedPointGeneral number, NumberFixedByte ret) {
			ret.b = BigPrimitiveMath.byteFromIntArrayFixed(number.number);
			return ret;
		}
		
		@Override
		public NumberFixedByte convertType(FixedPointGeneral number, Class<? extends NumberFixedByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntegerGeneralToShort implements IConverterSearchable<FixedPointGeneral, NumberFixedShort> {
		
		@Override
		public Class<FixedPointGeneral> classFrom() {
			return FixedPointGeneral.class;
		}
		
		@Override
		public Class<NumberFixedShort> classTo() {
			return NumberFixedShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberFixedShort convertNew(FixedPointGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedShort());
		}
		
		@Override
		public NumberFixedShort convertInstance(FixedPointGeneral number, NumberFixedShort ret) {
			ret.s = BigPrimitiveMath.shortFromIntArrayFixed(number.number);
			return ret;
		}
		
		@Override
		public NumberFixedShort convertType(FixedPointGeneral number, Class<? extends NumberFixedShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntegerGeneralToInt implements IConverterSearchable<FixedPointGeneral, NumberFixedInt> {
		
		@Override
		public Class<FixedPointGeneral> classFrom() {
			return FixedPointGeneral.class;
		}
		
		@Override
		public Class<NumberFixedInt> classTo() {
			return NumberFixedInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberFixedInt convertNew(FixedPointGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedInt());
		}
		
		@Override
		public NumberFixedInt convertInstance(FixedPointGeneral number, NumberFixedInt ret) {
			ret.i = BigPrimitiveMath.intFromIntArrayFixed(number.number);
			return ret;
		}
		
		@Override
		public NumberFixedInt convertType(FixedPointGeneral number, Class<? extends NumberFixedInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntegerGeneralToLong implements IConverterSearchable<FixedPointGeneral, NumberFixedLong> {
		
		@Override
		public Class<FixedPointGeneral> classFrom() {
			return FixedPointGeneral.class;
		}
		
		@Override
		public Class<NumberFixedLong> classTo() {
			return NumberFixedLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberFixedLong convertNew(FixedPointGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedLong());
		}
		
		@Override
		public NumberFixedLong convertInstance(FixedPointGeneral number, NumberFixedLong ret) {
			ret.l = BigPrimitiveMath.longFromIntArrayFixed(number.number);
			return ret;
		}
		
		@Override
		public NumberFixedLong convertType(FixedPointGeneral number, Class<? extends NumberFixedLong> type) {
			return convertNew(number);
		}
	}
}
