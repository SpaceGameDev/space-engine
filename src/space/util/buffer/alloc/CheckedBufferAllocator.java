package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.CheckedBuffer;

public class CheckedBufferAllocator implements BufferAllocator {
	
	public BufferAllocator alloc;
	
	public CheckedBufferAllocator(BufferAllocator alloc) {
		this.alloc = alloc;
	}
	
	@Override
	public Buffer malloc(long capacity) {
		return new CheckedBuffer(alloc.malloc(capacity));
	}
	
	@Override
	public Buffer alloc(long address, long capacity) {
		return new CheckedBuffer(alloc.alloc(address, capacity));
	}
}
