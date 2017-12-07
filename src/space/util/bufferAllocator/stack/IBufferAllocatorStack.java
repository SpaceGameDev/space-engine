package space.util.bufferAllocator.stack;

import space.util.bufferAllocator.alloc.IBufferAllocator;
import space.util.bufferAllocator.buffers.IBuffer;
import spaceOld.util.stack.multistack.IMultiStack;

public interface IBufferAllocatorStack extends IBufferAllocator, IMultiStack<IBuffer> {

}
