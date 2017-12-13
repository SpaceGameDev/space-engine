package space.util.buffer.stack;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.buffers.Buffer;
import space.util.stack.multistack.IMultiStack;

public interface BufferAllocatorStack extends BufferAllocator, IMultiStack<Buffer> {
	
	@Override
	Buffer malloc(long capacity);
	
	@Override
	Buffer alloc(long address, long capacity);
}
