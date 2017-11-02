package space.engine.number.unsigned.primitive;

import spaceOld.util.conversion.IConverterSearchable;
import spaceOld.util.math.number.INumberConverter;

public class UnsignedIntegerConverterPrimitiveInbetween {
	
	//primitive from byte
	@INumberConverter
	public static class ConverterUnsignedByteToUnsignedShort implements IConverterSearchable<NumberUnsignedByte, NumberUnsignedShort> {
		
		@Override
		public Class<NumberUnsignedByte> classFrom() {
			return NumberUnsignedByte.class;
		}
		
		@Override
		public Class<NumberUnsignedShort> classTo() {
			return NumberUnsignedShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedShort convertNew(NumberUnsignedByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedShort());
		}
		
		@Override
		public NumberUnsignedShort convertInstance(NumberUnsignedByte number, NumberUnsignedShort ret) {
			ret.s = (short) Byte.toUnsignedInt(number.b);
			return ret;
		}
		
		@Override
		public NumberUnsignedShort convertType(NumberUnsignedByte number, Class<? extends NumberUnsignedShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedByteToUnsignedInt implements IConverterSearchable<NumberUnsignedByte, NumberUnsignedInt> {
		
		@Override
		public Class<NumberUnsignedByte> classFrom() {
			return NumberUnsignedByte.class;
		}
		
		@Override
		public Class<NumberUnsignedInt> classTo() {
			return NumberUnsignedInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedInt convertNew(NumberUnsignedByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedInt());
		}
		
		@Override
		public NumberUnsignedInt convertInstance(NumberUnsignedByte number, NumberUnsignedInt ret) {
			ret.i = Byte.toUnsignedInt(number.b);
			return ret;
		}
		
		@Override
		public NumberUnsignedInt convertType(NumberUnsignedByte number, Class<? extends NumberUnsignedInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedByteToUnsignedLong implements IConverterSearchable<NumberUnsignedByte, NumberUnsignedLong> {
		
		@Override
		public Class<NumberUnsignedByte> classFrom() {
			return NumberUnsignedByte.class;
		}
		
		@Override
		public Class<NumberUnsignedLong> classTo() {
			return NumberUnsignedLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedLong convertNew(NumberUnsignedByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedLong());
		}
		
		@Override
		public NumberUnsignedLong convertInstance(NumberUnsignedByte number, NumberUnsignedLong ret) {
			ret.l = Byte.toUnsignedLong(number.b);
			return ret;
		}
		
		@Override
		public NumberUnsignedLong convertType(NumberUnsignedByte number, Class<? extends NumberUnsignedLong> type) {
			return convertNew(number);
		}
	}
	
	//primitive from short
	@INumberConverter
	public static class ConverterUnsignedShortToUnsignedByte implements IConverterSearchable<NumberUnsignedShort, NumberUnsignedByte> {
		
		@Override
		public Class<NumberUnsignedShort> classFrom() {
			return NumberUnsignedShort.class;
		}
		
		@Override
		public Class<NumberUnsignedByte> classTo() {
			return NumberUnsignedByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedByte convertNew(NumberUnsignedShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedByte());
		}
		
		@Override
		public NumberUnsignedByte convertInstance(NumberUnsignedShort number, NumberUnsignedByte ret) {
			ret.b = (byte) number.s;
			return ret;
		}
		
		@Override
		public NumberUnsignedByte convertType(NumberUnsignedShort number, Class<? extends NumberUnsignedByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedShortToUnsignedInt implements IConverterSearchable<NumberUnsignedShort, NumberUnsignedInt> {
		
		@Override
		public Class<NumberUnsignedShort> classFrom() {
			return NumberUnsignedShort.class;
		}
		
		@Override
		public Class<NumberUnsignedInt> classTo() {
			return NumberUnsignedInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedInt convertNew(NumberUnsignedShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedInt());
		}
		
		@Override
		public NumberUnsignedInt convertInstance(NumberUnsignedShort number, NumberUnsignedInt ret) {
			ret.i = Short.toUnsignedInt(number.s);
			return ret;
		}
		
		@Override
		public NumberUnsignedInt convertType(NumberUnsignedShort number, Class<? extends NumberUnsignedInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedShortToUnsignedLong implements IConverterSearchable<NumberUnsignedShort, NumberUnsignedLong> {
		
		@Override
		public Class<NumberUnsignedShort> classFrom() {
			return NumberUnsignedShort.class;
		}
		
		@Override
		public Class<NumberUnsignedLong> classTo() {
			return NumberUnsignedLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedLong convertNew(NumberUnsignedShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedLong());
		}
		
		@Override
		public NumberUnsignedLong convertInstance(NumberUnsignedShort number, NumberUnsignedLong ret) {
			ret.l = Short.toUnsignedLong(number.s);
			return ret;
		}
		
		@Override
		public NumberUnsignedLong convertType(NumberUnsignedShort number, Class<? extends NumberUnsignedLong> type) {
			return convertNew(number);
		}
	}
	
	//primitive from int
	@INumberConverter
	public static class ConverterUnsignedIntToUnsignedByte implements IConverterSearchable<NumberUnsignedInt, NumberUnsignedByte> {
		
		@Override
		public Class<NumberUnsignedInt> classFrom() {
			return NumberUnsignedInt.class;
		}
		
		@Override
		public Class<NumberUnsignedByte> classTo() {
			return NumberUnsignedByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedByte convertNew(NumberUnsignedInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedByte());
		}
		
		@Override
		public NumberUnsignedByte convertInstance(NumberUnsignedInt number, NumberUnsignedByte ret) {
			ret.b = (byte) number.i;
			return ret;
		}
		
		@Override
		public NumberUnsignedByte convertType(NumberUnsignedInt number, Class<? extends NumberUnsignedByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntToUnsignedShort implements IConverterSearchable<NumberUnsignedInt, NumberUnsignedShort> {
		
		@Override
		public Class<NumberUnsignedInt> classFrom() {
			return NumberUnsignedInt.class;
		}
		
		@Override
		public Class<NumberUnsignedShort> classTo() {
			return NumberUnsignedShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedShort convertNew(NumberUnsignedInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedShort());
		}
		
		@Override
		public NumberUnsignedShort convertInstance(NumberUnsignedInt number, NumberUnsignedShort ret) {
			ret.s = (short) number.i;
			return ret;
		}
		
		@Override
		public NumberUnsignedShort convertType(NumberUnsignedInt number, Class<? extends NumberUnsignedShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntToUnsignedLong implements IConverterSearchable<NumberUnsignedInt, NumberUnsignedLong> {
		
		@Override
		public Class<NumberUnsignedInt> classFrom() {
			return NumberUnsignedInt.class;
		}
		
		@Override
		public Class<NumberUnsignedLong> classTo() {
			return NumberUnsignedLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedLong convertNew(NumberUnsignedInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedLong());
		}
		
		@Override
		public NumberUnsignedLong convertInstance(NumberUnsignedInt number, NumberUnsignedLong ret) {
			ret.l = Integer.toUnsignedLong(number.i);
			return ret;
		}
		
		@Override
		public NumberUnsignedLong convertType(NumberUnsignedInt number, Class<? extends NumberUnsignedLong> type) {
			return convertNew(number);
		}
	}
	
	//primitive from long
	@INumberConverter
	public static class ConverterUnsignedLongToUnsignedByte implements IConverterSearchable<NumberUnsignedLong, NumberUnsignedByte> {
		
		@Override
		public Class<NumberUnsignedLong> classFrom() {
			return NumberUnsignedLong.class;
		}
		
		@Override
		public Class<NumberUnsignedByte> classTo() {
			return NumberUnsignedByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedByte convertNew(NumberUnsignedLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedByte());
		}
		
		@Override
		public NumberUnsignedByte convertInstance(NumberUnsignedLong number, NumberUnsignedByte ret) {
			ret.b = (byte) number.l;
			return ret;
		}
		
		@Override
		public NumberUnsignedByte convertType(NumberUnsignedLong number, Class<? extends NumberUnsignedByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedLongToUnsignedShort implements IConverterSearchable<NumberUnsignedLong, NumberUnsignedShort> {
		
		@Override
		public Class<NumberUnsignedLong> classFrom() {
			return NumberUnsignedLong.class;
		}
		
		@Override
		public Class<NumberUnsignedShort> classTo() {
			return NumberUnsignedShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedShort convertNew(NumberUnsignedLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedShort());
		}
		
		@Override
		public NumberUnsignedShort convertInstance(NumberUnsignedLong number, NumberUnsignedShort ret) {
			ret.s = (short) number.l;
			return ret;
		}
		
		@Override
		public NumberUnsignedShort convertType(NumberUnsignedLong number, Class<? extends NumberUnsignedShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedLongToUnsignedLong implements IConverterSearchable<NumberUnsignedLong, NumberUnsignedInt> {
		
		@Override
		public Class<NumberUnsignedLong> classFrom() {
			return NumberUnsignedLong.class;
		}
		
		@Override
		public Class<NumberUnsignedInt> classTo() {
			return NumberUnsignedInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberUnsignedInt convertNew(NumberUnsignedLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedInt());
		}
		
		@Override
		public NumberUnsignedInt convertInstance(NumberUnsignedLong number, NumberUnsignedInt ret) {
			ret.i = (int) number.l;
			return ret;
		}
		
		@Override
		public NumberUnsignedInt convertType(NumberUnsignedLong number, Class<? extends NumberUnsignedInt> type) {
			return convertNew(number);
		}
	}
}
