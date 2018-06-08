package space.util.buffer.direct.alloc.stack;

import space.util.buffer.Allocator;
import space.util.stack.multistack.IMultiStack;

/**
 * An {@link Allocator} which de-allocates like a Stack
 */
public interface AllocatorStack<T> extends Allocator<T>, IMultiStack<T> {

}
