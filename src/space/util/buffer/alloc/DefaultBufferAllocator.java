package space.util.buffer.alloc;

import space.util.buffer.direct.DirectBuffer;
import space.util.buffer.direct.DirectBufferImpl;
import space.util.buffer.direct.NotFreeableDirectBuffer;
import space.util.freeableStorage.FreeableStorage;

public class DefaultBufferAllocator implements BufferAllocator {
	
	@Override
	public DirectBuffer alloc(long address, long capacity, FreeableStorage... parents) {
		return new DirectBufferImpl(address, capacity, parents);
	}
	
	@Override
	public DirectBuffer allocNoFree(long address, long capacity, FreeableStorage... parents) {
		return new NotFreeableDirectBuffer(address, capacity, parents);
	}
	
	@Override
	public DirectBuffer malloc(long capacity, FreeableStorage... parents) {
		return new DirectBufferImpl(capacity, parents);
	}
}
