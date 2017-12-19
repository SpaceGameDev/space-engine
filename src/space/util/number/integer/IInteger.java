package space.util.number.integer;

import space.util.annotation.Self;
import space.util.number.base.NumberBase;
import space.util.number.format.IIntegerToString;

import java.util.function.Supplier;

/**
 * Represents an Integer in Two's Complement.
 * Booleans indicating sign should be true for positive numbers.
 */
public abstract class IInteger<@Self SELF extends IInteger<SELF>> implements NumberBase<SELF> {
	
	static IIntegerToString get() {
		return IntegerGeneral.DEFAULT_TO_STRING.get();
	}
	
	static void set(IIntegerToString toString) {
		set(() -> toString);
	}
	
	static void set(Supplier<IIntegerToString> supplier) {
		IntegerGeneral.DEFAULT_TO_STRING = supplier;
	}
}
