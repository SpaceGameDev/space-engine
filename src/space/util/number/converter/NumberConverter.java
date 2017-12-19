package space.util.number.converter;

import space.util.conversion.smart.ConverterSmart;
import space.util.number.base.NumberBase;

public class NumberConverter {
	
	public static final int WEIGHT_NATIVE_CONVERSION = 128;
	public static final int WEIGHT_GENERAL_CONVERSION = 256;
	public static final int WEIGHT_BETWEEN_TYPES_CONVERSION = 512;
	
	public static final ConverterSmart<NumberBase> conv;
	
	static {
		conv = new ConverterSmart<>();
		
		new IntegerConverter().accept(conv);
		new UnsignedConverter().accept(conv);
	}
}
