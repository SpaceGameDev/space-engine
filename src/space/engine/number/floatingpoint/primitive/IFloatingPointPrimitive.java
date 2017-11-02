package space.engine.number.floatingpoint.primitive;

import space.engine.number.floatingpoint.FloatingPointGeneral;
import space.engine.number.floatingpoint.IFloatingPoint;
import space.util.annotation.Self;

public abstract class IFloatingPointPrimitive<@Self SELF extends IFloatingPointPrimitive<SELF>> extends IFloatingPoint<SELF> {
	
	public FloatingPointGeneral get() {
		return get(new FloatingPointGeneral());
	}
	
	public abstract FloatingPointGeneral get(FloatingPointGeneral ret);
	
	public abstract SELF set(FloatingPointGeneral get);
}
