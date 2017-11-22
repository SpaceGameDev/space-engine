package space.util.baseobject;

import space.util.string.toStringHelper.ToStringHelper;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface BaseObject extends Createable, Copyable, ToString {
	
	static <OBJ> void initClass(Class<OBJ> clazz, Supplier<OBJ> make) {
		Createable.putMakeFunction(clazz, make);
	}
	
	static <OBJ> void initClass(Class<OBJ> clazz, UnaryOperator<OBJ> copy) {
		Copyable.putCopyFunction(clazz, copy);
	}
	
	static <OBJ> void initClass(Class<OBJ> clazz, Supplier<OBJ> make, UnaryOperator<OBJ> copy) {
		Createable.putMakeFunction(clazz, make);
		Copyable.putCopyFunction(clazz, copy);
	}
	
	static <OBJ> void initClassAll(Class<OBJ> clazz, Supplier<OBJ> make, UnaryOperator<OBJ> copy, BiFunction<ToStringHelper<?>, OBJ, Object> toTSH) {
		Createable.putMakeFunction(clazz, make);
		Copyable.putCopyFunction(clazz, copy);
		ToString.putToTSHFunction(clazz, toTSH);
	}
	
	@Override
	<T> T toTSH(ToStringHelper<T> api);
}
