package space.util.number.base;

import space.util.annotation.Self;

public interface NumberAddSub<@Self SELF extends NumberAddSub<SELF>> extends NumberBase<SELF> {
	
	@Self
	SELF add(SELF self);
	
	@Self
	SELF sub(SELF self);
	
	@Self
	SELF add(SELF self, int[] overflow);
	
	@Self
	SELF sub(SELF self, int[] overflow);
	
	@Self
	SELF negate();
}
