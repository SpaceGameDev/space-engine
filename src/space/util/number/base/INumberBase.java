package space.util.number.base;

import space.util.annotation.Self;

public interface INumberBase<@Self SELF extends INumberBase<SELF>> {
	
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
