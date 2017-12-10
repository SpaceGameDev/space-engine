package space.util.buffer.stack;

import space.util.buffer.alloc.IBufferAllocator;
import space.util.buffer.buffers.IBuffer;
import spaceOld.util.stack.multistack.IMultiStack;

public interface IBufferAllocatorStack extends IBufferAllocator, IMultiStack<IBuffer> {

}
