package space.engine.number.base;

import space.util.annotation.Self;
import space.util.creatorOld.CreatableObject;
import space.util.creatorOld.Creator;

/**
 * int[] arrays are all BigEndian<br>
 * sign: true means positive<br>
 * <br>
 * toString should be<br>
 * -sign: '-' is negative<br>
 * -int: LittleEndian hex (or decimal if available)<br>
 * -fp: fp value with 4 Decimals (see spaceOld.util.math.MathUtils.fpFormatterAcc)
 */
public interface INumberBase<@Self SELF extends INumberBase<SELF>> extends CreatableObject<Creator<SELF>> {
	
	@Self
	SELF set(SELF n);
	
	/**
	 * returns NOT itself
	 */
	SELF make();
	
	/**
	 * returns NOT itself
	 */
	SELF copy();
}
