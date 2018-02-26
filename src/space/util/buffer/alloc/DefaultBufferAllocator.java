package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.BufferImpl;
import space.util.ref.freeable.IFreeableStorage;

public class DefaultBufferAllocator implements BufferAllocator {
	
	@Override
	public Buffer malloc(long capacity, IFreeableStorage... lists) {
		return new BufferImpl(capacity, lists);
	}
	
	@Override
	public Buffer alloc(long address, long capacity, IFreeableStorage... lists) {
		return new BufferImpl(address, capacity, lists);
	}
}
