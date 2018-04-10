package space.util.buffer.alloc;

import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

@FunctionalInterface
public interface AllocMethod {
	
	DirectBuffer alloc(long address, long capacity, FreeableStorage... parents);
}
