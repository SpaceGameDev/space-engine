package space.util.keygen.attribute;

import space.util.keygen.IKey;
import space.util.keygen.map.IKeyMap;
import space.util.keygen.map.KeyMapImpl;

import java.util.function.Consumer;

public class AttributeListSetupAndUpdateHelper<VALUE> {
	
	public IKeyMap<Consumer<Info<VALUE>>> callMap = new KeyMapImpl<>();
	
	public void put(IKey<?> key, Consumer<Info<VALUE>> consumer) {
		callMap.put(key, consumer);
	}
	
	public static class Info<VALUE> {
	
	}
}
