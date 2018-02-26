package space.util.buffer.stack;

import space.util.buffer.alloc.BufferAllocator;
import space.util.buffer.buffers.Buffer;
import space.util.stack.multistack.IMultiStack;

/**
 * a {@link BufferAllocator} which deallocates like a Stack
 */
public interface BufferAllocatorStack extends BufferAllocator, IMultiStack<Buffer> {

}
