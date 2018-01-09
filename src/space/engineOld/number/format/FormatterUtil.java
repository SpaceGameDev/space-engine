package space.engineOld.number.format;

import space.util.math.MathUtils;

public class FormatterUtil {
	
	public static FormatterUtil INSTANCE = new FormatterUtil();
	
	//types
	public IIntegerToString UNSIGNED = new UnsignedToString(this);
	public IIntegerToString INTEGER = new IntegerToString(this);
	public IBytesToString BYTES = new HexToString(this);
	
	//buffer sizes
	public int charCacheSize = 8;
	public int charCacheIncreaseShift = 1;
	
	//general
	public char fillChar = ' ';
	public char fillCharNumber = '0';
	public char fillCharOutOfNumbers = fillChar;
	public String minus = "-";
	public char[] digits = MathUtils.digits;
	
	//floatingpoint
	public String PositiveZero = "0";
	public String NegativeZero = "-0";
	public String NaN = "NaN";
	public String PositiveInfinity = "+Inf";
	public String NegativeInfinity = "-Inf";
	
	public FormatterUtil() {
	}
}
