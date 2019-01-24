package space.engine.buffer.direct.alloc.stack;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import space.engine.baseobject.Freeable;
import space.engine.buffer.Allocator;
import space.engine.buffer.direct.DirectBuffer;
import space.engine.freeableStorage.FreeableStorage;
import space.engine.stack.PointerList;
import space.engine.stack.multistack.MultiStack;

import static space.engine.stack.multistack.MultiStack.DEFAULT_START_SIZE;

/**
 * A {@link AllocatorStack} which will put always create new {@link DirectBuffer Buffers} and puts them in a {@link MultiStack Stack},
 * so when the {@link MultiStack Stack} is {@link MultiStack#pop() poped} they can be freed.
 */
public class BufferAllocatorStackBufferList implements AllocatorStack<DirectBuffer> {
	
	@NotNull
	public Allocator<DirectBuffer> alloc;
	@NotNull
	private MultiStack<DirectBuffer> stack;
	
	public BufferAllocatorStackBufferList(@NotNull Allocator<DirectBuffer> alloc) {
		this.alloc = alloc;
		this.stack = new MultiStack<>(Freeable::free);
	}
	
	protected BufferAllocatorStackBufferList(@NotNull Allocator<DirectBuffer> alloc, @SuppressWarnings("NullableProblems") @NotNull PointerList pointerList) {
		this.alloc = alloc;
		this.stack = new MultiStack<>(DEFAULT_START_SIZE, DirectBuffer::free, pointerList);
	}
	
	//stack
	@Override
	public void push() {
		stack.push();
	}
	
	@Override
	public long pushPointer() {
		return stack.pushPointer();
	}
	
	@Override
	public void pop() {
		stack.pop();
	}
	
	@Override
	public void popPointer(long idl) {
		stack.popPointer(idl);
	}
	
	//stack -> unsupported
	@Override
	@Deprecated
	@Contract("_ -> fail")
	public <X extends DirectBuffer> X put(X t) {
		throw new UnsupportedOperationException();
	}
	
	//create
	@NotNull
	@Override
	public DirectBuffer create(long address, long capacity, @NotNull FreeableStorage... parents) {
		return stack.put(alloc.create(address, capacity, parents));
	}
	
	@NotNull
	@Override
	@Deprecated
	@Contract("_, _, _ -> fail")
	public DirectBuffer createNoFree(long address, long capacity, @NotNull FreeableStorage... parents) {
		throw new UnsupportedOperationException();
	}
	
	//malloc
	@NotNull
	@Override
	public DirectBuffer malloc(long capacity, @NotNull FreeableStorage... parents) {
		return stack.put(alloc.malloc(capacity, parents));
	}
	
	@NotNull
	@Override
	public DirectBuffer calloc(long capacity, @NotNull FreeableStorage... parents) {
		return stack.put(alloc.calloc(capacity, parents));
	}
}
