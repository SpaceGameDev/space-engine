package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.IBuffer;

public class DefaultBufferAllocator implements IBufferAllocator {
	
	@Override
	public IBuffer malloc(long capacity) {
		return new Buffer(capacity);
	}
	
	@Override
	public IBuffer alloc(long address, long capacity) {
		return new Buffer(address, capacity);
	}
}
