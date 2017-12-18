package space.util.number.floatingpoint.primitive;

import space.util.annotation.Self;
import space.util.number.floatingpoint.FloatingPointGeneral;
import space.util.number.floatingpoint.IFloatingPoint;

public abstract class IFloatingPointPrimitive<@Self SELF extends IFloatingPointPrimitive<SELF>> extends IFloatingPoint<SELF> {
	
	public FloatingPointGeneral get() {
		return get(new FloatingPointGeneral());
	}
	
	public abstract FloatingPointGeneral get(FloatingPointGeneral ret);
	
	public abstract SELF set(FloatingPointGeneral get);
}
