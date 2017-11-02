package space.engine.number.base;

import space.util.annotation.Self;
import space.util.wrapper.IntWrapper.IntWrapper1;

public interface INumberSimple<@Self SELF extends INumberSimple<SELF>> extends INumberBase<SELF> {
	
	@Self
	SELF add(SELF self);
	
	@Self
	SELF sub(SELF self);
	
	@Self
	default SELF add(SELF self, IntWrapper1 overflow) {
		return add(self);
	}
	
	@Self
	default SELF sub(SELF self, IntWrapper1 overflow) {
		return sub(self);
	}
	
	@Self
	SELF negate();
}
