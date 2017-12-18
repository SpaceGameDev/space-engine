package space.util.number.base;

import space.util.annotation.Self;

public interface NumberBase<@Self SELF extends NumberBase<SELF>> {
	
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
