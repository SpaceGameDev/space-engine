package space.util.number.unsigned;

import space.util.annotation.Self;
import space.util.number.base.NumberBase;
import space.util.number.format.IIntegerToString;

import java.util.function.Supplier;

/**
 * Represents an Unsigned Integer.
 */
public abstract class IUnsignedInteger<@Self SELF extends IUnsignedInteger<SELF>> implements NumberBase<SELF> {
	
	static IIntegerToString get() {
		return UnsignedIntegerGeneral.DEFAULT_TO_STRING.get();
	}
	
	static void set(IIntegerToString toString) {
		set(() -> toString);
	}
	
	static void set(Supplier<IIntegerToString> supplier) {
		UnsignedIntegerGeneral.DEFAULT_TO_STRING = supplier;
	}
}
