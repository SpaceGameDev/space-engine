package space.util.number.base;

import space.util.annotation.Self;

public interface NumberMulDiv<@Self SELF extends NumberMulDiv<SELF>> extends NumberAddSub<SELF> {
	
	//multiply - divide
	SELF multiply(SELF self);
	
	SELF divide(SELF self);
	
	//pow
	SELF pow2();
	
	SELF pow(SELF pow);
	
	//shift
	
	/**
	 * tinier<br>
	 * number >> bits
	 */
	SELF shiftRight(SELF shift);
	
	/**
	 * tinier<br>
	 * number >>> bits
	 */
	SELF shiftRightLog(SELF shift);
	
	/**
	 * bigger<br>
	 * number << bits
	 */
	SELF shiftLeft(SELF shift);
}
