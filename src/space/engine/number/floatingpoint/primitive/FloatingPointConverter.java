package space.engine.number.floatingpoint.primitive;

import spaceOld.util.conversion.IConverterSearchable;
import spaceOld.util.math.number.INumberConverter;
import spaceOld.util.math.number.floatingpoint.FloatingPointGeneral;

public class FloatingPointConverter {
	
	@INumberConverter
	public static class ConverterFloatToFloatingPointGeneral implements IConverterSearchable<NumberFloat, FloatingPointGeneral> {
		
		@Override
		public Class<NumberFloat> classFrom() {
			return NumberFloat.class;
		}
		
		@Override
		public Class<FloatingPointGeneral> classTo() {
			return FloatingPointGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public FloatingPointGeneral convertNew(NumberFloat number) throws UnsupportedOperationException {
			return convertInstance(number, new FloatingPointGeneral());
		}
		
		@Override
		public FloatingPointGeneral convertInstance(NumberFloat number, FloatingPointGeneral ret) {
			return number.get(ret);
		}
		
		@Override
		public FloatingPointGeneral convertType(NumberFloat number, Class<? extends FloatingPointGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterDoubleToFloatingPointGeneral implements IConverterSearchable<NumberDouble, FloatingPointGeneral> {
		
		@Override
		public Class<NumberDouble> classFrom() {
			return NumberDouble.class;
		}
		
		@Override
		public Class<FloatingPointGeneral> classTo() {
			return FloatingPointGeneral.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public FloatingPointGeneral convertNew(NumberDouble number) throws UnsupportedOperationException {
			return convertInstance(number, new FloatingPointGeneral());
		}
		
		@Override
		public FloatingPointGeneral convertInstance(NumberDouble number, FloatingPointGeneral ret) {
			return number.get(ret);
		}
		
		@Override
		public FloatingPointGeneral convertType(NumberDouble number, Class<? extends FloatingPointGeneral> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterFloatingPointGeneralToFloat implements IConverterSearchable<FloatingPointGeneral, NumberFloat> {
		
		@Override
		public Class<FloatingPointGeneral> classFrom() {
			return FloatingPointGeneral.class;
		}
		
		@Override
		public Class<NumberFloat> classTo() {
			return NumberFloat.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberFloat convertNew(FloatingPointGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFloat());
		}
		
		@Override
		public NumberFloat convertInstance(FloatingPointGeneral number, NumberFloat ret) {
			return ret.set(number);
		}
		
		@Override
		public NumberFloat convertType(FloatingPointGeneral number, Class<? extends NumberFloat> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterFloatingPointGeneralToDouble implements IConverterSearchable<FloatingPointGeneral, NumberDouble> {
		
		@Override
		public Class<FloatingPointGeneral> classFrom() {
			return FloatingPointGeneral.class;
		}
		
		@Override
		public Class<NumberDouble> classTo() {
			return NumberDouble.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberDouble convertNew(FloatingPointGeneral number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberDouble());
		}
		
		@Override
		public NumberDouble convertInstance(FloatingPointGeneral number, NumberDouble ret) {
			return ret.set(number);
		}
		
		@Override
		public NumberDouble convertType(FloatingPointGeneral number, Class<? extends NumberDouble> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterFloatToDouble implements IConverterSearchable<NumberFloat, NumberDouble> {
		
		@Override
		public Class<NumberFloat> classFrom() {
			return NumberFloat.class;
		}
		
		@Override
		public Class<NumberDouble> classTo() {
			return NumberDouble.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberDouble convertNew(NumberFloat number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberDouble());
		}
		
		@Override
		public NumberDouble convertInstance(NumberFloat number, NumberDouble ret) {
			ret.d = number.f;
			return ret;
		}
		
		@Override
		public NumberDouble convertType(NumberFloat number, Class<? extends NumberDouble> type) {
			return convertNew(number);
		}
	}
	
	@INumberConverter
	public static class ConverterDoubleToFloat implements IConverterSearchable<NumberDouble, NumberFloat> {
		
		@Override
		public Class<NumberDouble> classFrom() {
			return NumberDouble.class;
		}
		
		@Override
		public Class<NumberFloat> classTo() {
			return NumberFloat.class;
		}
		
		@Override
		public int weight() {
			return 0x00001000;
		}
		
		@Override
		public NumberFloat convertNew(NumberDouble number) throws UnsupportedOperationException {
			return convertInstance(number, new NumberFloat());
		}
		
		@Override
		public NumberFloat convertInstance(NumberDouble number, NumberFloat ret) {
			ret.f = (float) number.d;
			return ret;
		}
		
		@Override
		public NumberFloat convertType(NumberDouble number, Class<? extends NumberFloat> type) {
			return convertNew(number);
		}
	}
}
