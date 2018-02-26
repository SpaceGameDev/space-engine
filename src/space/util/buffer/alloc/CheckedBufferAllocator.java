package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.CheckedBuffer;
import space.util.ref.freeable.IFreeableStorage;

public class CheckedBufferAllocator implements BufferAllocator {
	
	public BufferAllocator alloc;
	
	public CheckedBufferAllocator(BufferAllocator alloc) {
		this.alloc = alloc;
	}
	
	@Override
	public Buffer malloc(long capacity, IFreeableStorage... lists) {
		return new CheckedBuffer(alloc.malloc(capacity, lists));
	}
	
	@Override
	public Buffer alloc(long address, long capacity, IFreeableStorage... lists) {
		return new CheckedBuffer(alloc.alloc(address, capacity, lists));
	}
}
