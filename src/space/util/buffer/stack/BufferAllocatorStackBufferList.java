package space.util.buffer.stack;

import space.util.buffer.alloc.IBufferAllocator;
import space.util.buffer.buffers.IBuffer;
import spaceOld.util.stack.multistack.MultiStack;

public class BufferAllocatorStackBufferList extends MultiStack<IBuffer> implements IBufferAllocatorStack {
	
	public IBufferAllocator alloc;
	
	public BufferAllocatorStackBufferList() {
		this(null);
	}
	
	public BufferAllocatorStackBufferList(IBufferAllocator alloc) {
		this(alloc, defaultPushPopSize, defaultpushPopExpander);
	}
	
	public BufferAllocatorStackBufferList(IBufferAllocator alloc, int pushPopSize, float pushPopExpander) {
		super(null, pushPopSize, pushPopExpander);
		this.onDelete = (IBuffer b) -> b.free();
	}
	
	@Override
	public IBuffer malloc(long capacity) {
		return put(alloc.malloc(capacity));
	}
	
	@Override
	public IBuffer alloc(long address, long capacity) {
		return put(alloc.alloc(address, capacity));
	}
}
