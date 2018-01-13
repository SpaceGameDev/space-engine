package space.util.keygen.attribute;

import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;
import space.util.keygen.map.IKeyMapKeyGeneric;

import java.util.function.Supplier;

public interface IAttributeListCreator extends IKeyGenerator {
	
	IAttributeList create();
	
	@Override
	<T> IKey<T> generateKey();
	
	<T> IKey<T> generateKey(Supplier<T> defaultValue);
	
	@Override
	boolean isKeyOf(IKey<?> key);
	
	interface IAttributeList extends IKeyMapKeyGeneric<Object> {
	
	}
}
