package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.BufferImpl;

public class DefaultBufferAllocator implements BufferAllocator {
	
	@Override
	public Buffer malloc(long capacity) {
		return new BufferImpl(capacity);
	}
	
	@Override
	public Buffer alloc(long address, long capacity) {
		return new BufferImpl(address, capacity);
	}
}
