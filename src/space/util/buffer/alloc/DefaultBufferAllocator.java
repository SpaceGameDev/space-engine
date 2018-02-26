package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.BufferImpl;
import space.util.freeableStorage.IFreeableStorage;

public class DefaultBufferAllocator implements BufferAllocator {
	
	@Override
	public Buffer malloc(long capacity, IFreeableStorage... parents) {
		return new BufferImpl(capacity, parents);
	}
	
	@Override
	public Buffer alloc(long address, long capacity, IFreeableStorage... parents) {
		return new BufferImpl(address, capacity, parents);
	}
}
