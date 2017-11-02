package space.engine.environment;

import space.util.keygen.IKey;
import space.util.keygen.IKeyGenerator;
import space.util.keygen.impl.IDKey.IDKey.IDKeyGenerator;
import space.util.keygen.map.IKeyMapGeneralGeneric;
import space.util.keygen.map.KeyMapGeneralGeneric;
import space.util.keygen.map.KeyMapKeyGeneric;

public class Environment {
	
	public static IKeyGenerator gen;
	public static ThreadLocal<KeyMapKeyGeneric<?>> env;
	public static IKeyMapGeneralGeneric<EnvFieldType> type;
	
	static {
		type = new KeyMapGeneralGeneric<>();
		gen = new IDKeyGenerator();
	}
	
	public static <VALUE> IKey<VALUE> makeKey(EnvFieldType ftype) {
		IKey<VALUE> key = gen.generate();
		type.put(key, ftype);
		return key;
	}
}
