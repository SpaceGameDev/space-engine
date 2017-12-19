package space.util.number;

import space.util.number.fixedpoint.FixedPointGeneral;
import space.util.number.floatingpoint.FloatingPointGeneral;
import space.util.number.floatingpoint.FloatingPointSpecialNumber;
import space.util.number.integer.IntegerGeneral;
import space.util.number.unsigned.UnsignedGeneral;

public class NumberZero {
	
	public static final UnsignedGeneral UnsignedIntegerZero = new UnsignedGeneral(new int[] {});
	public static final IntegerGeneral IntegerZero = new IntegerGeneral(true, new int[] {});
	public static final FixedPointGeneral FixedPointZero = new FixedPointGeneral(0, new int[] {});
	public static final FloatingPointGeneral FloatingPointZero = new FloatingPointGeneral(FloatingPointSpecialNumber.ZERO, true, FixedPointZero, IntegerZero);
}
