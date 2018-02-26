package space.util.buffer.stack;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.buffers.Buffer;
import space.util.freeableStorage.IFreeableStorage;
import space.util.stack.PointerList;
import space.util.stack.multistack.MultiStack;

/**
 * A {@link BufferAllocatorStack} which will put always create new {@link Buffer Buffers} and puts them in a {@link MultiStack Stack},
 * so when the {@link MultiStack Stack} is {@link MultiStack#pop() poped} they can be freed.
 */
public class BufferAllocatorStackBufferList extends MultiStack<Buffer> implements BufferAllocatorStack {
	
	public BufferAllocator alloc;
	
	public BufferAllocatorStackBufferList(BufferAllocator alloc) {
		super(Buffer::free);
		this.alloc = alloc;
	}
	
	protected BufferAllocatorStackBufferList(BufferAllocator alloc, PointerList pointerList) {
		super(DEFAULT_START_SIZE, Buffer::free, pointerList);
		this.alloc = alloc;
	}
	
	@Override
	public Buffer malloc(long capacity, IFreeableStorage... parents) {
		return put(alloc.malloc(capacity, parents));
	}
	
	@Override
	public Buffer alloc(long address, long capacity, IFreeableStorage... parents) {
		return put(alloc.alloc(address, capacity, parents));
	}
}
