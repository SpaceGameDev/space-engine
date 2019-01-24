package space.engine.buffer;

import space.engine.baseobject.Dumpable;
import space.engine.baseobject.Freeable.FreeableWithStorage;

public interface Buffer extends FreeableWithStorage, Dumpable {
	
	long address();
	
	long capacity();
}
