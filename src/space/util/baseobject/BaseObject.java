package space.util.baseobject;

import space.util.string.toStringHelper.ToStringHelper;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface BaseObject {
	
	/**
	 * used to have a static-init-function in the interface
	 */
	byte zero = InitializationOfStandardObjects.init2();
	
	static <OBJ> void initClassAll(Class<OBJ> clazz, Supplier<OBJ> create, BiConsumer<OBJ, OBJ> set, UnaryOperator<OBJ> copy, BiFunction<ToStringHelper<?>, OBJ, Object> toTSH) {
		Creatable.manualEntry(clazz, create);
		Setable.manualEntry(clazz, set);
		Copyable.manualEntry(clazz, copy);
		ToString.manualEntry(clazz, toTSH);
	}
}
