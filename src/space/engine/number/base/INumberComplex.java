package space.engine.number.base;

import space.util.annotation.Self;

public interface INumberComplex<@Self SELF extends INumberComplex<SELF>> extends INumberSimple<SELF> {
	
	SELF multiply(SELF self);
	
	SELF divide(SELF self);
	
	SELF pow2();
	
	SELF pow(SELF pow);
	
	SELF sqrt();
	
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
