package space.util.buffer.alloc;

import space.util.buffer.direct.CheckedDirectBuffer;
import space.util.buffer.direct.DirectBuffer;
import space.util.freeableStorage.FreeableStorage;

public class CheckedBufferAllocator implements BufferAllocator {
	
	public BufferAllocator alloc;
	
	public CheckedBufferAllocator(BufferAllocator alloc) {
		this.alloc = alloc;
	}
	
	@Override
	public DirectBuffer alloc(long address, long capacity, FreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.alloc(address, capacity, parents));
	}
	
	@Override
	public DirectBuffer allocNoFree(long address, long capacity, FreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.allocNoFree(address, capacity, parents));
	}
	
	@Override
	public DirectBuffer malloc(long capacity, FreeableStorage... parents) {
		return new CheckedDirectBuffer(alloc.malloc(capacity, parents));
	}
}
