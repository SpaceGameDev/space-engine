package space.util.number.base;

import space.util.annotation.Self;

public interface INumberComplex<@Self SELF extends INumberComplex<SELF>> extends INumberSimple<SELF> {
	
	//multiply - divide
	SELF multiply(SELF self);
	
	SELF divide(SELF self);
	
	//pow - sqrt
	SELF pow2();
	
	SELF pow(SELF pow);
	
	SELF sqrt();
	
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
	
	//functions
	SELF sin();
	
	SELF cos();
	
	SELF tan();
	
	SELF arcsin();
	
	SELF arccos();
	
	SELF arctan();
	
	SELF arctan2();
}
