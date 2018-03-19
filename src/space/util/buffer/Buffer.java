package space.util.buffer;

import space.util.baseobject.Dumpable;
import space.util.baseobject.Freeable.FreeableWithStorage;

public interface Buffer extends FreeableWithStorage, Dumpable {
	
	long address();
	
	long capacity();
	
	void clear();
}
