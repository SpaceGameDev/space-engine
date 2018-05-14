package space.util.buffer.alloc;

import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

@FunctionalInterface
public interface MallocMethod {
	
	DirectBuffer malloc(long capacity, FreeableStorage... parents);
}
