package space.engine.number.integer.primitive;

import spaceOld.util.conversion.IConverterSearchable;
import spaceOld.util.math.number.INumberConverter;

public class IntegerConverterPrimitiveInbetween {
	
	//primitive from byte
	@INumberConverter
	public static class ConverterByteToShort implements IConverterSearchable<NumberByte, NumberShort> {
		
		@Override
		public Class<NumberByte> classFrom() {
			return NumberByte.class;
		}
		
		@Override
		public Class<NumberShort> classTo() {
			return NumberShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberShort convertNew(NumberByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberShort());
		}
		
		@Override
		public NumberShort convertInstance(NumberByte number, NumberShort ret) {
			ret.s = (short) number.b;
			return ret;
		}
		
		@Override
		public NumberShort convertType(NumberByte number, Class<? extends NumberShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterByteToInt implements IConverterSearchable<NumberByte, NumberInt> {
		
		@Override
		public Class<NumberByte> classFrom() {
			return NumberByte.class;
		}
		
		@Override
		public Class<NumberInt> classTo() {
			return NumberInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberInt convertNew(NumberByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberInt());
		}
		
		@Override
		public NumberInt convertInstance(NumberByte number, NumberInt ret) {
			ret.i = number.b;
			return ret;
		}
		
		@Override
		public NumberInt convertType(NumberByte number, Class<? extends NumberInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterByteToLong implements IConverterSearchable<NumberByte, NumberLong> {
		
		@Override
		public Class<NumberByte> classFrom() {
			return NumberByte.class;
		}
		
		@Override
		public Class<NumberLong> classTo() {
			return NumberLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberLong convertNew(NumberByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberLong());
		}
		
		@Override
		public NumberLong convertInstance(NumberByte number, NumberLong ret) {
			ret.l = number.b;
			return ret;
		}
		
		@Override
		public NumberLong convertType(NumberByte number, Class<? extends NumberLong> type) {
			return convertNew(number);
		}
	}
	
	//primitive from short
	@INumberConverter
	public static class ConverterShortToByte implements IConverterSearchable<NumberShort, NumberByte> {
		
		@Override
		public Class<NumberShort> classFrom() {
			return NumberShort.class;
		}
		
		@Override
		public Class<NumberByte> classTo() {
			return NumberByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberByte convertNew(NumberShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberByte());
		}
		
		@Override
		public NumberByte convertInstance(NumberShort number, NumberByte ret) {
			ret.b = (byte) number.s;
			return ret;
		}
		
		@Override
		public NumberByte convertType(NumberShort number, Class<? extends NumberByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterShortToInt implements IConverterSearchable<NumberShort, NumberInt> {
		
		@Override
		public Class<NumberShort> classFrom() {
			return NumberShort.class;
		}
		
		@Override
		public Class<NumberInt> classTo() {
			return NumberInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberInt convertNew(NumberShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberInt());
		}
		
		@Override
		public NumberInt convertInstance(NumberShort number, NumberInt ret) {
			ret.i = number.s;
			return ret;
		}
		
		@Override
		public NumberInt convertType(NumberShort number, Class<? extends NumberInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterShortToLong implements IConverterSearchable<NumberShort, NumberLong> {
		
		@Override
		public Class<NumberShort> classFrom() {
			return NumberShort.class;
		}
		
		@Override
		public Class<NumberLong> classTo() {
			return NumberLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberLong convertNew(NumberShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberLong());
		}
		
		@Override
		public NumberLong convertInstance(NumberShort number, NumberLong ret) {
			ret.l = number.s;
			return ret;
		}
		
		@Override
		public NumberLong convertType(NumberShort number, Class<? extends NumberLong> type) {
			return convertNew(number);
		}
	}
	
	//primitive from int
	@INumberConverter
	public static class ConverterIntToByte implements IConverterSearchable<NumberInt, NumberByte> {
		
		@Override
		public Class<NumberInt> classFrom() {
			return NumberInt.class;
		}
		
		@Override
		public Class<NumberByte> classTo() {
			return NumberByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberByte convertNew(NumberInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberByte());
		}
		
		@Override
		public NumberByte convertInstance(NumberInt number, NumberByte ret) {
			ret.b = (byte) number.i;
			return ret;
		}
		
		@Override
		public NumberByte convertType(NumberInt number, Class<? extends NumberByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterIntToShort implements IConverterSearchable<NumberInt, NumberShort> {
		
		@Override
		public Class<NumberInt> classFrom() {
			return NumberInt.class;
		}
		
		@Override
		public Class<NumberShort> classTo() {
			return NumberShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberShort convertNew(NumberInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberShort());
		}
		
		@Override
		public NumberShort convertInstance(NumberInt number, NumberShort ret) {
			ret.s = (short) number.i;
			return ret;
		}
		
		@Override
		public NumberShort convertType(NumberInt number, Class<? extends NumberShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterIntToLong implements IConverterSearchable<NumberInt, NumberLong> {
		
		@Override
		public Class<NumberInt> classFrom() {
			return NumberInt.class;
		}
		
		@Override
		public Class<NumberLong> classTo() {
			return NumberLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberLong convertNew(NumberInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberLong());
		}
		
		@Override
		public NumberLong convertInstance(NumberInt number, NumberLong ret) {
			ret.l = number.i;
			return ret;
		}
		
		@Override
		public NumberLong convertType(NumberInt number, Class<? extends NumberLong> type) {
			return convertNew(number);
		}
	}
	
	//primitive from long
	@INumberConverter
	public static class ConverterLongToByte implements IConverterSearchable<NumberLong, NumberByte> {
		
		@Override
		public Class<NumberLong> classFrom() {
			return NumberLong.class;
		}
		
		@Override
		public Class<NumberByte> classTo() {
			return NumberByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberByte convertNew(NumberLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberByte());
		}
		
		@Override
		public NumberByte convertInstance(NumberLong number, NumberByte ret) {
			ret.b = (byte) number.l;
			return ret;
		}
		
		@Override
		public NumberByte convertType(NumberLong number, Class<? extends NumberByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterLongToShort implements IConverterSearchable<NumberLong, NumberShort> {
		
		@Override
		public Class<NumberLong> classFrom() {
			return NumberLong.class;
		}
		
		@Override
		public Class<NumberShort> classTo() {
			return NumberShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberShort convertNew(NumberLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberShort());
		}
		
		@Override
		public NumberShort convertInstance(NumberLong number, NumberShort ret) {
			ret.s = (short) number.l;
			return ret;
		}
		
		@Override
		public NumberShort convertType(NumberLong number, Class<? extends NumberShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterLongToLong implements IConverterSearchable<NumberLong, NumberInt> {
		
		@Override
		public Class<NumberLong> classFrom() {
			return NumberLong.class;
		}
		
		@Override
		public Class<NumberInt> classTo() {
			return NumberInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberInt convertNew(NumberLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberInt());
		}
		
		@Override
		public NumberInt convertInstance(NumberLong number, NumberInt ret) {
			ret.i = (int) number.l;
			return ret;
		}
		
		@Override
		public NumberInt convertType(NumberLong number, Class<? extends NumberInt> type) {
			return convertNew(number);
		}
	}
}
