package space.util.number.base;

import space.util.annotation.Self;

public interface NumberComplex<@Self SELF extends NumberComplex<SELF>> extends NumberMulDiv<SELF> {
	
	//sqrt
	SELF sqrt();
	
	//functions
	SELF sin();
	
	SELF cos();
	
	SELF tan();
	
	SELF arcsin();
	
	SELF arccos();
	
	SELF arctan();
	
	SELF arctan2();
}
