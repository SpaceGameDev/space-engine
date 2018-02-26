package space.util.buffer.stack;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.buffers.Buffer;
import space.util.ref.freeable.IFreeableStorage;
import space.util.stack.PointerList;
import space.util.stack.multistack.MultiStack;

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
	public Buffer malloc(long capacity, IFreeableStorage... lists) {
		return put(alloc.malloc(capacity, lists));
	}
	
	@Override
	public Buffer alloc(long address, long capacity, IFreeableStorage... lists) {
		return put(alloc.alloc(address, capacity, lists));
	}
}
