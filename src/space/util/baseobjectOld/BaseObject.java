package space.util.baseobjectOld;

import space.util.string.toStringHelper.ToStringHelper;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface BaseObject extends Makeable, Copyable, ToString {
	
	static <OBJ> void initClass(Class<OBJ> clazz, Supplier<OBJ> make) {
		Makeable.putMakeFunction(clazz, make);
	}
	
	static <OBJ> void initClass(Class<OBJ> clazz, UnaryOperator<OBJ> copy) {
		Copyable.putCopyFunction(clazz, copy);
	}
	
	static <OBJ> void initClass(Class<OBJ> clazz, Supplier<OBJ> make, UnaryOperator<OBJ> copy) {
		Makeable.putMakeFunction(clazz, make);
		Copyable.putCopyFunction(clazz, copy);
	}
	
	static <OBJ> void initClassAll(Class<OBJ> clazz, Supplier<OBJ> make, UnaryOperator<OBJ> copy, BiFunction<ToStringHelper<?>, OBJ, Object> toTSH) {
		Makeable.putMakeFunction(clazz, make);
		Copyable.putCopyFunction(clazz, copy);
		ToString.putToTSHFunction(clazz, toTSH);
	}
	
	@Override
	<T> T toTSH(ToStringHelper<T> api);
}
