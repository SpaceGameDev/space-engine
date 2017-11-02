package space.util.baseobject;

import space.util.string.toStringHelper.ToStringHelperCollection;
import space.util.string.toStringHelper.ToStringHelperInstance;

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
	
	static <OBJ> void initClassAll(Class<OBJ> clazz, Supplier<OBJ> make, UnaryOperator<OBJ> copy, BiFunction<ToStringHelperCollection, OBJ, ToStringHelperCollection> toTSH) {
		Makeable.putMakeFunction(clazz, make);
		Copyable.putCopyFunction(clazz, copy);
		ToString.putToTSHFunction(clazz, toTSH);
	}
	
	@Override
	ToStringHelperInstance toTSH(ToStringHelperCollection api);
}
