package space.engine.buffer;

import space.engine.baseobject.Dumpable;
import space.engine.freeableStorage.Freeable;

public interface Buffer extends Freeable, Dumpable {
	
	long address();
	
	long capacity();
}
