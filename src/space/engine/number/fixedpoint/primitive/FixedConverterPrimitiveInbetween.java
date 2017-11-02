package space.engine.number.fixedpoint.primitive;

import spaceOld.util.conversion.IConverterSearchable;
import spaceOld.util.math.number.INumberConverter;

public class FixedConverterPrimitiveInbetween {
	
	//primitive from byte
	@INumberConverter
	public static class ConverterUnsignedByteToUnsignedShort implements IConverterSearchable<NumberFixedByte, NumberFixedShort> {
		
		@Override
		public Class<NumberFixedByte> classFrom() {
			return NumberFixedByte.class;
		}
		
		@Override
		public Class<NumberFixedShort> classTo() {
			return NumberFixedShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedShort convertNew(NumberFixedByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedShort());
		}
		
		@Override
		public NumberFixedShort convertInstance(NumberFixedByte number, NumberFixedShort ret) {
			ret.s = (short) Byte.toUnsignedInt(number.b);
			return ret;
		}
		
		@Override
		public NumberFixedShort convertType(NumberFixedByte number, Class<? extends NumberFixedShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedByteToUnsignedInt implements IConverterSearchable<NumberFixedByte, NumberFixedInt> {
		
		@Override
		public Class<NumberFixedByte> classFrom() {
			return NumberFixedByte.class;
		}
		
		@Override
		public Class<NumberFixedInt> classTo() {
			return NumberFixedInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedInt convertNew(NumberFixedByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedInt());
		}
		
		@Override
		public NumberFixedInt convertInstance(NumberFixedByte number, NumberFixedInt ret) {
			ret.i = Byte.toUnsignedInt(number.b);
			return ret;
		}
		
		@Override
		public NumberFixedInt convertType(NumberFixedByte number, Class<? extends NumberFixedInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedByteToUnsignedLong implements IConverterSearchable<NumberFixedByte, NumberFixedLong> {
		
		@Override
		public Class<NumberFixedByte> classFrom() {
			return NumberFixedByte.class;
		}
		
		@Override
		public Class<NumberFixedLong> classTo() {
			return NumberFixedLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedLong convertNew(NumberFixedByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedLong());
		}
		
		@Override
		public NumberFixedLong convertInstance(NumberFixedByte number, NumberFixedLong ret) {
			ret.l = Byte.toUnsignedLong(number.b);
			return ret;
		}
		
		@Override
		public NumberFixedLong convertType(NumberFixedByte number, Class<? extends NumberFixedLong> type) {
			return convertNew(number);
		}
	}
	
	//primitive from short
	@INumberConverter
	public static class ConverterUnsignedShortToUnsignedByte implements IConverterSearchable<NumberFixedShort, NumberFixedByte> {
		
		@Override
		public Class<NumberFixedShort> classFrom() {
			return NumberFixedShort.class;
		}
		
		@Override
		public Class<NumberFixedByte> classTo() {
			return NumberFixedByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedByte convertNew(NumberFixedShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedByte());
		}
		
		@Override
		public NumberFixedByte convertInstance(NumberFixedShort number, NumberFixedByte ret) {
			ret.b = (byte) number.s;
			return ret;
		}
		
		@Override
		public NumberFixedByte convertType(NumberFixedShort number, Class<? extends NumberFixedByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedShortToUnsignedInt implements IConverterSearchable<NumberFixedShort, NumberFixedInt> {
		
		@Override
		public Class<NumberFixedShort> classFrom() {
			return NumberFixedShort.class;
		}
		
		@Override
		public Class<NumberFixedInt> classTo() {
			return NumberFixedInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedInt convertNew(NumberFixedShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedInt());
		}
		
		@Override
		public NumberFixedInt convertInstance(NumberFixedShort number, NumberFixedInt ret) {
			ret.i = Short.toUnsignedInt(number.s);
			return ret;
		}
		
		@Override
		public NumberFixedInt convertType(NumberFixedShort number, Class<? extends NumberFixedInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedShortToUnsignedLong implements IConverterSearchable<NumberFixedShort, NumberFixedLong> {
		
		@Override
		public Class<NumberFixedShort> classFrom() {
			return NumberFixedShort.class;
		}
		
		@Override
		public Class<NumberFixedLong> classTo() {
			return NumberFixedLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedLong convertNew(NumberFixedShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedLong());
		}
		
		@Override
		public NumberFixedLong convertInstance(NumberFixedShort number, NumberFixedLong ret) {
			ret.l = Short.toUnsignedLong(number.s);
			return ret;
		}
		
		@Override
		public NumberFixedLong convertType(NumberFixedShort number, Class<? extends NumberFixedLong> type) {
			return convertNew(number);
		}
	}
	
	//primitive from int
	@INumberConverter
	public static class ConverterUnsignedIntToUnsignedByte implements IConverterSearchable<NumberFixedInt, NumberFixedByte> {
		
		@Override
		public Class<NumberFixedInt> classFrom() {
			return NumberFixedInt.class;
		}
		
		@Override
		public Class<NumberFixedByte> classTo() {
			return NumberFixedByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedByte convertNew(NumberFixedInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedByte());
		}
		
		@Override
		public NumberFixedByte convertInstance(NumberFixedInt number, NumberFixedByte ret) {
			ret.b = (byte) number.i;
			return ret;
		}
		
		@Override
		public NumberFixedByte convertType(NumberFixedInt number, Class<? extends NumberFixedByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntToUnsignedShort implements IConverterSearchable<NumberFixedInt, NumberFixedShort> {
		
		@Override
		public Class<NumberFixedInt> classFrom() {
			return NumberFixedInt.class;
		}
		
		@Override
		public Class<NumberFixedShort> classTo() {
			return NumberFixedShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedShort convertNew(NumberFixedInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedShort());
		}
		
		@Override
		public NumberFixedShort convertInstance(NumberFixedInt number, NumberFixedShort ret) {
			ret.s = (short) number.i;
			return ret;
		}
		
		@Override
		public NumberFixedShort convertType(NumberFixedInt number, Class<? extends NumberFixedShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedIntToUnsignedLong implements IConverterSearchable<NumberFixedInt, NumberFixedLong> {
		
		@Override
		public Class<NumberFixedInt> classFrom() {
			return NumberFixedInt.class;
		}
		
		@Override
		public Class<NumberFixedLong> classTo() {
			return NumberFixedLong.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedLong convertNew(NumberFixedInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedLong());
		}
		
		@Override
		public NumberFixedLong convertInstance(NumberFixedInt number, NumberFixedLong ret) {
			ret.l = Integer.toUnsignedLong(number.i);
			return ret;
		}
		
		@Override
		public NumberFixedLong convertType(NumberFixedInt number, Class<? extends NumberFixedLong> type) {
			return convertNew(number);
		}
	}
	
	//primitive from long
	@INumberConverter
	public static class ConverterUnsignedLongToUnsignedByte implements IConverterSearchable<NumberFixedLong, NumberFixedByte> {
		
		@Override
		public Class<NumberFixedLong> classFrom() {
			return NumberFixedLong.class;
		}
		
		@Override
		public Class<NumberFixedByte> classTo() {
			return NumberFixedByte.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedByte convertNew(NumberFixedLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedByte());
		}
		
		@Override
		public NumberFixedByte convertInstance(NumberFixedLong number, NumberFixedByte ret) {
			ret.b = (byte) number.l;
			return ret;
		}
		
		@Override
		public NumberFixedByte convertType(NumberFixedLong number, Class<? extends NumberFixedByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedLongToUnsignedShort implements IConverterSearchable<NumberFixedLong, NumberFixedShort> {
		
		@Override
		public Class<NumberFixedLong> classFrom() {
			return NumberFixedLong.class;
		}
		
		@Override
		public Class<NumberFixedShort> classTo() {
			return NumberFixedShort.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedShort convertNew(NumberFixedLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedShort());
		}
		
		@Override
		public NumberFixedShort convertInstance(NumberFixedLong number, NumberFixedShort ret) {
			ret.s = (short) number.l;
			return ret;
		}
		
		@Override
		public NumberFixedShort convertType(NumberFixedLong number, Class<? extends NumberFixedShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterUnsignedLongToUnsignedLong implements IConverterSearchable<NumberFixedLong, NumberFixedInt> {
		
		@Override
		public Class<NumberFixedLong> classFrom() {
			return NumberFixedLong.class;
		}
		
		@Override
		public Class<NumberFixedInt> classTo() {
			return NumberFixedInt.class;
		}
		
		@Override
		public int weight() {
			return 0x00000100;
		}
		
		@Override
		public NumberFixedInt convertNew(NumberFixedLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFixedInt());
		}
		
		@Override
		public NumberFixedInt convertInstance(NumberFixedLong number, NumberFixedInt ret) {
			ret.i = (int) number.l;
			return ret;
		}
		
		@Override
		public NumberFixedInt convertType(NumberFixedLong number, Class<? extends NumberFixedInt> type) {
			return convertNew(number);
		}
	}
}
