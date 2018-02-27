package space.util.buffer;

import space.util.baseobject.additional.Dumpable;
import space.util.baseobject.additional.Freeable.FreeableWithStorage;

public interface Buffer extends FreeableWithStorage, Dumpable {
	
	long address();
	
	long capacity();
	
	void clear();
}
