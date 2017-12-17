package space.util.number.base;

import space.util.annotation.Self;

public interface INumberSimple<@Self SELF extends INumberSimple<SELF>> extends INumberBase<SELF> {
	
	@Self
	SELF add(SELF self);
	
	@Self
	SELF sub(SELF self);
	
	@Self
	default SELF add(SELF self, int[] overflow) {
		return add(self);
	}
	
	@Self
	default SELF sub(SELF self, int[] overflow) {
		return sub(self);
	}
	
	@Self
	SELF negate();
}
