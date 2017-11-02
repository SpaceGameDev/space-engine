package space.engine.number.inbetween;

import spaceOld.util.conversion.IConverterSearchable;
import spaceOld.util.math.number.INumberConverter;
import spaceOld.util.math.number.integer.primitive.NumberByte;
import spaceOld.util.math.number.integer.primitive.NumberInt;
import spaceOld.util.math.number.integer.primitive.NumberLong;
import spaceOld.util.math.number.integer.primitive.NumberShort;
import spaceOld.util.math.number.unsigned.primitive.NumberUnsignedByte;
import spaceOld.util.math.number.unsigned.primitive.NumberUnsignedInt;
import spaceOld.util.math.number.unsigned.primitive.NumberUnsignedLong;
import spaceOld.util.math.number.unsigned.primitive.NumberUnsignedShort;

public class IntegerAndUnsignedIntegerConverter {
	
	//Byte
	@INumberConverter
	public static class ConverterUnsignedByteToByte implements IConverterSearchable<NumberUnsignedByte, NumberByte> {
		
		@Override
		public Class<NumberUnsignedByte> classFrom() {
			return NumberUnsignedByte.class;
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
		public NumberByte convertNew(NumberUnsignedByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberByte());
		}
		
		@Override
		public NumberByte convertInstance(NumberUnsignedByte number, NumberByte ret) {
			ret.b = number.b;
			return ret;
		}
		
		@Override
		public NumberByte convertType(NumberUnsignedByte number, Class<? extends NumberByte> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterByteToUnsignedByte implements IConverterSearchable<NumberByte, NumberUnsignedByte> {
		
		@Override
		public Class<NumberByte> classFrom() {
			return NumberByte.class;
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
		public NumberUnsignedByte convertNew(NumberByte number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedByte());
		}
		
		@Override
		public NumberUnsignedByte convertInstance(NumberByte number, NumberUnsignedByte ret) {
			ret.b = number.b;
			return ret;
		}
		
		@Override
		public NumberUnsignedByte convertType(NumberByte number, Class<? extends NumberUnsignedByte> type) {
			return convertNew(number);
		}
	}
	
	//Short
	@INumberConverter
	public static class ConverterUnsignedShortToShort implements IConverterSearchable<NumberUnsignedShort, NumberShort> {
		
		@Override
		public Class<NumberUnsignedShort> classFrom() {
			return NumberUnsignedShort.class;
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
		public NumberShort convertNew(NumberUnsignedShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberShort());
		}
		
		@Override
		public NumberShort convertInstance(NumberUnsignedShort number, NumberShort ret) {
			ret.s = number.s;
			return ret;
		}
		
		@Override
		public NumberShort convertType(NumberUnsignedShort number, Class<? extends NumberShort> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterShortToUnsignedShort implements IConverterSearchable<NumberShort, NumberUnsignedShort> {
		
		@Override
		public Class<NumberShort> classFrom() {
			return NumberShort.class;
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
		public NumberUnsignedShort convertNew(NumberShort number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedShort());
		}
		
		@Override
		public NumberUnsignedShort convertInstance(NumberShort number, NumberUnsignedShort ret) {
			ret.s = number.s;
			return ret;
		}
		
		@Override
		public NumberUnsignedShort convertType(NumberShort number, Class<? extends NumberUnsignedShort> type) {
			return convertNew(number);
		}
	}
	
	//Int
	@INumberConverter
	public static class ConverterUnsignedIntToInt implements IConverterSearchable<NumberUnsignedInt, NumberInt> {
		
		@Override
		public Class<NumberUnsignedInt> classFrom() {
			return NumberUnsignedInt.class;
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
		public NumberInt convertNew(NumberUnsignedInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberInt());
		}
		
		@Override
		public NumberInt convertInstance(NumberUnsignedInt number, NumberInt ret) {
			ret.i = number.i;
			return ret;
		}
		
		@Override
		public NumberInt convertType(NumberUnsignedInt number, Class<? extends NumberInt> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterIntToUnsignedInt implements IConverterSearchable<NumberInt, NumberUnsignedInt> {
		
		@Override
		public Class<NumberInt> classFrom() {
			return NumberInt.class;
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
		public NumberUnsignedInt convertNew(NumberInt number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedInt());
		}
		
		@Override
		public NumberUnsignedInt convertInstance(NumberInt number, NumberUnsignedInt ret) {
			ret.i = number.i;
			return ret;
		}
		
		@Override
		public NumberUnsignedInt convertType(NumberInt number, Class<? extends NumberUnsignedInt> type) {
			return convertNew(number);
		}
	}
	
	//Long
	@INumberConverter
	public static class ConverterUnsignedLongToLong implements IConverterSearchable<NumberUnsignedLong, NumberLong> {
		
		@Override
		public Class<NumberUnsignedLong> classFrom() {
			return NumberUnsignedLong.class;
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
		public NumberLong convertNew(NumberUnsignedLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberLong());
		}
		
		@Override
		public NumberLong convertInstance(NumberUnsignedLong number, NumberLong ret) {
			ret.l = number.l;
			return ret;
		}
		
		@Override
		public NumberLong convertType(NumberUnsignedLong number, Class<? extends NumberLong> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterLongToUnsignedLong implements IConverterSearchable<NumberLong, NumberUnsignedLong> {
		
		@Override
		public Class<NumberLong> classFrom() {
			return NumberLong.class;
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
		public NumberUnsignedLong convertNew(NumberLong number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberUnsignedLong());
		}
		
		@Override
		public NumberUnsignedLong convertInstance(NumberLong number, NumberUnsignedLong ret) {
			ret.l = number.l;
			return ret;
		}
		
		@Override
		public NumberUnsignedLong convertType(NumberLong number, Class<? extends NumberUnsignedLong> type) {
			return convertNew(number);
		}
	}
}
