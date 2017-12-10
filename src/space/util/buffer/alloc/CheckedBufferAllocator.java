package space.util.buffer.alloc;

import space.util.buffer.buffers.Buffer;
import space.util.buffer.buffers.CheckedBuffer;
import space.util.buffer.buffers.IBuffer;

public class CheckedBufferAllocator implements IBufferAllocator {
	
	@Override
	public IBuffer malloc(long capacity) {
		return new CheckedBuffer(new Buffer(capacity));
	}
	
	@Override
	public IBuffer alloc(long address, long capacity) {
		return new CheckedBuffer(new Buffer(address, capacity));
	}
}
