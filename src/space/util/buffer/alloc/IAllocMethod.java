package space.util.buffer.alloc;

import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.IFreeableStorage;

@FunctionalInterface
public interface IAllocMethod {
	
	DirectBuffer alloc(long address, long capacity, IFreeableStorage... parents);
}
