package space.util.number.converter;

import space.util.conversion.smart.ConverterSmart;
import space.util.gui.monofont.MonofontGuiApi;
import space.util.number.base.NumberBase;
import space.util.string.toStringHelper.ToStringHelper;

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
	
	public static void main(String[] args) {
		ToStringHelper.setDefault(MonofontGuiApi.TSH);
		System.out.println(conv);
	}
}
