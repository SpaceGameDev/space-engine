package space.engine.buffer.direct.alloc.stack;

import space.engine.buffer.Allocator;
import space.engine.stack.multistack.IMultiStack;

/**
 * An {@link Allocator} which de-allocates like a Stack
 */
public interface AllocatorStack<T> extends Allocator<T>, IMultiStack<T> {

}
