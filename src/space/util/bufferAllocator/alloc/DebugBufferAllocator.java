package space.util.bufferAllocator.alloc;

import space.util.bufferAllocator.buffers.Buffer;
import space.util.bufferAllocator.buffers.CheckedBuffer;
import space.util.bufferAllocator.buffers.IBuffer;

public class DebugBufferAllocator implements IBufferAllocator {
	
	@Override
	public IBuffer malloc(long capacity) {
		return new CheckedBuffer(new Buffer(capacity));
	}
	
	@Override
	public IBuffer alloc(long address, long capacity) {
		return new CheckedBuffer(new Buffer(address, capacity));
	}
}
