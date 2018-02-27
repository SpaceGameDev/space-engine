package space.util.buffer.alloc;

import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.IFreeableStorage;

@FunctionalInterface
public interface IMallocMethod {
	
	DirectBuffer malloc(long capacity, IFreeableStorage... parents);
}
