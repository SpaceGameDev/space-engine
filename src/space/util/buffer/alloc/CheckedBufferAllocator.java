package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.CheckedBuffer;
import space.util.freeableStorage.IFreeableStorage;

public class CheckedBufferAllocator implements BufferAllocator {
	
	public BufferAllocator alloc;
	
	public CheckedBufferAllocator(BufferAllocator alloc) {
		this.alloc = alloc;
	}
	
	@Override
	public Buffer malloc(long capacity, IFreeableStorage... parents) {
		return new CheckedBuffer(alloc.malloc(capacity, parents));
	}
	
	@Override
	public Buffer alloc(long address, long capacity, IFreeableStorage... parents) {
		return new CheckedBuffer(alloc.alloc(address, capacity, parents));
	}
}
