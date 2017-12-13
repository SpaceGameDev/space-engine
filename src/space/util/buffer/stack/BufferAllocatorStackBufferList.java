package space.util.buffer.stack;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.buffers.Buffer;
import space.util.stack.multistack.MultiStack;

public class BufferAllocatorStackBufferList extends MultiStack<Buffer> implements BufferAllocatorStack {
	
	public BufferAllocator alloc;
	
	public BufferAllocatorStackBufferList() {
		this(null);
	}
	
	public BufferAllocatorStackBufferList(BufferAllocator alloc) {
		super(Buffer::free);
	}
	
	@Override
	public Buffer malloc(long capacity) {
		return put(alloc.malloc(capacity));
	}
	
	@Override
	public Buffer alloc(long address, long capacity) {
		return put(alloc.alloc(address, capacity));
	}
}
