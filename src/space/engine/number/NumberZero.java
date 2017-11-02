package space.engine.number;

import space.engine.number.fixedpoint.FixedPointGeneral;
import space.engine.number.floatingpoint.FloatingPointGeneral;
import space.engine.number.floatingpoint.FloatingPointSpecialNumber;
import space.engine.number.integer.IntegerGeneral;
import space.engine.number.unsigned.UnsignedIntegerGeneral;

public class NumberZero {
	
	public static final UnsignedIntegerGeneral UnsignedIntegerZero = new UnsignedIntegerGeneral(new int[] {});
	public static final IntegerGeneral IntegerZero = new IntegerGeneral(true, new int[] {});
	public static final FixedPointGeneral FixedPointZero = new FixedPointGeneral(0, new int[] {});
	public static final FloatingPointGeneral FloatingPointZero = new FloatingPointGeneral(FloatingPointSpecialNumber.ZERO, true, FixedPointZero, IntegerZero);
}
